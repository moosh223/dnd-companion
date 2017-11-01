package edu.bsu.cs222;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClientParser {

    private Socket client;
    private String serverName;

    public NetworkClientParser(String serverName){
        int port = 2000;
        this.serverName = serverName;
        connectToServer(port);
    }

    private void connectToServer(int port) {
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMessageFromServer(){
        try{
            InputStream outToServer = client.getInputStream();
            DataInputStream out = new DataInputStream(outToServer);
            System.out.println(out.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToServer(String message){
        try{
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

