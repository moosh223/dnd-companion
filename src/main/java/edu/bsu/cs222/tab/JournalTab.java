package edu.bsu.cs222.tab;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.*;

public class JournalTab extends Tab {

    private BorderPane parent;
    private TextArea journalSpace;
    private String filepath;

    public JournalTab(String filepath){
        this.filepath = filepath;
        setText("Journal");
        setContent();
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
        parent = (BorderPane) getContent();
    }

    public void setContent(){
        try{
            setContent(new FXMLLoader(getClass().getClassLoader().getResource("fxml/JournalTab.fxml")).load());
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
