package edu.bsu.cs222.net;

import edu.bsu.cs222.control.CompanionController;
import edu.bsu.cs222.util.CharacterParser;
import edu.bsu.cs222.tab.CharacterTab;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientNode extends Thread implements Runnable{

    private final static int SERVER_TIMEOUT_MILLIS = 1000;
    private Socket connection;
    private DataOutputStream dos;
    private DataInputStream dis;

    public ClientNode(Socket connection) throws IOException{
        this.connection = connection;
        System.out.println("Server Side Client Node Created");
        dos = new DataOutputStream(connection.getOutputStream());
        dis = new DataInputStream(connection.getInputStream());
    }

    public ClientNode(String address, int port) throws IOException{
        connection = new Socket();
        connection.connect(new InetSocketAddress(address,port),SERVER_TIMEOUT_MILLIS);
        System.out.println("Client Side Client Node Created");
        dos = new DataOutputStream(connection.getOutputStream());
        dis = new DataInputStream(connection.getInputStream());
    }

    public String getSocketAddress(){
        return connection.getInetAddress().toString();
    }
    public DataOutputStream getDos() {
        return dos;
    }
    public DataInputStream getDis() {
        return dis;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) try {
            System.out.println(dis.readUTF());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}