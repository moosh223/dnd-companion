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
import java.nio.file.Files;
import java.util.ArrayList;

public class ClientNode extends Thread implements Runnable{

    private final static int SERVER_TIMEOUT_MILLIS = 1000;
    private String side;
    private TabPane view;
    private Socket connection;
    private DataOutputStream dos;
    private DataInputStream dis;
    private CharacterParser character;
    private String path;
    private CharacterTab tab;

    public ClientNode(Socket connection, TabPane view) throws IOException{
        this.connection = connection;
        System.out.println("Server Side Client Node Created");
        this.view = view;
        side = "server";
        initStreams();
    }

    public ClientNode(String address, int port) throws IOException{
        connection = new Socket();
        connection.connect(new InetSocketAddress(address,port),SERVER_TIMEOUT_MILLIS);
        side = "client";
        System.out.println("Client Side Client Node Created");
        initStreams();
    }

    public void initStreams() throws IOException{
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
                if(side.equals("server")) {
                    Platform.runLater(() -> view.getTabs().remove(tab));
                    Files.delete(new File(path + ".xml").toPath());
                }
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
            case "UPDATE":
                System.out.println("update sent");
                retrieveCharacterSheet();
                break;
            default:
                System.out.println(s);
                break;
        }
    }

    public void retrieveCharacterSheet() {
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
        updateCharacterSheet();
    }

    private void updateCharacterSheet() {
        character = new CharacterParser(path);
        tab = getMatchingTab();
        Platform.runLater(() -> {
            view.getTabs().remove(tab);
            CharacterTab ctab = new CharacterTab(character,this);
            ctab.setText(character.readTag("name"));
            view.getTabs().add(ctab);
            tab = ctab;
            view.getSelectionModel().select(ctab);
        });
    }

    private CharacterTab getMatchingTab() {
        for(Tab tab: view.getTabs()) {
            try {
                CharacterTab ctab = (CharacterTab) tab;
                if (ctab.getCharacter().getPath().equals(character.getPath())) {
                    return ctab;
                }
            }catch (ClassCastException e){
                System.err.println("Non-character tab found. Ignoring");
            }
        }
        return null;
    }

    public void setCharacter(CharacterParser character) {
        this.character = character;
    }

    public CharacterParser getCharacter() {
        return character;
    }

    public void setView(TabPane view) {
        this.view = view;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
