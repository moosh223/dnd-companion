package edu.bsu.cs222;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class NetworkServerParser {

        public ServerSocket serverSocket;
        public Socket server;

        public NetworkServerParser(int port) throws IOException {
            serverSocket = new ServerSocket(port);
        }

        public void terminateConnection() throws IOException {
            server.close();
        }
    }