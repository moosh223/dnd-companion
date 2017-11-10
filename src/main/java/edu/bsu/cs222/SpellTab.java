package edu.bsu.cs222;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.*;

public class SpellTab {
    private FXMLLoader sheet;
    private BorderPane parent;
    private String filepath;

    public SpellTab(){
        sheet = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SpellTab.fxml"));
        loadPaneContent();
        hookIntoButton();
    }

    private void hookIntoButton() {
        GridPane grid = (GridPane)parent.getChildren().get(0);
        for(Node node : grid.getChildren()){
            System.out.println(node);
            if(node.getClass() == Button.class){
                Button button = (Button)node;
                button.setOnAction((e) -> createNewSpell());
            }
        }
    }

    private void createNewSpell() {

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
