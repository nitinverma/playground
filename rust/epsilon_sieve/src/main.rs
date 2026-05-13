use std::env;
use std::io::{self, Write};
use std::time::Instant;
use bitvec::prelude::*;

const CYAN: &str = "\x1b[1;36m";
const YELLOW: &str = "\x1b[1;33m";
const MAGENTA: &str = "\x1b[1;35m";
const GREEN: &str = "\x1b[1;32m";
const RED: &str = "\x1b[1;31m";
const WHITE: &str = "\x1b[1;37m";
const RESET: &str = "\x1b[0m";

// ----------------------------------------------------------------------
// Bootstrapping Primes (64-Bit Mod-30 Wheel Factorization)
// ----------------------------------------------------------------------
fn find_primes(n: usize) -> Vec<u64> {
    if n == 0 { return vec![]; }
    if n == 1 { return vec![2]; }
    if n == 2 { return vec![2, 3]; }
    if n == 3 { return vec![2, 3, 5]; }

    let nf = n as f64;
    let limit = (nf * (nf.ln() + nf.ln().ln() + 1.0)).ceil() as usize;

    //let mut is_prime = vec![true; limit];
    let mut is_prime = bitvec![u8, Msb0; 1; limit];
    let mut primes = Vec::with_capacity(n);
    
    primes.push(2);
    primes.push(3);
    primes.push(5);

    let wheel_gaps = [4, 2, 4, 2, 4, 6, 2, 6];
    
    let mut p = 7;
    let mut w = 0;
    // Mathematically guaranteed to never exceed 4,294,967,295 (2^32 - 1)
    let sqrt_limit = limit.isqrt();

    while p <= sqrt_limit {
        if is_prime[p] {
            primes.push(p as u64); // Upgraded to 64-bit array
            if primes.len() == n { return primes; }

            let mut multiple = p * p;
            while multiple < limit {
                //is_prime[multiple] = false;
                is_prime.set(multiple, false);
                multiple += 2 * p; 
            }
        }
        p += wheel_gaps[w];
        w = (w + 1) % 8;
    }

    while p < limit {
        if is_prime[p] {
            primes.push(p as u64); // Upgraded to 64-bit array
            if primes.len() == n { return primes; }
        }
        p += wheel_gaps[w];
        w = (w + 1) % 8;
    }

    primes
}

// ----------------------------------------------------------------------
// Verification Engine (Upgraded to handle 64-bit factors)
// ----------------------------------------------------------------------
fn verify_factorization(mut n: u64, sieve_primes: &[u64]) -> (Vec<u64>, Vec<u64>) {
    let mut sieve_factors = Vec::new();
    for &p in sieve_primes {
        if n % p == 0 {
            sieve_factors.push(p);
            while n % p == 0 { n /= p; }
        }
    }
    let mut non_sieve_factors = Vec::new();
    if n > 1 { non_sieve_factors.push(n); }
    (sieve_factors, non_sieve_factors)
}

