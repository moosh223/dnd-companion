package edu.bsu.cs222;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetThread extends Thread implements Runnable{

    private Socket netSocket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String UTFMessage = "Default connect";

    public NetThread(Socket netSocket){
        this.netSocket = netSocket;
        createDataOutputStream();
        createDataInputStream();
    }

    private void createDataInputStream(){
        try {
            dis = new DataInputStream(netSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDataOutputStream() {
        try {
            dos = new DataOutputStream(netSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setUTFMessage(String UTFMessage){
        this.UTFMessage = UTFMessage;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().setName(dis.readUTF());
            dos.writeUTF("you connected,"+netSocket.getInetAddress()+"!");
            while (Thread.currentThread().isAlive()) {
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
