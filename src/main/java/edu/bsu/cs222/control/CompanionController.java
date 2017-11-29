package edu.bsu.cs222.control;

import edu.bsu.cs222.net.*;
import edu.bsu.cs222.tab.*;
import edu.bsu.cs222.util.CampaignParser;
import edu.bsu.cs222.util.CharacterParser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class CompanionController {

    //ViewPanes
    @FXML public BorderPane welcomePane;
    @FXML public TabPane sheetPane;
    @FXML public BorderPane charTypePane;
    @FXML public BorderPane charTypePaneDM;
    @FXML public BorderPane loadPane;
    @FXML public BorderPane loadCampaignPane;
    @FXML public BorderPane newCampaignPane;
    @FXML public BorderPane namePane;
    @FXML public AnchorPane racePane;
    @FXML public AnchorPane languagePane;
    @FXML public AnchorPane classPane;
    @FXML public AnchorPane statPane;
    @FXML public AnchorPane rcvPane;
    //ErrorLabels
    @FXML public Label nameErrorLabel;
    @FXML public Label raceErrorLabel;
    @FXML public Label languageErrorLabel;
    @FXML public Label classErrorLabel;
    @FXML public Label statErrorLabel;
    @FXML public Label networkLabel;

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
    private List<TextField> creatorTextFields = new ArrayList<>();

    // Text Fields for New CampaignParser
    @FXML public TextField campaignTitle;
    @FXML public TextField campaignSummary;

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
    @FXML public ListView<CampaignParser> campaignLoadList;
    @FXML public ListView<CharacterParser> characterLoadList;
    @FXML public ListView<CharacterParser> sendView;
    @FXML public CheckBox diceRollerButton;
    @FXML public MenuItem newCharacterSheetMenuItem;
    //@FXML public MenuItem loadPrevCharacters;
    @FXML public MenuItem newJournalMenuItem;
    @FXML public Menu newTabMenu;
    @FXML public TextField ipConnect;

    private final String characterDir = "assets/characters/";
    private final String campaignDir = "assets/campaigns/";
    private String currentCampaignDir;
    private String currentCharacterDir;
    private ClientNode clientParser;
    private boolean isPlayer = true;
    private Stage diceRoller = new Stage();
    private ArrayList<ClientNode> clients = new ArrayList<>();

    private enum StatName {
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

        private int getValue() {
            return stat;
        }
    }

    public void initialize() {
        newTabMenu.setDisable(true);
    }

    /**Creates a new character sheet tab
     * @author Josh Mooshian <jmmooshian@bsu.edu>
     * @param character Character used to create the new character sheet
     * @see #createJournalTab(String) createJournalTab
     */
    private void createCharacterSheetTab(CharacterParser character) {
        CharacterTab characterTab = new CharacterTab(character);
        characterTab.setClosable(false);
        sheetPane.getTabs().add(characterTab);
    }
    private void createCharacterSheetTab(CharacterParser character,ClientNode node) {
        CharacterTab characterTab = new CharacterTab(character);
        characterTab.setNode(node);
        characterTab.setClosable(false);
        sheetPane.getTabs().add(characterTab);
    }

    private CharacterTab makeCharacterTab(CharacterParser character){
        for(Tab tab: sheetPane.getTabs()){
            CharacterTab characterTab = (CharacterTab)tab;
            if(characterTab.getCharacter().equals(character)){
                return characterTab;
            }
        }
        return null;
    }


    /**Creates a new journal tab
     * @author Josh Mooshian <jmmooshian@bsu.edu>
     * @param filepath Path to the journal file
     */
    private void createJournalTab(String filepath) {
        JournalTab journalTab = new JournalTab(filepath);
        sheetPane.getTabs().add(journalTab);
    }


    private void createSpellSheetTab(){
        Tab newTab = new Tab("Spell Sheet");
        SpellTab spellTab = new SpellTab();
        newTab.setContent(spellTab.getSheet());
        sheetPane.getTabs().add(newTab);
    }

    @FXML
    public void newCharacterSheetMenuAction(){
        if(sheetPane.isVisible()&& !isPlayer) {
            String newChar = makeNewCharacterFolder(String.format("%s/%s/characters/",campaignDir, currentCampaignDir));
            createCharacterSheetTab(new CharacterParser(String.format("%s/%s/characters/%s/%s",campaignDir, currentCampaignDir,newChar,newChar)));
        }
    }

    @FXML
    public void newJournalMenuAction(){
        if(sheetPane.isVisible()) {
            createJournalTab(String.format("%s/%d.jour", currentCharacterDir,System.nanoTime()));
        }
    }

    @FXML
    public void newSpellSheetMenuAction(){
        if(sheetPane.isVisible()){
            createSpellSheetTab();
        }
    }

    @FXML
    public void closeProgram(){
        System.exit(0);
    }

    @FXML
    public void setDiceRollerVisible() throws IOException {
        if (diceRollerButton.isSelected()) {
            URL loadDir = getClass().getClassLoader().getResource("fxml/DiceRoll.fxml");
            assert loadDir != null;
            Parent parent = FXMLLoader.load(loadDir);
            Scene scene=new Scene(parent);
            diceRoller.setTitle("Dice Roller");
            diceRoller.getIcons().add(new Image("icons/DiceIcon.png"));
            parent.getStylesheets().clear();
            parent.getStylesheets().add("themes/default.css");
            diceRoller.setOnCloseRequest((e) -> diceRollerButton.selectedProperty().setValue(false));
            diceRoller.setResizable(false);
            diceRoller.setScene(scene);
            diceRoller.show();
            return;
        }
        diceRoller.close();
    }

    @FXML
    public void playerButtonPress() {
        welcomePane.setVisible(false);
        charTypePane.setVisible(true);
        isPlayer = true;
    }
    @FXML
    public void dmButtonPress() {
        welcomePane.setVisible(false);
        charTypePaneDM.setVisible(true);
        isPlayer = false;
    }
    @FXML
    private void startServer() {
        try {
            Server server = new Server(clients);
            networkLabel.setText(server.getLANAddress().toString());
            server.start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void sendServerMessage(){
        for(ClientNode node: clients){
            CharacterParser parser = new CharacterParser(currentCampaignDir+"/characters/"+node.getName());
            createCharacterSheetTab(parser,node);
            makeCharacterTab(parser);
        }
    }

    @FXML
    public void loadButtonAction() {
        charTypePane.setVisible(false);
        loadPane.setVisible(true);
        populateLoadTable();
    }

    @FXML
    public void loadButtonCampaign() {
        charTypePaneDM.setVisible(false);
        loadCampaignPane.setVisible(true);
        populateLoadTableDM();
    }

    private void populateLoadTable() {
        characterLoadList.setItems(FXCollections.observableArrayList(getCharacters()));
    }

    private void populateLoadTableDM() {
        campaignLoadList.setItems(FXCollections.observableArrayList(getCampaigns()));
    }

    private void populateSendTable() {
        sendView.setItems(FXCollections.observableArrayList(getCharacters()));
    }

    private ArrayList<CharacterParser> getCharacters() {
        ArrayList<CharacterParser> fileNames = new ArrayList<>();
        File path = new File(characterDir);
        for(File file: getXMLFiles(path.listFiles())){
            fileNames.add(new CharacterParser(file.getPath()));
        }
        return fileNames;
    }

    private ArrayList<CampaignParser> getCampaigns() {
        ArrayList<CampaignParser> fileNames = new ArrayList<>();
        File path = new File(campaignDir);
        for(File file: getXMLFiles(path.listFiles())){
            fileNames.add(new CampaignParser(file.getPath()));
        }
        return fileNames;
    }


    @FXML
    public void loadSelectedCampaign() {
        try {
            currentCampaignDir = campaignLoadList.getSelectionModel().getSelectedItem().getCampaignDirectory();
            loadCampaign(currentCampaignDir);
        }catch(NullPointerException e){
            buildNewCampaign();
        }
        newTabMenu.setDisable(false);
        newJournalMenuItem.setDisable(false);
        sheetPane.setVisible(true);
        newCharacterSheetMenuItem.setDisable(false);
        loadCampaignPane.setVisible(false);
    }

    @FXML
    public void loadSelectedCharacter() {
        CharacterParser character;
        try {
            currentCharacterDir = characterLoadList.getSelectionModel().getSelectedItem().getPath();
            character = characterLoadList.getSelectionModel().getSelectedItem();
        }catch(NullPointerException e){
            String charFile = makeNewCharacterFolder(characterDir);
            currentCharacterDir = String.format("%s/%s/%s",characterDir,charFile,charFile);
            character = new CharacterParser(currentCharacterDir);
        }
        if(clientParser != null){
            createCharacterSheetTab(character,clientParser);
        }else {
            createCharacterSheetTab(character);
        }
        createCharacterJournals(currentCharacterDir);
        newTabMenu.setDisable(false);
        newJournalMenuItem.setDisable(false);
        newCharacterSheetMenuItem.setDisable(true);
        loadPane.setVisible(false);
        sheetPane.setVisible(true);
    }

    private void createCharacterJournals(String directory) {
        File dir = new File(directory).getParentFile();
        File[] fileList = dir.listFiles();
        assert fileList != null;
        for (File file : fileList)
            if (file.getName().contains(".jour"))
                createJournalTab(file.getPath());
    }


    private String makeNewCharacterFolder(String stringPath) {
        final File charFolder = new File(stringPath+System.nanoTime());
        try {
            Files.createDirectory(charFolder.toPath());
        }catch(IOException e){
            e.printStackTrace();
        }
        return charFolder.getName();
    }

    private String makeNewCampaignFolder() {
        final File campaignFolder = new File(campaignDir+String.valueOf(System.nanoTime()));
        final File charFolder = new File(campaignFolder.getPath()+"/characters/");
        try {
            Files.createDirectory(campaignFolder.toPath());
            Files.createDirectory(charFolder.toPath());
        }catch(IOException e){
            e.printStackTrace();
        }
        return campaignFolder.getName();
    }

    @FXML
    public void newButtonCampaign() {
        charTypePaneDM.setVisible(false);
        newCampaignPane.setVisible(true);
    }

    @FXML
    public void newButtonPress() {
        creatorTextFields.addAll(Arrays.asList(playerNameTextBox, characterNameTextBox));
        charTypePane.setVisible(false);
        namePane.setVisible(true);
    }
    @FXML
    public void nameNextButtonPress() {
        if (!isPageFilled()) {
            nameErrorLabel.setVisible(true);
        } else {
            creatorTextFields.addAll(
                    Arrays.asList(raceTextBox, ageTextBox, heightTextBox, speedTextBox));
            creatorComboBoxes.addAll(
                    Arrays.asList(firstAbilityModified, firstModifiedScore,
                            secondAbilityModified, secondModifiedScore, alignmentBox, sizeBox));
            namePane.setVisible(false);
            racePane.setVisible(true);
        }
    }
    @FXML
    public void raceNextButtonPress() {
        if (!isPageFilled()) {
            raceErrorLabel.setVisible(true);
        } else {
            racePane.setVisible(false);
            languagePane.setVisible(true);
        }
    }
    @FXML
    public void languageNextButtonPress() {
        if (!isPageFilled() || languageTextBox.getText().equals("")) {
            languageErrorLabel.setVisible(true);
        } else {
            creatorTextFields.addAll(
                    Arrays.asList(classTextBox, hpTextBox));
            creatorComboBoxes.addAll(
                    Arrays.asList(savingThrowOne, savingThrowTwo, primaryAbilityOne, primaryAbilityTwo));
            languagePane.setVisible(false);
            classPane.setVisible(true);
        }
    }
    @FXML
    public void classNextButtonPress() {
        if (!isPageFilled()) {
            classErrorLabel.setVisible(true);
        } else {
            creatorTextFields.addAll(
                    Arrays.asList(strTextBox, dexTextBox, conTextBox,
                            intTextBox, wisTextBox, chaTextBox));
            classPane.setVisible(false);
            statPane.setVisible(true);
        }
    }
    @FXML
    public void statNextButtonPress() {
        if (!isPageFilled()) {
            statErrorLabel.setVisible(true);
        } else {
            buildNewCharacter();
            newTabMenu.setDisable(false);
            newJournalMenuItem.setDisable(false);
            newCharacterSheetMenuItem.setDisable(true);
            statPane.setVisible(false);
            sheetPane.setVisible(true);
        }
    }

    @FXML
    public void newCampaignNextButtonPress() {
        if (!isPageFilled()) {
            nameErrorLabel.setVisible(true);
        } else {
            creatorTextFields.addAll(
                    Arrays.asList(campaignTitle, campaignSummary));
            buildNewCampaign();
            newCampaignPane.setVisible(false);
            newTabMenu.setDisable(false);
            newJournalMenuItem.setDisable(false);
            newCharacterSheetMenuItem.setDisable(false);
            sheetPane.setVisible(true);
        }
    }

    @FXML
    public void rcvButtonPress() {
        charTypePane.setVisible(false);
        rcvPane.setVisible(true);
        populateSendTable();
    }

    @FXML
    public void connectToServer(){
        String ip = ipConnect.getText();
        connectToServer(ip);
    }
    private void connectToServer(String ip){
        try {
            clientParser = new ClientNode(ip, 2000);
            networkLabel.setText("Connected to: " + clientParser.getSocketAddress());
            clientParser.start();
            if(currentCharacterDir != null){
                CharacterTab tab = (CharacterTab)sheetPane.getTabs().get(0);
                tab.setNode(clientParser);
            }
        }catch(IOException e){
            System.err.println("Unable to establish a connection");
        }
    }

    @FXML
    public void sendSelectedCharacter() {
        try {
            clientParser.getDos().writeUTF(sendView.getSelectionModel().getSelectedItem().getTagString());
        }catch (IOException e){
            e.printStackTrace();
        }catch(NullPointerException npe){
            if(clientParser==null){
                System.err.println("Not connected to a DM");
            }else{
                System.err.println("Please select a character to send");
            }
        }
    }

    private boolean isPageFilled() {
        return isComboBoxFilled() && isTextFieldFilled();
    }

    private boolean isTextFieldFilled() {
        for (TextField field : creatorTextFields) {
            if (field.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private boolean isComboBoxFilled() {
        for (ComboBox box : creatorComboBoxes) {
            if(box.getValue() == null){
                return false;
            }
        }
        return true;
    }

    private void loadCampaign(String campaign){
        File[] characterFiles = new File(campaign+"/characters").listFiles();
        for(File file: getXMLFiles(characterFiles)){
            createCharacterSheetTab(new CharacterParser(file.getPath()));
        }
    }

    private ArrayList<File> getXMLFiles(File[] directory){
        ArrayList<File> xmlFiles = new ArrayList<>();
        for(File file:directory){
            if(file.isDirectory()){
                File[] subDirectory = file.listFiles();
                assert subDirectory != null;
                for(File subFile: subDirectory){
                    if (subFile.isFile() && subFile.getName().contains(".xml")) {
                        xmlFiles.add(subFile);
                    }
                }
            }
        }
        return xmlFiles;
    }

    private void buildNewCampaign() {
        currentCampaignDir = makeNewCampaignFolder();
        CampaignParser campaign = new CampaignParser(String.format("%s/%s/%s",campaignDir, currentCampaignDir, currentCampaignDir));
        campaign.writeTag("name",campaignTitle.getText());
        campaign.writeTag("description",campaignSummary.getText());
    }

    private void buildNewCharacter() {
        currentCharacterDir = makeNewCharacterFolder(characterDir);
        CharacterParser character = new CharacterParser(String.format("%s/%s/%s",characterDir, currentCharacterDir, currentCharacterDir));
        character.writeTag("name",characterNameTextBox.getText());
        character.writeTag("exp","0");
        character.writeTag("race",raceTextBox.getText());
        character.writeTag("age",ageTextBox.getText());
        character.writeTag("alignment",alignmentBox.getValue());
        character.writeTag("size",sizeBox.getValue());
        character.writeTag("height",heightTextBox.getText());
        character.writeTag("speed",speedTextBox.getText());
        character.writeTag("languages",parseLanguages());
        character.writeTag("class",classTextBox.getText());
        character.writeTag("maxhp",hpTextBox.getText());
        character.writeTag("currenthp",hpTextBox.getText());
        int[] stats = new int[]{
                Integer.parseInt(strTextBox.getText()), Integer.parseInt(dexTextBox.getText()), Integer.parseInt(conTextBox.getText()),
                Integer.parseInt(intTextBox.getText()), Integer.parseInt(wisTextBox.getText()), Integer.parseInt(chaTextBox.getText())};
        stats = parseAbilityModifiers(stats, firstAbilityModified.getValue(), firstModifiedScore.getValue());
        stats = parseAbilityModifiers(stats, secondAbilityModified.getValue(), secondModifiedScore.getValue());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stats.length - 1; i++) {
            result.append(stats[i]).append(",");
        }
        result.append(stats[stats.length - 1]);
        character.writeTag("stats",result.toString());
        createCharacterSheetTab(character);
    }

    private String parseLanguages() {
        String[] languages = languageTextBox.getText().split("\n");
        StringBuilder languageTag = new StringBuilder();
        for (int i = 0; i < languages.length - 1; i++) {
            languageTag.append(languages[i]).append(",");
        }
        languageTag.append(languages[languages.length - 1]);
        return languageTag.toString().replace("null", " ").trim();
    }

    private int[] parseAbilityModifiers(int[] stats, String ability, String score) {
        if(!ability.equals("None")) {
            for (StatName statName : StatName.values()) {
                if (ability.equals(statName.toString())) {
                    stats[statName.getValue()] += Integer.parseInt(score);
                }
            }
        }
        return stats;
    }

    @FXML
    public void openPlayersHandbook() {
        if (Desktop.isDesktopSupported()) {
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