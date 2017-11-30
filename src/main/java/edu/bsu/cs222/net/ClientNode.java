package edu.bsu.cs222.net;

import edu.bsu.cs222.util.CharacterParser;
import edu.bsu.cs222.tab.CharacterTab;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientNode extends Thread implements Runnable{

    private final static int SERVER_TIMEOUT_MILLIS = 1000;
    private String side;
    private TabPane view;
    private Socket connection;
    private DataOutputStream dos;
    private DataInputStream dis;
    private CharacterParser character;
    ArrayList<CharacterTab> cleanup = new ArrayList<>();

    public ClientNode(Socket connection, TabPane view) throws IOException{
        this.connection = connection;
        System.out.println("Server Side Client Node Created");
        this.view = view;
        side = "server";
        dos = new DataOutputStream(connection.getOutputStream());
        dis = new DataInputStream(connection.getInputStream());
    }

    public ClientNode(String address, int port) throws IOException{
        connection = new Socket();
        connection.connect(new InetSocketAddress(address,port),SERVER_TIMEOUT_MILLIS);
        side = "client";
        System.out.println("Client Side Client Node Created");
        dos = new DataOutputStream(connection.getOutputStream());
        dis = new DataInputStream(connection.getInputStream());
    }

    public String getSocketAddress(){
        return connection.getInetAddress().toString();
    }
    public DataOutputStream getDos() {
        return dos;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) try {
            parse(dis.readUTF());
        }catch (EOFException | SocketException e){
            System.err.println("Disconnected");
            try {
                connection.close();
                Thread.currentThread().join();
            } catch (InterruptedException | IOException e1) {
                e1.printStackTrace();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void parse(String s) throws IOException{
        String[] command = s.split(" ");
        switch (command[0]) {
            case "GET":
                System.out.println("get request received from " + connection.getInetAddress());
                dos.writeUTF("POST");
                break;
            case "POST":
                System.out.println("post request received from " + connection.getInetAddress());
                break;
            case "UPDATE":
                System.out.println("update sent");
                retrieveCharacterSheet(command[1]);
                break;
            case "CHAR":
            default:
                System.out.println(s);
                break;
        }
    }

    public void retrieveCharacterSheet(String path) {
        try {
            OutputStream os = new FileOutputStream(path+".xml");
            byte[] buffer = new byte[0xFFFF];
            int len = dis.read(buffer);
            System.out.println(len);
            os.write(buffer, 0, len);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        character = new CharacterParser(path);
        for(Tab tab: view.getTabs()) {
            try {
                CharacterTab ctab = (CharacterTab) tab;
                if (ctab.getCharacter().readTag("name").equals(character.readTag("name"))) {
                    cleanup.add(ctab);
                }
            }catch (ClassCastException e){
                System.err.println("Non-character tab found. Ignoring");
            }
        }
        Platform.runLater(() -> {
            view.getTabs().removeAll(FXCollections.observableArrayList(cleanup));
            CharacterTab ctab = new CharacterTab(character,this);
            view.getTabs().add(ctab);
            view.getSelectionModel().select(ctab);
        });
    }

    public void setCharacter(CharacterParser character) {
        this.character = character;
        Platform.runLater(() -> {

        });
    }

    public CharacterParser getCharacter() {
        return character;
    }

    public void setView(TabPane view) {
        this.view = view;
    }
}
