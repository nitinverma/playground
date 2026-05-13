package main

import (
	"fmt"
	"net/http"
	"os"
	"runtime"
	"time"
)

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello! router\n")
	fmt.Fprintf(w, "Architecture: %s\n", runtime.GOARCH)
	fmt.Fprintf(w, "OS: %s\n", runtime.GOOS)

	host, _ := os.Hostname()
	fmt.Fprintf(w, "Hostname: %s\n", host)

	fmt.Fprintf(w, "Time: %s\n", time.Now())
}

func main() {
	http.HandleFunc("/", handler)

	fmt.Println("Starting HTTP server on :8080")
	http.ListenAndServe(":8080", nil)
}

