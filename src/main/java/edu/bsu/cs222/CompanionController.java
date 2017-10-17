package edu.bsu.cs222;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanionController {

    @FXML private BorderPane welcomePane;
    @FXML private TabPane characterPane;
    @FXML private BorderPane charTypePane;
    @FXML private BorderPane loadPane;
    @FXML private BorderPane namePane;
    @FXML private AnchorPane racePane;
    @FXML private AnchorPane languagePane;
    @FXML private AnchorPane classPane;
    @FXML private AnchorPane statPane;
    @FXML private Label nameErrorLabel;
    @FXML private Label raceErrorLabel;
    @FXML private Label languageErrorLabel;
    @FXML private Label classErrorLabel;
    @FXML private Label statErrorLabel;
    @FXML private Label displayStrMod;
    @FXML private Label displayDexMod;
    @FXML private Label displayConMod;
    @FXML private Label displayIntMod;
    @FXML private Label displayWisMod;
    @FXML private Label displayChaMod;
    @FXML private TextField playerNameTextBox;
    @FXML private TextField characterNameTextBox;
    @FXML private TextField raceTextBox;
    @FXML private TextField ageTextBox;
    @FXML private TextField heightTextBox;
    @FXML private TextField speedTextBox;
    @FXML private TextField classTextBox;
    @FXML private TextField hpTextBox;
    @FXML private TextArea languageTextBox;
    @FXML private TextField strTextBox;
    @FXML private TextField dexTextBox;
    @FXML private TextField conTextBox;
    @FXML private TextField wisTextBox;
    @FXML private TextField intTextBox;
    @FXML private TextField chaTextBox;
    @FXML private TextField displayCharName;
    @FXML private TextField displayRace;
    @FXML private TextField displayClass;
    @FXML private TextField displayAlignment;
    @FXML private TextField displayExp;
    @FXML private TextField displayAge;
    @FXML private TextField displaySize;
    @FXML private TextField displayHeight;
    @FXML private TextField displayMaxHp;
    @FXML private TextField displayCurrentHp;
    @FXML private TextField displayStr;
    @FXML private TextField displayDex;
    @FXML private TextField displayCon;
    @FXML private TextField displayInt;
    @FXML private TextField displayWis;
    @FXML private TextField displayCha;
    @FXML private ComboBox<String> firstAbilityModified;
    @FXML private ComboBox<String> firstModifiedScore;
    @FXML private ComboBox<String> secondAbilityModified;
    @FXML private ComboBox<String> secondModifiedScore;
    @FXML private ComboBox<String> alignmentBox;
    @FXML private ComboBox<String> sizeBox;
    @FXML private ComboBox<String> primaryAbilityOne;
    @FXML private ComboBox<String> primaryAbilityTwo;
    @FXML private ComboBox<String> savingThrowOne;
    @FXML private ComboBox<String> savingThrowTwo;
    @FXML private ListView<String> characterLoadList;
    private List<TextField> pageTextFields = new ArrayList<>();
    private List<ComboBox> pageComboBoxes = new ArrayList<>();
    private PlayerCharacter character;


    @FXML
    public void playerButtonPress(){
        welcomePane.setVisible(false);
        charTypePane.setVisible(true);
    }

    @FXML
    public void dmButtonPress(){
        welcomePane.setVisible(false);
    }
    @FXML
    public void loadButtonPress(){
        charTypePane.setVisible(false);
        loadPane.setVisible(true);
        populateLoadTable();
    }

    @FXML
    public void loadSelectedCharacter(){
        character = new PlayerCharacter(characterLoadList.getSelectionModel().getSelectedItem()+".xml");
        updateCharacterView();
        loadPane.setVisible(false);
        characterPane.setVisible(true);
    }

    private void populateLoadTable() {
        ObservableList<String> fileList= FXCollections.observableArrayList(getFileList());
        characterLoadList.getItems().addAll(fileList);
    }

    private ArrayList<String> getFileList() {
        File folder = new File("src/main/resources/characters");
        File[] fileList = folder.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();
        assert fileList != null;
        for(File file: fileList){
            System.out.println(file.getName());
            fileNames.add(file.getName().replace(".xml",""));
        }
        return fileNames;
    }

    @FXML
    public void newButtonPress(){
        pageTextFields.addAll(Arrays.asList(playerNameTextBox,characterNameTextBox));
        charTypePane.setVisible(false);
        namePane.setVisible(true);
    }
    @FXML
    public void nameNextButtonPress(){
        if(!pageFilled()) {
            nameErrorLabel.setVisible(true);
        }
        else {
            pageTextFields.addAll(
                    Arrays.asList(raceTextBox, ageTextBox, heightTextBox, speedTextBox));
            pageComboBoxes.addAll(
                    Arrays.asList(firstAbilityModified,firstModifiedScore,
                            secondAbilityModified,secondModifiedScore,alignmentBox,sizeBox));
            namePane.setVisible(false);
            racePane.setVisible(true);
        }
    }
    @FXML
    public void raceNextButtonPress(){
        if(!pageFilled()){
            raceErrorLabel.setVisible(true);
        }
        else{
            racePane.setVisible(false);
            languagePane.setVisible(true);
        }
    }
    @FXML
    public void languageNextButtonPress(){
        if (!pageFilled() || languageTextBox.getText().equals("")) {
            languageErrorLabel.setVisible(true);
        }
        else{
            pageTextFields.addAll(
                    Arrays.asList(classTextBox, hpTextBox));
            pageComboBoxes.addAll(
                    Arrays.asList(savingThrowOne,savingThrowTwo,primaryAbilityOne,primaryAbilityTwo));
            languagePane.setVisible(false);
            classPane.setVisible(true);
        }
    }
    @FXML
    public void classNextButtonPress(){
        if(!pageFilled()){
            classErrorLabel.setVisible(true);
        }
        else{
            pageTextFields.addAll(
                    Arrays.asList(strTextBox, dexTextBox,conTextBox,
                            intTextBox,wisTextBox,chaTextBox));
            classPane.setVisible(false);
            statPane.setVisible(true);
        }
    }
    @FXML
    public void statNextButtonPress(){
        if (!pageFilled()){
            statErrorLabel.setVisible(true);
        }
        else {
            buildNewCharacter();
            updateCharacterView();
            statPane.setVisible(false);
            characterPane.setVisible(true);
        }
    }

    @FXML
    public void rcvButtonPress(){
        charTypePane.setVisible(false);
        characterPane.setVisible(true);
    }


    private boolean pageFilled(){
        return testPageComboBoxes() && testPageTextFields();
    }

    private boolean testPageTextFields(){
        for(TextField field: pageTextFields){
            if(field.getText().equals("")){
                return false;
            }
        }
        return true;
    }

    private boolean testPageComboBoxes(){
        for(ComboBox box: pageComboBoxes){
            try{
                box.getValue().equals(null);
            }catch(NullPointerException npe){
                return false;
            }
        }
        return true;
    }

    private void buildNewCharacter() {
        character = new PlayerCharacter(playerNameTextBox.getText(), characterNameTextBox.getText());
        character.setPlayerName(playerNameTextBox.getText());
        character.setCharacterName(characterNameTextBox.getText());
        character.setEXP("0");
        character.setRace(raceTextBox.getText());
        character.setAge(ageTextBox.getText());
        character.setAlignment(alignmentBox.getValue());
        character.setSize(sizeBox.getValue());
        character.setHeight(heightTextBox.getText());
        character.setSpeed(speedTextBox.getText());
        character.setLanguages(parseLanguages());
        character.setClassName(classTextBox.getText());
        character.setMaxHp(hpTextBox.getText());
        character.setCurrentHp(hpTextBox.getText());
        String primaryOne = primaryAbilityOne.getValue();
        String primaryTwo = primaryAbilityTwo.getValue();
        String saveThrowOne = savingThrowOne.getValue();
        String saveThrowTwo = savingThrowTwo.getValue();
        character.setStats(String.format("%s,%s,%s,%s,%s,%s",
                strTextBox.getText(),dexTextBox.getText(),conTextBox.getText(),
                intTextBox.getText(),wisTextBox.getText(),chaTextBox.getText()));
        parseAbilityModifiers(firstAbilityModified.getValue(),firstModifiedScore.getValue());
        parseAbilityModifiers(secondAbilityModified.getValue(),secondModifiedScore.getValue());
    }

    private void updateCharacterView(){
        displayCharName.setText(character.getCharacterName());
        displayRace.setText(character.getRace());
        displayClass.setText(character.getClassName());
        displayAlignment.setText(character.getAlignment());
        displayExp.setText(character.getEXP());
        displayAge.setText(character.getAge());
        displaySize.setText(character.getSize());
        displayHeight.setText(character.getHeight());
        displayMaxHp.setText(character.getMaxHP());
        displayCurrentHp.setText(character.getCurrentHp());
        displayStr.setText(String.valueOf(character.getStats()[0]));
        displayStrMod.setText(String.valueOf((int)Math.floor((character.getStats()[0]-10)/2.0)));
        displayDex.setText(String.valueOf(character.getStats()[1]));
        displayDexMod.setText(String.valueOf((int)Math.floor((character.getStats()[1]-10)/2.0)));
        displayCon.setText(String.valueOf(character.getStats()[2]));
        displayConMod.setText(String.valueOf((int)Math.floor((character.getStats()[2]-10)/2.0)));
        displayInt.setText(String.valueOf(character.getStats()[3]));
        displayIntMod.setText(String.valueOf((int)Math.floor((character.getStats()[3]-10)/2.0)));
        displayWis.setText(String.valueOf(character.getStats()[4]));
        displayWisMod.setText(String.valueOf((int)Math.floor((character.getStats()[4]-10)/2.0)));
        displayCha.setText(String.valueOf(character.getStats()[5]));
        displayChaMod.setText(String.valueOf((int)Math.floor((character.getStats()[5]-10)/2.0)));
    }

    private String parseLanguages() {
        String[] languages = languageTextBox.getText().split("\n");
        StringBuilder languageTag= new StringBuilder();
        for(int i=0; i< languages.length-1;i++){
            languageTag.append(languages[i]).append(",");
        }
        languageTag.append(languages[languages.length - 1]);
        return languageTag.toString().replace("null"," ").trim();
    }

    private enum StatName{
        Strength(0),
        Dexterity(1),
        Constitution(2),
        Intelligence(3),
        Wisdom(4),
        Charisma(5);

        private int stat;
        StatName(int stat) {
            this.stat = stat;
        }
        private int getValue(){
            return stat;
        }
    }
    private void parseAbilityModifiers(String ability, String score){
        for(StatName statName: StatName.values()){
            if(ability.equals(statName.toString())){
                character.setStat(statName.getValue(),character.getStats()[statName.getValue()]
                        +Integer.parseInt(score));
            }else if(ability.equals("None")){
                return;
            }
        }
    }

    @FXML
    public void openPlayersHandbook(){
        if(Desktop.isDesktopSupported()){
            new Thread(() -> {
                try {
                    File file = new File("Players_Handbook.pdf");
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
