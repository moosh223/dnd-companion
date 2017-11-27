package edu.bsu.cs222.net;

import edu.bsu.cs222.PlayerCharacter;
import edu.bsu.cs222.tab.CharacterTab;

import java.io.*;
import java.net.Socket;

public class NetThread extends Thread implements Runnable{

    private Socket netSocket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private PlayerCharacter clientCharacter;
    private CharacterTab threadTab;
    private String campaign = null;
    private String characterFile;

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
                    String name = dis.readUTF();
                    characterFile = String.format("assets/campaigns/%s/characters/%s",campaign,name);
                    receiveCharacter(characterFile);
                    clientCharacter = new PlayerCharacter(characterFile);
                    checkPlayerUpdates();
                }else {
                    System.out.println("Message Received, no command");
                    try{
                        dis.readUTF();
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
                threadTab.updateFlag = false;
                sendCharacterUpdate(characterFile);
            }
        }
    }

    private void sendCharacterUpdate(String file) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream())
        {
            InputStream is = new FileInputStream(file+".xml");
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);
            os.flush();
            OutputStream dos = new DataOutputStream(netSocket.getOutputStream());
            dos.write(os.toByteArray());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void receiveCharacter(String filepath){
        try {
            OutputStream os = new FileOutputStream(String.format("%s.xml",filepath));
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
