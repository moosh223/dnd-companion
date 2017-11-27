package edu.bsu.cs222.net;

import java.io.*;
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
            InputStream fromServer = socket.getInputStream();
            DataInputStream out = new DataInputStream(fromServer);
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

    public void sendCharacterXML(File file){
        try (ByteArrayOutputStream os = new ByteArrayOutputStream())
        {
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);
            os.flush();
            OutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.write(os.toByteArray());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getObjectFromServer(String filepath){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            OutputStream os = new FileOutputStream(String.format("%stemp.xml",filepath));
            byte[] buffer = new byte[0xFFFF];
            int len = dis.read(buffer);
            System.out.println(len);
            os.write(buffer, 0, len);
            os.flush();
            os.close();
        }catch(IOException e){
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

    public String getSocketAddress(){
        return socket.getInetAddress().toString();
    }
}

