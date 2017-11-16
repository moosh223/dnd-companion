package edu.bsu.cs222;

import java.io.*;
import java.net.Socket;

public class NetThread extends Thread implements Runnable{

    private Socket netSocket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private PlayerCharacter clientCharacter;

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

    public void setClientCharacter(PlayerCharacter clientCharacter){
        this.clientCharacter = clientCharacter;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    @Override
    public void run() {
        try {
            dis.readUTF();
            dos.writeUTF("you connected,"+netSocket.getInetAddress()+"!");
            receiver(getName());
            while (Thread.currentThread().isAlive()) {
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void receiver(String filepath){
        try {
            OutputStream os = new FileOutputStream(String.format("assets/campaigns/temp/%s.xml",filepath));
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = dis.read(buffer)) != -1; ) {
                os.write(buffer, 0, len);
            }
            os.close();
            clientCharacter = new PlayerCharacter(String.format("assets/campaigns/temp/%s.xml",getName()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public PlayerCharacter getCharacter() {
        return clientCharacter;
    }
}
