package edu.bsu.cs222.tab;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.*;

public class SpellTab {
    private FXMLLoader sheet;
    private AnchorPane parent;
    private Button newSpellButton;
    private GridPane gridPane;
    private int count = 1;
    private String filepath;

    public SpellTab(){
        sheet = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SpellTab.fxml"));
        loadPaneContent();
        setButtonListener();
    }

    private void setButtonListener() {
        newSpellButton.setOnAction((e) -> addNewSpellSlot());
    }

    private void addNewSpellSlot() {
    }

    private void loadPaneContent() {
        try{
            parent = sheet.load();
            BorderPane borderPane = (BorderPane)parent.getChildren().get(0);
            newSpellButton = (Button)borderPane.getBottom();
            ScrollPane sp = (ScrollPane) borderPane.getCenter();
            gridPane = (GridPane)sp.getContent();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Node getSheet() {
            return parent;
        }
}
