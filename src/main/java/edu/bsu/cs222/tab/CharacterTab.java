package edu.bsu.cs222.tab;

import edu.bsu.cs222.CharacterParser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterTab extends Tab{
    private FXMLLoader sheet;
    private CharacterParser character;
    private TabPane tabPane;
    private AnchorPane parent;
    private List<TextField> displayFields = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();
    private List<String> strSkills = new ArrayList<>();
    private List<String> dexSkills = new ArrayList<>();
    private List<String> conSkills = new ArrayList<>();
    private List<String> intSkills = new ArrayList<>();
    private List<String> wisSkills = new ArrayList<>();
    private List<String> chaSkills = new ArrayList<>();
    private List<List<String>> skillLists = new ArrayList<>();
    public boolean updateFlag = false;

    public CharacterTab(CharacterParser character) {
        sheet = new FXMLLoader(getClass().getClassLoader().getResource("fxml/CharacterTab.fxml"));
        this.character = character;
        setText(character.readTag("name"));
        init();
        updateCharacterView();
    }

    private void init() {
        loadPaneContent();
        buildDataFields();
        setActions();
    }

    private void setActions() {
        createDisplayAction();
        addDisplayFocusListeners();
    }

    private void addDisplayFocusListeners() {
        for(TextField field: displayFields){
            field.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {
                    updateFlag = true;
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

    private void buildDataFields() {
        buildDisplayFields();
        buildSkillFields();
        createSkillLists();
    }

    private TextField searchFields(String searchQuery) {
        for (TextField field : displayFields) {
            assert field != null;
            if (field.getId().equals(searchQuery)) {
                return field;
            }
        }
        return null;
    }

    private Label searchLabels(String searchQuery) throws NullPointerException{
        for(Label label: labels){
            if(label.getId().equals(searchQuery)){
                return label;
            }
        }
        throw new NullPointerException();
    }

    private void createSkillLists() {
        strSkills.add("displayAthleticsMod");
        dexSkills.addAll(Arrays.asList("displayAcrobaticsMod", "displaySleightOfHandMod","displayStealthMod"));
        intSkills.addAll(Arrays.asList("displayArcanaMod", "displayHistoryMod", "displayInvestigationMod","displayNatureMod","displayReligionMod"));
        wisSkills.addAll(Arrays.asList("displayAnimalHandlingMod", "displayInsightMod","displayMedicineMod","displayPerceptionMod","displaySurvivalMod"));
        chaSkills.addAll(Arrays.asList("displayDeceptionMod","displayIntimidationMod","displayPerformanceMod", "displayPersuasionMod"));
        skillLists.addAll(Arrays.asList(strSkills,dexSkills,conSkills,intSkills,wisSkills,chaSkills));
    }

    private void buildDisplayFields() {
        for(Tab tab : tabPane.getTabs()){
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

    private void buildSkillFields(){
        Tab tab = tabPane.getTabs().get(2);
        GridPane pane = (GridPane)((Pane)tab.getContent()).getChildren().get(0);
        for(Node node: pane.getChildren()){
            if(node.getId() != null) try {
                labels.add((Label) node);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadPaneContent() {
        try{
            parent = sheet.load();
            BorderPane borderPane =  (BorderPane)parent.getChildren().get(0);
            tabPane = (TabPane) borderPane.getCenter();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void updateCharacterView(){
        updateField("displayCharName", character.readTag("name"));
        updateField("displayRace", character.readTag("race"));
        updateField("displayClassName", character.readTag("classname"));
        updateField("displayAlignment", character.readTag("alignment"));
        updateField("displayExp", character.readTag("exp"));
        updateField("displayAge", character.readTag("age"));
        updateField("displaySize", character.readTag("size"));
        updateField("displayHeight", character.readTag("height"));
        updateField("displayMaxHp", character.readTag("maxhp"));
        updateField("displayCurrentHp", character.readTag("currenthp"));
        updateField("displayStr", String.valueOf(character.readTag("stats").split(",")[0]));
        updateLabel("displayStrMod", getModifier(character.readTag("stats").split(",")[0]));
        updateField("displayDex", String.valueOf(character.readTag("stats").split(",")[1]));
        updateLabel("displayDexMod", getModifier(character.readTag("stats").split(",")[1]));
        updateField("displayCon", String.valueOf(character.readTag("stats").split(",")[2]));
        updateLabel("displayConMod", getModifier(character.readTag("stats").split(",")[2]));
        updateField("displayInt", String.valueOf(character.readTag("stats").split(",")[3]));
        updateLabel("displayIntMod", getModifier(character.readTag("stats").split(",")[3]));
        updateField("displayWis", String.valueOf(character.readTag("stats").split(",")[4]));
        updateLabel("displayWisMod", getModifier(character.readTag("stats").split(",")[4]));
        updateField("displayCha", String.valueOf(character.readTag("stats").split(",")[5]));
        updateLabel("displayChaMod", getModifier(character.readTag("stats").split(",")[5]));
        updateSkillModifiers();
    }

    private void updateCharacterXML() {
        character.writeTag("name", searchFields("displayCharName").getText());
        character.writeTag("race", searchFields("displayRace").getText());
        character.writeTag("classname", searchFields("displayClassName").getText());
        character.writeTag("alignment", searchFields("displayAlignment").getText());
        character.writeTag("exp", searchFields("displayExp").getText());
        character.writeTag("age", searchFields("displayAge").getText());
        character.writeTag("size", searchFields("displaySize").getText());
        character.writeTag("height", searchFields("displayHeight").getText());
        character.writeTag("maxhp", searchFields("displayMaxHp").getText());
        character.writeTag("currenthp", searchFields("displayCurrentHp").getText());
        character.writeTag("stats", String.format("%s,%s,%s,%s,%s,%s",
                searchFields("displayStr").getText(), searchFields("displayDex").getText(),
                searchFields("displayCon").getText(), searchFields("displayInt").getText(),
                searchFields("displayWis").getText(), searchFields("displayCha").getText()
        ));
        updateCharacterView();
    }

    private String getModifier(String stat){
        return String.valueOf((int)Math.floor((Integer.parseInt(stat)-10)/2.0));
    }


    private void updateField(String field, String property) {
        TextField toEdit = searchFields(field);
        assert toEdit != null;
        toEdit.setText(property);
    }

    private void updateLabel(String field, String property) {
        Label toEdit = searchLabels(field);
        assert toEdit != null;
        toEdit.setText(property);
    }

    private void updateSkillModifiers(){
        for(List<String> skill: skillLists){
            setSkillModifiers(skill,Integer.parseInt(getModifier(character.readTag("stats").split(",")[skillLists.indexOf(skill)])));
        }
    }
    private void setSkillModifiers(List<String> skillList, int stat){
        for(String skill : skillList){
            Label label = searchLabels(skill);
            assert label != null;
            label.setText(String.valueOf(stat));
        }
    }
    public Node getSheet() {
        return parent;
    }

    public CharacterParser getCharacter() {
        return character;
    }
}
