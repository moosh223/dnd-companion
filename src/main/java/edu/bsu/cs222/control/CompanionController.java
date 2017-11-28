package edu.bsu.cs222.control;

import edu.bsu.cs222.*;
import edu.bsu.cs222.net.*;
import edu.bsu.cs222.tab.*;
import edu.bsu.cs222.util.XMLParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class CompanionController {

    //ViewPanes
    @FXML public BorderPane welcomePane;
    @FXML public TabPane characterPane;
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
    @FXML public ListView<String> campaignLoadList;
    @FXML public ListView<CharacterParser> characterLoadList;
    @FXML public ListView<String> sendView;
    @FXML public CheckBox diceRollerButton;
    @FXML public MenuItem newCharacterSheetMenuItem;
    //@FXML public MenuItem loadPrevCharacters;
    @FXML public MenuItem newJournalMenuItem;
    @FXML public Menu newTabMenu;
    @FXML public TextField ipConnect;

    private final String characterDir = "assets/characters/";
    private final String campaignDir = "assets/campaigns/";
    private File dir;
    private String currentCampaignDirectory;
    private boolean isPlayer = true;
    private Stage stage = new Stage();
    private ArrayList<NetThread> netThreads = new ArrayList<>();
    private NetworkServerParser netParse;
    private NetworkClientParser clientParser;

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
        CharacterTab sheet = new CharacterTab(character);
        sheet.setContent(sheet.getSheet());
        sheet.setClosable(false);
        characterPane.getTabs().add(sheet);
    }

    private CharacterTab makeCharacterTab(CharacterParser character){
        CharacterTab sheet = new CharacterTab(character);
        sheet.setContent(sheet.getSheet());
        sheet.setClosable(false);
        return sheet;
    }


    /**Creates a new journal tab
     * @author Josh Mooshian <jmmooshian@bsu.edu>
     * @param filepath Path to the journal file
     */
    private void createJournalTab(String filepath) {
        Tab newTab = new Tab("Journal");
        JournalTab journalTab = new JournalTab(filepath);
        newTab.setContent(journalTab.getSheet());
        characterPane.getTabs().add(newTab);
    }


    private void createSpellSheetTab(){
        Tab newTab = new Tab("Spell Sheet");
        SpellTab spellTab = new SpellTab();
        newTab.setContent(spellTab.getSheet());
        characterPane.getTabs().add(newTab);
    }

    @FXML
    public void newCharacterSheetMenuAction(){
        if(characterPane.isVisible()&& !isPlayer) {
            String newChar = makeNewCharacterFolder(String.format("%s/%s/characters/",campaignDir,currentCampaignDirectory));
            createCharacterSheetTab(new CharacterParser(String.format("%s/%s/characters/%s/%s",campaignDir,currentCampaignDirectory,newChar,newChar)));
        }
    }

    @FXML
    public void newJournalMenuAction(){
        if(characterPane.isVisible()) {
            createJournalTab(String.format("%s/%d.jour",dir,System.nanoTime()));
        }
    }

    @FXML
    public void newSpellSheetMenuAction(){
        if(characterPane.isVisible()){
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
            stage.setTitle("Dice Roller");
            stage.getIcons().add(new Image("icons/DiceIcon.png"));
            parent.getStylesheets().clear();
            parent.getStylesheets().add("themes/default.css");
            stage.setOnCloseRequest((e) -> diceRollerButton.selectedProperty().setValue(false));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            return;
        }
        stage.close();
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
            netParse = new NetworkServerParser(2000);
            networkLabel.setText("Your IP Address is: "+ netParse.getLANAddress());
            new Thread(() -> {
                while(Thread.currentThread().isAlive()) {
                    try {
                        netParse.server = netParse.serverSocket.accept();
                        System.out.printf("%s has connected%n", netParse.server.getInetAddress().toString());
                        System.out.println("CREATING NEW THREAD");
                        createNewNetThread();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createNewNetThread() {
        NetThread thread = new NetThread(netParse.server);
        thread.setName(netParse.server.getInetAddress().toString());
        if(!(currentCampaignDirectory == null)){
            thread.setCampaign(currentCampaignDirectory);
        }
        netThreads.add(thread);
        thread.start();
    }

    @FXML
    public void sendServerMessage(){
        characterPane.getTabs().clear();
        for(NetThread thread: netThreads){
            thread.setTab(makeCharacterTab(thread.getCharacter()));
            characterPane.getTabs().add(thread.getTab());
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
        characterLoadList.setItems(FXCollections.observableArrayList(getCharacters(characterDir)));
    }

    private void populateLoadTableDM() {
        ObservableMap<String, String> fileList = FXCollections.observableMap(getXMLFileList(campaignDir));
        for (String name : fileList.values()) {
            campaignLoadList.getItems().add(name);
        }
    }

    private void populateSendTable() {
        ObservableMap<String, String> fileList = FXCollections.observableMap(getXMLFileList(characterDir));
        for (String name : fileList.values()) {
            sendView.getItems().add(name);
        }
    }

    private Map<String, String> getXMLFileList(String searchPath) {
        Map<String,String> fileNames = new HashMap<>();
            dir = new File(searchPath);
            for (File dir : getCharacterFiles()) {
                File[] fileList = dir.listFiles();
                assert fileList != null;
                for (File file : fileList) {
                    if (file.isFile() && file.getName().contains(".xml")) {
                        CharacterParser parser = new CharacterParser(file.getPath());
                        String displayName = parser.readTag("name");
                        fileNames.put(file.getName().replace(".xml", ""), displayName);
                    }
                }
            }
        return fileNames;
    }

    private ArrayList<CharacterParser> getCharacters(String searchPath) {
        ArrayList<CharacterParser> fileNames = new ArrayList<>();
        File path = new File(searchPath);
        for (File dir : path.listFiles()) {
            if(dir.isDirectory()){
                File[] fileList = dir.listFiles();
                assert fileList != null;
                for (File file : fileList) {
                    if (file.isFile() && file.getName().contains(".xml")) {
                        System.err.println(file.getPath());
                        CharacterParser parser = new CharacterParser(file.getPath());
                        fileNames.add(parser);
                    }
                }
            }

        }
        return fileNames;
    }


    @FXML
    public void loadSelectedCampaign() {
        try {
            for (Map.Entry<String, String> entry : getXMLFileList(campaignDir).entrySet()) {
                if (campaignLoadList.getSelectionModel().getSelectedItem().equals(entry.getValue())) {
                    currentCampaignDirectory = entry.getKey();
                    for(NetThread thread: netThreads){
                        thread.setCampaign(currentCampaignDirectory);
                    }
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            buildNewCampaign();
        }
        newTabMenu.setDisable(false);
        newJournalMenuItem.setDisable(false);
        characterPane.setVisible(true);
        newCharacterSheetMenuItem.setDisable(false);
        loadCampaignPane.setVisible(false);
    }

    @FXML
    public void loadSelectedCharacter() {
        try {
            createCharacterSheetTab(characterLoadList.getSelectionModel().getSelectedItem());
            createCharacterJournals(characterLoadList.getSelectionModel().getSelectedItem().getPath().replace(".xml",""));
        }catch(NullPointerException e){
            String charFile = makeNewCharacterFolder(characterDir);
            createCharacterSheetTab(new CharacterParser(String.format("%s/%s/%s",characterDir,charFile,charFile)));
        }
        newTabMenu.setDisable(false);
        newJournalMenuItem.setDisable(false);
        newCharacterSheetMenuItem.setDisable(true);
        loadPane.setVisible(false);
        characterPane.setVisible(true);
    }

    private void createCharacterJournals(String directory) {
        dir = new File(directory).getParentFile();
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

    private List<File> getCharacterFiles(){
        List<File> characterList = new ArrayList<>();
        File[] fileList = dir.listFiles();
        assert fileList != null;
        for (File file : fileList)
            if (file.isDirectory())
                characterList.add(file);
        return characterList;
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
            characterPane.setVisible(true);
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
            characterPane.setVisible(true);
        }
    }

    @FXML
    public void rcvButtonPress() {
        charTypePane.setVisible(false);
        rcvPane.setVisible(true);
        populateSendTable();
    }

    private void connectToServer(String ip){
        try {
            clientParser = new NetworkClientParser(ip);
            for(NetThread thread : netThreads){
                if(clientParser.getSocketAddress().equals(thread.getName())){
                    return;
                }
            }
            networkLabel.setText("Connected to: " + clientParser.getSocketAddress());
            clientParser.writeToServer(clientParser.getSocketAddress());
            clientParser.getMessageFromServer();
            new Thread(() -> {
                while(Thread.currentThread().isAlive()){
                    try{
                        clientParser.getMessageFromServer();
                    }catch(Exception e){
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }catch(Exception e){
            System.err.println("Unable to establish a connection");
        }
    }

    @FXML
    public void connectToSpecificServer(){
        String ip = ipConnect.getText();
        connectToServer(ip);
    }

    @FXML
    public void sendSelectedCharacter() {
        try {
            for (Map.Entry<String, String> entry : getXMLFileList(characterDir).entrySet()) {
                if (sendView.getSelectionModel().getSelectedItem().equals(entry.getValue())) {
                    try {
                        clientParser.writeToServer("load");
                        clientParser.writeToServer(entry.getValue());
                        clientParser.sendCharacterXML(new File(String.format("%s%s/%s.xml",characterDir, entry.getKey(),entry.getKey())));
                        new Thread(() -> clientParser.getObjectFromServer(String.format("%s%s/%s",characterDir,entry.getKey(),entry.getKey()))).start();
                    }catch(NullPointerException e){
                        System.err.println("You aren't connected to a server!");
                    }
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
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

    private void buildNewCampaign() {
        currentCampaignDirectory = makeNewCampaignFolder();
        CampaignParser campaign = new CampaignParser(String.format("%s/%s/%s",campaignDir, currentCampaignDirectory, currentCampaignDirectory));
        System.out.println(campaignTitle.getText());
        campaign.writeTag("name",campaignTitle.getText());
        campaign.writeTag("description",campaignSummary.getText());
        System.out.println(campaign.toString());
        for(NetThread thread: netThreads){
            thread.setCampaign(currentCampaignDirectory);
        }
    }

    private void buildNewCharacter() {
        String charFile = makeNewCharacterFolder(characterDir);
        CharacterParser character = new CharacterParser(String.format("assets/characters/%s/%s",charFile,charFile));
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