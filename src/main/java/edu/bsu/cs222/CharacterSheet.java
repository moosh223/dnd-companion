package edu.bsu.cs222;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.Document;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterSheet {
    private FXMLLoader sheet;
    private PlayerCharacter character;
    private BorderPane parent;
    private TabPane testTab;
    private List<TextField> displayFields = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();
    private List<String> strSkills = new ArrayList<>();
    private List<String> dexSkills = new ArrayList<>();
    private List<String> conSkills = new ArrayList<>();
    private List<String> intSkills = new ArrayList<>();
    private List<String> wisSkills = new ArrayList<>();
    private List<String> chaSkills = new ArrayList<>();
    private List<List<String>> skillList = new ArrayList<>();


    public CharacterSheet(PlayerCharacter character) {
        sheet = new FXMLLoader(getClass().getClassLoader().getResource("CharacterTab.fxml"));
        this.character = character;
        loadTabPaneContent();
        buildDisplayFields();
        createDisplayAction();
        updateCharacterView();
        addDisplayFocusListeners();
    }

    private TextField searchFields(String searchQuery) {
        for (TextField field : displayFields) {
            if (field.getId().equals(searchQuery)) {
                return field;
            }
        }
        return null;
    }
    private Label searchLabels(String searchQuery) {
        for(Label label: labels){
            if(label.getId().equals(searchQuery)){
                return label;
            }
        }
        return null;
    }

    private void createSkillLists() {
        strSkills.add("displayAthleticsMod");
        dexSkills.addAll(Arrays.asList("displayAcrobaticsMod", "displaySleightOfHandMod","displayStealthMod"));
        intSkills.addAll(Arrays.asList("displayArcanaMod", "displayHistoryMod", "displayInvestigationMod","displayNatureMod","displayReligionMod"));
        wisSkills.addAll(Arrays.asList("displayAnimalHandlingMod", "displayInsightMod","displayMedicineMod","displayPerceptionMod","displaySurvivalMod"));
        chaSkills.addAll(Arrays.asList("displayDeceptionMod","displayIntimidationMod","displayPerformanceMod", "displayPersuasionMod"));
        skillList.addAll(Arrays.asList(strSkills,dexSkills,conSkills,intSkills,wisSkills,chaSkills));

    }

    private Method searchCharacterMethods(String searchQuery){

        for(Method method: PlayerCharacter.class.getMethods()){
            if(method.getName().equals(searchQuery)){
                return  method;
            }
        }
        return null;
    }

    private void buildDisplayFields() {
        for(Tab tab :testTab.getTabs()){
            Pane paneContent = (Pane)tab.getContent();
            for(Node node: paneContent.getChildren()){
                if(node.getId() != null) try {
                    displayFields.add((TextField) node);
                } catch (ClassCastException e) {
                    labels.add((Label) node);
                }
            }
        }
    }

    private void addDisplayFocusListeners() {
        for(TextField field: displayFields){
            field.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {
                    updateCharacterXML();
                }
            });
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

    private void updateCharacterView(){
        updateField("displayCharName", character.getCharacterName());
        updateField("displayRace", character.getRace());
        updateField("displayClassName", character.getClassName());
        updateField("displayAlignment", character.getAlignment());
        updateField("displayExp", character.getEXP());
        updateField("displayAge", character.getAge());
        updateField("displaySize", character.getSize());
        updateField("displayHeight", character.getHeight());
        updateField("displayMaxHp", character.getMaxHP());
        updateField("displayCurrentHp", character.getCurrentHp());
        updateField("displayStr", String.valueOf(character.getStats()[0]));
        updateLabel("displayStrMod", getModifier(character.getStats()[0]));
        updateField("displayDex", String.valueOf(character.getStats()[1]));
        updateLabel("displayDexMod", getModifier(character.getStats()[1]));
        updateField("displayCon", String.valueOf(character.getStats()[2]));
        updateLabel("displayConMod", getModifier(character.getStats()[2]));
        updateField("displayInt", String.valueOf(character.getStats()[3]));
        updateLabel("displayIntMod", getModifier(character.getStats()[3]));
        updateField("displayWis", String.valueOf(character.getStats()[4]));
        updateLabel("displayWisMod", getModifier(character.getStats()[4]));
        updateField("displayCha", String.valueOf(character.getStats()[5]));
        updateLabel("displayChaMod", getModifier(character.getStats()[5]));
        //updateSkillModifiers();
    }

    private void updateCharacterXML(){
        writeField("setCharacterName","displayCharName");
        writeField("setRace","displayRace");
        writeField("setClassName","displayClassName");
        writeField("setAlignment","displayAlignment");
        writeField("setEXP","displayExp");
        writeField("setAge","displayAge");
        writeField("setSize","displaySize");
        writeField("setHeight","displayHeight");
        writeField("setMaxHp","displayMaxHp");
        writeField("setCurrentHp","displayCurrentHp");
        writeStats(String.format("%s,%s,%s,%s,%s,%s",
                searchFields("displayStr").getText(),searchFields("displayDex").getText(),
                searchFields("displayCon").getText(),searchFields("displayInt").getText(),
                searchFields("displayWis").getText(),searchFields("displayCha").getText()
        ));

        //character.setStats(String.format("%s,%s,%s,%s,%s,%s",
        //        displayStr.getText(),displayDex.getText(),displayCon.getText(),
        //        displayInt.getText(),displayWis.getText(),displayCha.getText()));*/
        updateCharacterView();
    }

    private void writeStats(String stats) {
        try{
            Method toEdit = searchCharacterMethods("setStats");
            toEdit.invoke(character,stats);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String getModifier(int stat){
        return String.valueOf((int)Math.floor((stat-10)/2.0));
    }


    private void updateField(String field, String property) {
        TextField toEdit = searchFields(field);
        toEdit.setText(property);
    }

    private void updateLabel(String field, String property) {
        Label toEdit = searchLabels(field);
        toEdit.setText(property);
    }

    private void writeField(String field, String property) {
        try{
            Method toEdit = searchCharacterMethods(field);
            toEdit.invoke(character,searchFields(property).getText());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /*private void updateSkillModifiers(){
        for(List<String> skill: skillList){
            setSkillModifiers(skill,character.getStats()[skillList.indexOf(skill)]);
        }
    }*/
    private void setSkillModifiers(List<Label> skillList, int stat){
        for(Label skill : skillList){
            skill.setText(getModifier(stat));
        }
    }

    public Document getXMLDoc() {
        return character.getXML();
    }

    public PlayerCharacter getCharacter() {
        return character;
    }
}
