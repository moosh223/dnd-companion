package edu.bsu.cs222.net;

import javafx.concurrent.Task;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerTask extends Task<Void> {

    private ArrayList<ClientNode> clientList;
    private ServerSocket clientListener;

    public ServerTask(ArrayList<ClientNode> clientList) throws IOException{
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
                for (Enumeration addresses = anInterface.getInetAddresses(); addresses.hasMoreElements();) {
                    InetAddress address = (InetAddress) addresses.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address.isSiteLocalAddress()) {
                            return address;
                        }
                        else if (candidateAddress == null) {
                            candidateAddress = address;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK INetAddress.getLocalHost() method unexpectedly returned null.");
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
    public Void call(){
        System.out.println("Starting Server...");
        while(Thread.currentThread().isAlive()) try {
            Socket socket = clientListener.accept();
            createNewClient(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createNewClient(Socket socket) {
        try {
            System.out.printf("%s has connected%n", socket.getInetAddress().toString());
            ClientNode thread = new ClientNode(socket);
            thread.setName(socket.getInetAddress().toString());
            clientList.add(thread);
            thread.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
