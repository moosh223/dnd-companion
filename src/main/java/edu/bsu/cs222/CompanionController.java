package edu.bsu.cs222;

import com.sun.javafx.stage.StageHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanionController extends FXMLElements{


    private List<TextField> pageTextFields = new ArrayList<>();
    private List<ComboBox> pageComboBoxes = new ArrayList<>();
    private List<TextField> displayFields = new ArrayList<>();
    private List<Label> skillList = new ArrayList<>();
    private PlayerCharacter character;

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

    private enum SkillName{
        Acrobatics("Dex"),
        Animal_Handling("Wis"),
        Arcana("Int"),
        Athletics("Str"),
        Deception("Cha"),
        History("Int"),
        Insight("Wis"),
        Intimidation("Cha"),
        Investigation("Int"),
        Medicine("Wis"),
        Nature("Int"),
        Perception("Wis"),
        Performance("Cha"),
        Persuasion("Cha"),
        Religion("Int"),
        Sleight_of_Hand("Dex"),
        Stealth("Dex"),
        Survival("Wis");
        
        private String stat;
        SkillName(String stat) {
            this.stat = stat;
        }
        private String getValue(){
            return stat;
        }
    }

    public void initialize(){
        addDisplayFocusListeners();
        createTabListener();
        createSkillList();
    }

    private void createSkillList() {
        for(SkillName skillName:SkillName.values()){
        }
    }

    private void createTabListener() {
        characterPane.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldTab, newTab) -> {
                    if(newTab == newTabButton) {
                        characterPane.getSelectionModel().select(oldTab);
                        createNewTab();
                    }
                });
    }

    private void createNewTab() {
        Tab newTab = new Tab();
        newTab.setText("TestTab");
        characterPane.getTabs().remove(newTabButton);
        characterPane.getTabs().addAll(newTab,newTabButton);
        System.out.println(characterPane.getTabs().size());
    }

    @FXML
    public void closeProgram(){
        System.exit(0);
    }

    @FXML
    public void setDiceRollerVisible(){
        Stage stage;
        if(diceRollerButton.isSelected()){
            stage = new Stage();
            Scene scene = new Scene(new AnchorPane());
            stage.setOnCloseRequest((event) ->
                    diceRollerButton.selectedProperty().setValue(false));
            stage.setScene(scene);
            stage.show();
            return;
        }
            stage = StageHelper.getStages().get(StageHelper.getStages().size()-1);
            stage.close();
    }

    @FXML
    public void playerButtonPress(){
        welcomePane.setVisible(false);
        charTypePane.setVisible(true);
    }

    @FXML
    public void dmButtonPress(){}
    @FXML
    public void loadButtonPress(){
        charTypePane.setVisible(false);
        loadPane.setVisible(true);
        populateLoadTable();
    }

    @FXML
    public void loadSelectedCharacter() {
        character = new PlayerCharacter(characterLoadList.getSelectionModel().getSelectedItem() + ".xml");
        updateCharacterView();
        loadPane.setVisible(false);
        characterPane.setVisible(true);

    }


    private void addDisplayFocusListeners() {
        displayFields.addAll(Arrays.asList(
                displayCharName, displayRace,displayClass,displayAlignment,displayExp,displayAge,displaySize,
                displayHeight,displayMaxHp,displayCurrentHp,displayStr,displayDex,displayCon,displayInt,displayWis,displayCha));
        for(TextField field: displayFields){
            field.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {
                    updateCharacterXML();
                }
            });
        }
    }

    private void populateLoadTable() {
        ObservableList<String> fileList= FXCollections.observableArrayList(getFileList());
        characterLoadList.getItems().addAll(fileList);
    }

    private ArrayList<String> getFileList() {
        File folder = new File("assets/characters");
        File[] fileList = folder.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();
        assert fileList != null;
        for(File file: fileList){
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
            addDisplayFocusListeners();
            statPane.setVisible(false);
            characterPane.setVisible(true);
        }
    }

    @FXML
    public void rcvButtonPress(){
       /*
        charTypePane.setVisible(false);
        characterPane.setVisible(true);
       */
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

    private int updateAbilityModifers(){
        return 0;
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

    @FXML
    private void updateCharacterXML(){
        character.setCharacterName(displayCharName.getText());
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
        updateCharacterView();
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
                    File file = new File("assets/Players_Handbook.pdf");
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
