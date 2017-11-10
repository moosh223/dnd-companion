package edu.bsu.cs222;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.*;

public class JournalTab {

    private FXMLLoader sheet;
    private BorderPane parent;
    private TextArea journalSpace;
    private String filepath;

    public JournalTab(String filepath){
        sheet = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JournalTab.fxml"));
        this.filepath = filepath;
        loadPaneContent();
        setTextListener();
        readJournal();
    }

    private void setTextListener() {
        journalSpace = (TextArea)parent.getChildren().get(0);
        journalSpace.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                writeJournal();
            }
        });
    }

    private void writeJournal() {
        try {
            PrintWriter writer = new PrintWriter(filepath, "UTF-8");
            writer.write(journalSpace.getText());
            writer.close();
        } catch (FileNotFoundException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void readJournal(){
        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            journalSpace.setText(bufferedReader.readLine());
        } catch (FileNotFoundException ignored){
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPaneContent() {
        try{
            parent = sheet.load();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Node getSheet() {
        return parent;
    }

}
