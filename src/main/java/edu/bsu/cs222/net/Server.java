package edu.bsu.cs222.net;

import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server extends Thread implements Runnable{

    private ArrayList<ClientNode> clientList;
    private ServerSocket clientListener;
    private String campaignPath;
    private TabPane view;

    public Server(ArrayList<ClientNode> clientList, TabPane pane) throws IOException{
        this.clientList = clientList;
        view = pane;
        clientListener = new ServerSocket(2000);
    }

    @Override
    public String toString(){
        return clientListener.getInetAddress().toString();
    }


    /**Gets the LAN address of the server
     * @return The internal IP address of the server used to connect
     * @throws UnknownHostException Cannot find a host address
     */
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
    public void run(){
        System.out.println("Starting Server...");
        while(Thread.currentThread().isAlive()) try {
            Socket socket = clientListener.accept();
            createNewClient(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewClient(Socket socket) {
        try {
            System.out.printf("%s has connected%n", socket.getInetAddress().toString());
            ClientNode client = new ClientNode(socket,view);
            client.setName(socket.getInetAddress().toString());
            if(campaignPath != null){
                client.setPath(String.format("%s/characters%s",campaignPath,client.getName()));
            }
            client.setSide("server");
            clientList.add(client);
            client.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Sets the campaign path for the server and for any existing client nodes
     * @author Josh Mooshian <jmmooshian@bsu.edu>
     * @param campaignPath Path to the campaign loaded
     */
    public void setCampaignPath(String campaignPath) {
        this.campaignPath = campaignPath;
        for(ClientNode client: clientList){
            client.setPath(String.format("%s/characters%s",campaignPath,client.getName()));
        }
    }
}
