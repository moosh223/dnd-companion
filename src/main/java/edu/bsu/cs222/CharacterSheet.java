package edu.bsu.cs222;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterSheet {
    private FXMLLoader sheet;
    private PlayerCharacter character;
    private BorderPane parent;
    private TabPane testTab;
    private List<TextField> displayFields = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();

    public CharacterSheet(PlayerCharacter character) {
        sheet = new FXMLLoader(getClass().getClassLoader().getResource("CharacterTab.fxml"));
        this.character = character;
        loadTabPaneContent();
        buildDisplayFields();
        createDisplayAction();
        updateCharacterView();
    }

    private TextField searchForID(String searchQuery) {
        for(TextField field: displayFields){
            System.out.println(field);
            if(field.getId().equals(searchQuery)){
                //System.out.println(field.getText());
                return field;
            }else{
                System.out.println("ERROR");
            }
        }
        return null;
    }

    private void buildDisplayFields() {
        for(Tab tab :testTab.getTabs()){
            Pane paneContent = (Pane)tab.getContent();
            for(Node node: paneContent.getChildren()){
                if(node.getId() != null){
                    try{
                        displayFields.add((TextField)node);
                    }catch(Exception e){
                        labels.add((Label)node);
                    }
                }
            }
        }
    }

    private void createDisplayAction(){
        for(TextField field: displayFields){
            field.setOnAction((event) -> updateCharacterXML());
        }

    }

    private void loadTabPaneContent() {
        try{
            parent = sheet.load();
            testTab = (TabPane)parent.getCenter();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public BorderPane getSheet() {
        return parent;
    }

    private void updateCharacterXML(){
        /*character.setCharacterName(displayCharName.getText());
        character.setRace(displayRace.getText());
        character.setClassName(displayClass.getText());
        character.setAlignment(displayAlignment.getText());
        character.setEXP(displayExp.getText());
        character.setAge(displayAge.getText());
        character.setSize(displaySize.getText());
        character.setHeight(displayHeight.getText());
        character.setMaxHp(displayMaxHp.getText());
        character.setCurrentHp(displayCurrentHp.getText());
        character.setStats(String.format("%s,%s,%s,%s,%s,%s",
                displayStr.getText(),displayDex.getText(),displayCon.getText(),
                displayInt.getText(),displayWis.getText(),displayCha.getText()));
        updateCharacterView();*/
    }

    private void updateCharacterView(){
       updateField("displayCharName", character.getCharacterName());
        updateField("displayRace", character.getRace());
        updateField("displayClass", character.getClassName());
        updateField("displayAlignment", character.getAlignment());
        updateField("displayExp", character.getEXP());
        updateField("displayAge", character.getAge());
        updateField("displaySize", character.getSize());
        updateField("displayHeight", character.getHeight());
        updateField("displayMaxHp", character.getMaxHP());
        updateField("displayCurrentHp", character.getCurrentHp());
        updateField("displayStr", String.valueOf(character.getStats()[0]));
//        updateField("displayStrMod", getModifier(character.getStats()[0]));
        updateField("displayDex", String.valueOf(character.getStats()[1]));
        //updateField("displayDexMod", getModifier(character.getStats()[1]));
        updateField("displayCon", String.valueOf(character.getStats()[2]));
        //updateField("displayConMod", getModifier(character.getStats()[2]));
        updateField("displayInt", String.valueOf(character.getStats()[3]));
        //updateField("displayIntMod", getModifier(character.getStats()[3]));
        updateField("displayWis", String.valueOf(character.getStats()[4]));
        //updateField("displayWisMod", getModifier(character.getStats()[4]));
        updateField("displayCha", String.valueOf(character.getStats()[5]));
        //updateField("displayChaMod", getModifier(character.getStats()[5]));
        //updateSkillModifiers();
    }

    private String getModifier(int stat){
        return String.valueOf((int)Math.floor((stat-10)/2.0));
    }


    private void updateField(String field, String property) {
        TextField toEdit = searchForID(field);
        toEdit.setText(property);
    }
}
