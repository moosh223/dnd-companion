package edu.bsu.cs222.net;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server extends Thread implements Runnable{

    private ArrayList<ClientNode> clientList;
    private ServerSocket clientListener;

    public Server(ArrayList<ClientNode> clientList) throws IOException{
        this.clientList = clientList;
        clientListener = new ServerSocket(2000);
    }

    public String toString(){
        return clientListener.getInetAddress().toString();
    }

    public InetAddress getLANAddress() throws UnknownHostException {
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

    @Override
    public void start(){
        System.out.println("Starting Server...");
    }

    @Override
    public void run(){
        while(Thread.currentThread().isAlive()) {
            try {
                createNewClient(clientListener.accept());
                System.out.println("CREATING NEW THREAD");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createNewClient(Socket socket) {
        System.out.printf("%s has connected%n", socket.getInetAddress().toString());
        ClientNode thread = new ClientNode(socket);
        thread.setName(socket.getInetAddress().toString());
        clientList.add(thread);
        thread.start();
    }
}
