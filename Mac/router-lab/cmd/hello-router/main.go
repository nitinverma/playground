package main

import (
	"fmt"
	"net"
	"os"
	"runtime"
	"time"
)

func main() {
	fmt.Println("Hello! router")

	fmt.Println("Architecture:", runtime.GOARCH)
	fmt.Println("OS:", runtime.GOOS)

	host, _ := os.Hostname()
	fmt.Println("Hostname:", host)

	ifaces, _ := net.Interfaces()
	for _, i := range ifaces {
		fmt.Println("Interface:", i.Name)
	}

	fmt.Println("Time:", time.Now())
}

