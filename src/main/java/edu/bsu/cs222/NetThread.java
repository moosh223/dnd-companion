package edu.bsu.cs222;

import javafx.scene.control.Tab;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class NetThread extends Thread implements Runnable{

    private Socket netSocket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private PlayerCharacter clientCharacter;
    private CharacterTab threadTab;
    private String campaign = null;

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
                if(message.equals("load") && campaign != null){
                    receiver(getName());
                    clientCharacter = new PlayerCharacter(String.format("assets/campaigns/%s/characters/%s.xml",campaign,getName()));
                    System.out.println("Begin checking for player changes");
                    checkPlayerUpdates();
                }else {
                    System.out.println("Message Received, no command");
                    try{
                        dis.read(new byte[0xFFFF]);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void checkPlayerUpdates() {
        while(Thread.currentThread().isAlive()){
            if(threadTab != null && threadTab.updateFlag){
                System.out.println("flag raised");
                threadTab.updateFlag = false;
            }
        }
    }

    private void receiver(String filepath){
        try {
            OutputStream os = new FileOutputStream(String.format("assets/campaigns/%s/characters/%s.xml",campaign,filepath));
            byte[] buffer = new byte[0xFFFF];
            int len = dis.read(buffer);
                os.write(buffer, 0, len);
                os.flush();
                os.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public PlayerCharacter getCharacter() {
        return clientCharacter;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setTab(CharacterTab tab) {
        threadTab = tab;
    }

    public CharacterTab getTab() {
        return threadTab;
    }
}
