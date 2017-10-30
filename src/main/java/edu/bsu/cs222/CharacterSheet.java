package edu.bsu.cs222;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.swing.border.Border;
import java.awt.*;

public class CharacterSheet {
    FXMLLoader sheet;
    PlayerCharacter character;
    Node parent;
    @FXML TextField displayName;

    public CharacterSheet(){
        sheet = new FXMLLoader(getClass().getClassLoader().getResource("CharacterTab.fxml"));
        System.out.println(sheet.toString());
        try{
            parent = sheet.load();
            System.out.println(parent.toString());
            BorderPane topLevel = (BorderPane)parent;
            System.out.println(topLevel.getChildren().get(0).);
        }catch(Exception e){}
    }

    public Node getSheet() {
        return parent;
    }
}
