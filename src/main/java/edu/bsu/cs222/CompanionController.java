package edu.bsu.cs222;

import com.sun.javafx.stage.StageHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanionController{

    //ViewPanes
    @FXML public BorderPane welcomePane;
    @FXML public TabPane characterPane;
    @FXML public BorderPane charTypePane;
    @FXML public BorderPane loadPane;
    @FXML public BorderPane namePane;
    @FXML public AnchorPane racePane;
    @FXML public AnchorPane languagePane;
    @FXML public AnchorPane classPane;
    @FXML public AnchorPane statPane;

    //ErrorLabels
    @FXML public Label nameErrorLabel;
    @FXML public Label raceErrorLabel;
    @FXML public Label languageErrorLabel;
    @FXML public Label classErrorLabel;
    @FXML public Label statErrorLabel;

    //Modifier Labels
    @FXML public Label displayStrMod;
    @FXML public Label displayDexMod;
    @FXML public Label displayConMod;
    @FXML public Label displayIntMod;
    @FXML public Label displayWisMod;
    @FXML public Label displayChaMod;

    //Skill Labels
    @FXML public Label displayAcrobaticsMod;
    @FXML public Label displayAnimalHandlingMod;
    @FXML public Label displayArcanaMod;
    @FXML public Label displayAthleticsMod;
    @FXML public Label displayDeceptionMod;
    @FXML public Label displayHistoryMod;
    @FXML public Label displayInsightMod;
    @FXML public Label displayIntimidationMod;
    @FXML public Label displayInvestigationMod;
    @FXML public Label displayMedicineMod;
    @FXML public Label displayNatureMod;
    @FXML public Label displayPerceptionMod;
    @FXML public Label displayPerformanceMod;
    @FXML public Label displayPersuasionMod;
    @FXML public Label displayReligionMod;
    @FXML public Label displaySleightOfHandMod;
    @FXML public Label displayStealthMod;
    @FXML public Label displaySurvivalMod;
    private List<Label> strSkills = new ArrayList<>();
    private List<Label> dexSkills = new ArrayList<>();
    private List<Label> conSkills = new ArrayList<>();
    private List<Label> intSkills = new ArrayList<>();
    private List<Label> wisSkills = new ArrayList<>();
    private List<Label> chaSkills = new ArrayList<>();
    private List<List<Label>> skillList = new ArrayList<>();

    //Text Fields for Character Creator
    @FXML public TextField playerNameTextBox;
    @FXML public TextField characterNameTextBox;
    @FXML public TextField raceTextBox;
    @FXML public TextField ageTextBox;
    @FXML public TextField heightTextBox;
    @FXML public TextField speedTextBox;
    @FXML public TextField classTextBox;
    @FXML public TextField hpTextBox;
    @FXML public TextArea languageTextBox;
    @FXML public TextField strTextBox;
    @FXML public TextField dexTextBox;
    @FXML public TextField conTextBox;
    @FXML public TextField wisTextBox;
    @FXML public TextField intTextBox;
    @FXML public TextField chaTextBox;

    @FXML public TextField displayCharName;
    @FXML public TextField displayRace;
    @FXML public TextField displayClass;
    @FXML public TextField displayAlignment;
    @FXML public TextField displayExp;
    @FXML public TextField displayAge;
    @FXML public TextField displaySize;
    @FXML public TextField displayHeight;
    @FXML public TextField displayMaxHp;
    @FXML public TextField displayCurrentHp;
    @FXML public TextField displayStr;
    @FXML public TextField displayDex;
    @FXML public TextField displayCon;
    @FXML public TextField displayInt;
    @FXML public TextField displayWis;
    @FXML public TextField displayCha;
    private List<TextField> creatorTextFields = new ArrayList<>();

    //Combo Boxes for Character Creator
    @FXML public ComboBox<String> firstAbilityModified;
    @FXML public ComboBox<String> firstModifiedScore;
    @FXML public ComboBox<String> secondAbilityModified;
    @FXML public ComboBox<String> secondModifiedScore;
    @FXML public ComboBox<String> alignmentBox;
    @FXML public ComboBox<String> sizeBox;
    @FXML public ComboBox<String> primaryAbilityOne;
    @FXML public ComboBox<String> primaryAbilityTwo;
    @FXML public ComboBox<String> savingThrowOne;
    @FXML public ComboBox<String> savingThrowTwo;
    private List<ComboBox> creatorComboBoxes = new ArrayList<>();

    //Miscellaneous Elements
    @FXML public ListView<String> characterLoadList;
    @FXML public ContextMenu tabContextMenu;
    @FXML public CheckBox diceRollerButton;
    @FXML public Tab characterSheetTab;
    @FXML public Tab newTabButton;

    private List<TextField> displayFields = new ArrayList<>();
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

    public void initialize(){
        //addDisplayFocusListeners();
        addNewTabListener();
    }

    private void createSkillLists() {
        strSkills.add(displayAthleticsMod);
        dexSkills.addAll(Arrays.asList(displayAcrobaticsMod, displaySleightOfHandMod,displayStealthMod));
        intSkills.addAll(Arrays.asList(displayArcanaMod, displayHistoryMod, displayInvestigationMod,displayNatureMod,displayReligionMod));
        wisSkills.addAll(Arrays.asList(displayAnimalHandlingMod, displayInsightMod,displayMedicineMod,displayPerceptionMod,displaySurvivalMod));
        chaSkills.addAll(Arrays.asList(displayDeceptionMod,displayIntimidationMod,displayPerformanceMod, displayPersuasionMod));
        skillList.addAll(Arrays.asList(strSkills,dexSkills,conSkills,intSkills,wisSkills,chaSkills));

    }

    private void updateSkillModifiers(){
        for(List<Label> skill: skillList){
            setSkillModifiers(skill,character.getStats()[skillList.indexOf(skill)]);
        }
    }

    private void setSkillModifiers(List<Label> skillList, int stat){
        for(Label skill : skillList){
            skill.setText(getModifier(stat));
        }
    }

    private void addNewTabListener() {
        characterPane.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldTab, newTab) -> {
                    if(newTab == newTabButton) {
                        characterPane.getSelectionModel().select(oldTab);
                        createNewTab();
                    }
                });
    }

    private void addDisplayFocusListeners() {
        displayFields.addAll(Arrays.asList(
                displayCharName, displayRace,displayClass,displayAlignment,displayExp,
                displayAge,displaySize, displayHeight,displayMaxHp,displayCurrentHp,
                displayStr,displayDex,displayCon,displayInt,displayWis,displayCha));
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

    private void createNewTab() {
        Tab newTab = new Tab("New Tab");
            CharacterSheet sheet = new CharacterSheet();
            newTab.setContent(sheet.getSheet());
        characterPane.getTabs().remove(newTabButton);
        characterPane.getTabs().addAll(newTab,newTabButton);
    }

    @FXML
    public void closeProgram(){
        System.exit(0);
    }

    @FXML
    public void tabCloseAction(){
        System.out.println();
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
    public void loadButtonAction(){
        charTypePane.setVisible(false);
        loadPane.setVisible(true);
        populateLoadTable();
    }

    @FXML
    public void loadSelectedCharacter() {
        character = new PlayerCharacter(characterLoadList.getSelectionModel().getSelectedItem() + ".xml");
        createSkillLists();
        //updateCharacterView();
        loadPane.setVisible(false);
        characterPane.setVisible(true);

    }

    private void populateLoadTable() {
        ObservableList<String> fileList = FXCollections.observableArrayList(getFileList());
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
        creatorTextFields.addAll(Arrays.asList(playerNameTextBox,characterNameTextBox));
        charTypePane.setVisible(false);
        namePane.setVisible(true);
    }
    @FXML
    public void nameNextButtonPress(){
        if(!isPageFilled()) {
            nameErrorLabel.setVisible(true);
        }
        else {
            creatorTextFields.addAll(
                    Arrays.asList(raceTextBox, ageTextBox, heightTextBox, speedTextBox));
            creatorComboBoxes.addAll(
                    Arrays.asList(firstAbilityModified,firstModifiedScore,
                            secondAbilityModified,secondModifiedScore,alignmentBox,sizeBox));
            namePane.setVisible(false);
            racePane.setVisible(true);
        }
    }
    @FXML
    public void raceNextButtonPress(){
        if(!isPageFilled()){
            raceErrorLabel.setVisible(true);
        }
        else{
            racePane.setVisible(false);
            languagePane.setVisible(true);
        }
    }
    @FXML
    public void languageNextButtonPress(){
        if (!isPageFilled() || languageTextBox.getText().equals("")) {
            languageErrorLabel.setVisible(true);
        }
        else{
            creatorTextFields.addAll(
                    Arrays.asList(classTextBox, hpTextBox));
            creatorComboBoxes.addAll(
                    Arrays.asList(savingThrowOne,savingThrowTwo,primaryAbilityOne,primaryAbilityTwo));
            languagePane.setVisible(false);
            classPane.setVisible(true);
        }
    }
    @FXML
    public void classNextButtonPress(){
        if(!isPageFilled()){
            classErrorLabel.setVisible(true);
        }
        else{
            creatorTextFields.addAll(
                    Arrays.asList(strTextBox, dexTextBox,conTextBox,
                            intTextBox,wisTextBox,chaTextBox));
            classPane.setVisible(false);
            statPane.setVisible(true);
        }
    }
    @FXML
    public void statNextButtonPress(){
        if (!isPageFilled()){
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


    private boolean isPageFilled(){
        return isComboBoxFilled() && isTextFieldFilled();
    }

    private boolean isTextFieldFilled(){
        for(TextField field: creatorTextFields){
            if(field.getText().equals("")){
                return false;
            }
        }
        return true;
    }

    private boolean isComboBoxFilled(){
        for(ComboBox box: creatorComboBoxes){
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
        displayStrMod.setText(getModifier(character.getStats()[0]));
        displayDex.setText(String.valueOf(character.getStats()[1]));
        displayDexMod.setText(getModifier(character.getStats()[1]));
        displayCon.setText(String.valueOf(character.getStats()[2]));
        displayConMod.setText(getModifier(character.getStats()[2]));
        displayInt.setText(String.valueOf(character.getStats()[3]));
        displayIntMod.setText(getModifier(character.getStats()[3]));
        displayWis.setText(String.valueOf(character.getStats()[4]));
        displayWisMod.setText(getModifier(character.getStats()[4]));
        displayCha.setText(String.valueOf(character.getStats()[5]));
        displayChaMod.setText(getModifier(character.getStats()[5]));
        updateSkillModifiers();

    }

    private String getModifier(int stat){
        return String.valueOf((int)Math.floor((stat-10)/2.0));
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
