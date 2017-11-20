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
            while (Thread.currentThread().isAlive()) {
                String message = dis.readUTF();
                if(message.equals("load")){
                    receiver(getName());
                    clientCharacter = new PlayerCharacter(String.format("assets/campaigns/temp/%s.xml",getName()));
                }else{
                    System.out.println("Message Received, no command");
                }
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
                os.flush();
                os.close();
                return;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public PlayerCharacter getCharacter() {
        return clientCharacter;
    }
}
