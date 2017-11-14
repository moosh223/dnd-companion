package edu.bsu.cs222;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
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
            socket = new Socket();
            socket.connect(new InetSocketAddress(serverName,port),1000);
            System.out.println("Just connected to " + socket.getRemoteSocketAddress());
        } catch (IOException e){
            System.err.println("Server not found");
        }
    }

    public void getMessageFromServer(){
        try{
            InputStream outToServer = socket.getInputStream();
            DataInputStream out = new DataInputStream(outToServer);
            try {
                System.out.println(out.readUTF());
            }catch(EOFException e){
                try {
                    System.err.printf("You have disconnected from %s%n",socket.getInetAddress());
                    Thread.currentThread().join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

