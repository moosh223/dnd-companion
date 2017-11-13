package edu.bsu.cs222;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class NetworkServerParser {

    public ServerSocket serverSocket;
    public Socket server;

    public NetworkServerParser(int port) throws IOException {
            serverSocket = new ServerSocket(port);

        }

        public void terminateConnection() throws IOException {
            serverSocket.close();
        }

    public InetAddress getLANAddress() throws UnknownHostException{
        try {
            InetAddress candidateAddress = null;
            for (Enumeration interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();) {
                NetworkInterface anInterface = (NetworkInterface) interfaces.nextElement();
                for (Enumeration inetAddrs = anInterface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr;
                        }
                        else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    public void getMessageFromClient(){
        new Thread(() -> {
            try{
                InputStream fromClient = server.getInputStream();
                DataInputStream out = new DataInputStream(fromClient);
                System.out.println(out.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void writeToClient(Socket socket, String message){
        try{
            OutputStream toClient = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(toClient);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}