// ----------------------------------------------------------------------
// Main Execution
// ----------------------------------------------------------------------
fn main() {
    let max_supported_primes = 203_280_221;
    let args: Vec<String> = env::args().collect();
    let mut n_primes: usize = 30000; 

    if args.len() > 1 {
        match args[1].parse::<usize>() {
            Ok(num) if num > 0 => n_primes = num,
            _ => {
                println!("{}Usage: cargo run --release -- <prime-index>{}", RED, RESET);
                return;
            }
        }
    }
    if n_primes > max_supported_primes {
        println!("{}Warning: Cap reached. Limiting prime generation to {}{}", YELLOW, max_supported_primes, RESET);
        n_primes = max_supported_primes;
    }

    println!("{}Bootstrapping continuous waves...{}", YELLOW, RESET);
    let sieve_start_time = Instant::now();
    let primes_ext = find_primes(n_primes + 1);
    let sieve_primes = &primes_ext[..n_primes];
    let sieve_elapsed = sieve_start_time.elapsed();
    println!("{}⏱t  Sieve Execution Time: {:.2?}, Max prime {}{}", YELLOW, sieve_elapsed, sieve_primes.last().unwrap(), RESET);
    
    // p_next is now safely pulled directly as a u64
    let p_next = primes_ext[n_primes]; 
    
    // Checked Multiplication Guard against the physical u64 capacity horizon
    let max_valid = match p_next.checked_mul(p_next) {
        Some(sq) => sq.saturating_sub(1),
        None => u64::MAX,
    };

    let expected_noise_floor = 0.30103 * (n_primes as f64);
    let max_p = if n_primes > 1 { (n_primes as f64) * (n_primes as f64).ln() } else { 2.0 };
    let derivative_offset = max_p.log10();
    let eps_power = -((expected_noise_floor * 3.0) + derivative_offset + 50.0).round();

    println!("{}=================================================={}", CYAN, RESET);
    println!("{}   ANALYTIC SINC-SIEVE (Closed-Form Taylor Mode)  {}", CYAN, RESET);
    println!("{}=================================================={}", CYAN, RESET);
    println!("Sieve initialized with {} primes (Max Gear: {})", n_primes, sieve_primes.last().unwrap());
    
    if max_valid == u64::MAX {
        println!("{}Geometric Sieve Bounds: 100% accurate up to the 64-bit integer limit!{}", YELLOW, RESET);
    } else {
        println!("Geometric Sieve Bounds: 100% accurate up to {}", max_valid);
    }
    
    println!("{}Architecture: Native f64 Angle Addition | Epsilon: 10^{}{}\n", MAGENTA, eps_power, RESET);

    loop {
        println!("{}", format!("{}-{}", WHITE, RESET).repeat(50));
        print!("{}Enter a number between {} and {} (or 'q' to quit): {}", WHITE, p_next, max_valid, RESET);
        io::stdout().flush().unwrap();

        let mut input = String::new();
        io::stdin().read_line(&mut input).unwrap();
        let input = input.trim();

        if input.eq_ignore_ascii_case("q") { break; }

        let target_n: u64 = match input.parse() {
            Ok(num) => num,
            Err(_) => { continue; }
        };

        if target_n < p_next || (target_n > max_valid && max_valid != u64::MAX) {
            println!("{}Out of bounds! Domain: [{}, {}]{}", RED, p_next, max_valid, RESET);
            continue;
        }

        // ==========================================
        // START TOPOLOGICAL SCANNER
        // ==========================================
        let start_time = Instant::now();

        let mut log_depth: f64 = 0.0;
        let mut sine_count = 0;
        let mut taylor_count = 0;

        for &p in sieve_primes {
            let p_f64 = p as f64;
            let rem = target_n % p;

            if rem == 0 {
                log_depth += eps_power + std::f64::consts::PI.log10() - p_f64.log10();
                taylor_count += 1;
            } else {
                let amp = f64::sin(std::f64::consts::PI * (rem as f64) / p_f64);
                log_depth += amp.abs().log10();
                sine_count += 1;
            }
        }
        
        let calculated_sieve_factors = (log_depth / eps_power).round() as i64;
        let elapsed = start_time.elapsed();
        
        let (actual_sieve_factors, outside_factors) = verify_factorization(target_n, sieve_primes);

        // --- OUTPUT CONSOLE ---
        println!("\n{}[TOPOLOGY SCANNER]{}", MAGENTA, RESET);
        println!("Raw Log-Depth:       {:.2}", log_depth);
        println!("Calculated Sieve Factors: {}{}{}", CYAN, calculated_sieve_factors, RESET);
        println!("{}Engine Path Routing: {} Baseline Waves, {} Taylor Derivatives{}", YELLOW, sine_count, taylor_count, RESET);
        
        println!("\n{}[VERIFICATION]{}", GREEN, RESET);
        println!("Actual Sieve Factors:     {} {:?}", actual_sieve_factors.len(), actual_sieve_factors);
        
        if !outside_factors.is_empty() {
            println!("Non-Sieve Factors:        {} {:?} {}(Invisible to Sieve){}", outside_factors.len(), outside_factors, YELLOW, RESET);
        } else {
            println!("Non-Sieve Factors:        0 []");
        }

        println!("\n{}[VERDICT]{}", WHITE, RESET);
        if calculated_sieve_factors == 0 {
            println!("{}>>> {} is a TRUE PRIME <<<{}", GREEN, target_n, RESET);
        } else {
            println!("{}>>> {} is COMPOSITE <<<{}", RED, target_n, RESET);
        }
        println!("{}⏱  Execution Time: {:.2?}{}", YELLOW, elapsed, RESET);
        println!();
    }
}
