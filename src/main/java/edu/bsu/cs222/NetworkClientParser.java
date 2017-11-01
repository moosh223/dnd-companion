package edu.bsu.cs222;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClientParser {
    public class client {

        public static void main(String [] args) {

            Scanner scan = new Scanner(System.in);
            //10.2.170.231
            String serverName = scan.nextLine();
            int port = 2000;
            try {
                System.out.println("Connecting to " + serverName + " on port " + port);
                Socket client = new Socket(serverName, port);

                System.out.println("Just connected to " + client.getRemoteSocketAddress());
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);

                out.writeUTF("Hello from " + client.getLocalSocketAddress());
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);

                System.out.println("Server says " + in.readUTF());
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
