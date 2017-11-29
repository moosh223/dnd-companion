package edu.bsu.cs222.net;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientModel{

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final static int SERVER_TIMEOUT_MILLIS = 1000;

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

    public String getSocketAddress(){
        return socket.getInetAddress().toString();
    }
}

