package main

import (
	"bufio"
	"flag"
	"log/slog"
	"net"
	"os"
)

var logger *slog.Logger

func main() {

	logger = slog.New(slog.NewJSONHandler(os.Stdout, nil))

	port := flag.String("port", ":9090", "allows to change the port where will connect the slaves")
	logger.Info("starting tcp server: ", slog.String("port", *port))

	listener, err := net.Listen("tcp", *port)
	if err != nil {
		logger.Error("Error when tried to create tcp listener", slog.Any("err", err))
		return
	}

	for {
		accepted, tryToAcceptError := listener.Accept()
		if tryToAcceptError != nil {
			logger.Error("something happend when conection intent was accepted", slog.Any("err", tryToAcceptError))
			continue
		}

		go handleConnection(accepted)
	}

}

func handleConnection(connection net.Conn) {
	message, err := bufio.NewReader(connection).ReadString('\n')
	if err != nil {
		logger.Error("cant read message from slave", slog.Any("err", err))
    return
	}

	logger.Info("message received from slave", slog.Any("message", message))
}
