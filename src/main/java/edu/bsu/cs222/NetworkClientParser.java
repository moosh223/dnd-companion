package edu.bsu.cs222;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class NetworkClientParser {

    private Socket socket;
    private String serverName;

    public NetworkClientParser(String serverName){
        int port = 2000;
        this.serverName = serverName;
        connectToServer(port);
    }

    private void connectToServer(int port){
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            socket = new Socket(serverName, port);
            System.out.println("Just connected to " + socket.getRemoteSocketAddress());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getMessageFromServer(){
        new Thread(() -> {
            try{
                InputStream outToServer = socket.getInputStream();
                DataInputStream out = new DataInputStream(outToServer);
                System.out.println(out.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void writeToServer(String message){
        try{
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSocket(){
        return socket.getInetAddress().toString();
    }
}

