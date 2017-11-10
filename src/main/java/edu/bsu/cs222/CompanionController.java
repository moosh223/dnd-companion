package edu.bsu.cs222;

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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;

public class CompanionController {

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
    @FXML public ListView<String> sendView;
    @FXML public CheckBox diceRollerButton;
    @FXML public MenuItem newCharacterSheetMenuItem;
    @FXML public MenuItem newJournalMenuItem;
    @FXML public Menu newTabMenu;
    private XMLParser parser = new XMLParser();
    private File dir = new File("assets/characters/");
    private boolean isPlayer = true;
    Stage stage = new Stage();

    private NetworkServerParser netParse;

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
    private void createCharacterSheetTab(PlayerCharacter character) {
        Tab newTab = new Tab("Character Sheet");
        CharacterSheet sheet = new CharacterSheet(character);
        newTab.setContent(sheet.getSheet());
        newTab.setClosable(false);
        characterPane.getTabs().addAll(newTab);
    }

    @FXML
    private void createJournalTab(String filepath) {
        Tab newTab = new Tab("Journal");
        JournalTab journalTab = new JournalTab(filepath);
        newTab.setContent(journalTab.getSheet());
        characterPane.getTabs().addAll(newTab);
    }

    @FXML
    public void newCharacterSheetMenuAction(){
        if(characterPane.isVisible()&& !isPlayer) {
            createCharacterSheetTab(new PlayerCharacter(String.valueOf(System.nanoTime())));
        }
    }

    @FXML
    public void newJournalMenuAction(){
        if(characterPane.isVisible()) {
            createJournalTab(String.format("%s/%d.jour",dir,System.nanoTime()));
        }
    }

    @FXML
    public void closeProgram() throws IOException {
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
        //stage = StageHelper.getStages().get(StageHelper.getStages().size() - 1);
        stage.close();
    }

    @FXML
    public void playerButtonPress() {
        welcomePane.setVisible(false);
        charTypePane.setVisible(true);
        isPlayer = true;
    }

    @FXML
    public void dmButtonPress() throws UnknownHostException {
        try {
            netParse = new NetworkServerParser(2000);
            networkLabel.setText("Your IP Address is: "+ netParse.getLANAddress());
            new Thread(() -> {
                        Thread.currentThread().setName("NetThread");
                        while(Thread.currentThread().isAlive()) {
                            System.out.println("Awaiting client message...");
                            try {
                                netParse.server = netParse.serverSocket.accept();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            netParse.getMessageFromClient();
                            netParse.writeToClient("hey there,"+netParse.server.getInetAddress());
                        }
            }).start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void loadButtonAction() {
        charTypePane.setVisible(false);
        loadPane.setVisible(true);
        populateLoadTable();
    }
    private void populateLoadTable() {
        ObservableMap<String, String> fileList = FXCollections.observableMap(getXMLFileList());
        for (String name : fileList.values()) {
            characterLoadList.getItems().add(name);
        }
    }

    private void populateSendTable() {
        ObservableMap<String, String> fileList = FXCollections.observableMap(getXMLFileList());
        for (String name : fileList.values()) {
            sendView.getItems().add(name);
        }
    }
    private Map<String, String> getXMLFileList() {
        Map<String,String> fileNames = new HashMap<>();
        try {
            for (File dir : getCharacterFiles()) {
                File[] fileList = dir.listFiles();
                assert fileList != null;
                for (File file : fileList) {
                    if (file.isFile() && file.getName().contains(".xml")) {
                        Document doc = parser.buildDocumentStream(file.getPath());
                        String displayName = doc.getDocumentElement().getElementsByTagName("name").item(0).getTextContent();
                        fileNames.put(file.getName().replace(".xml", ""), displayName);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    @FXML
    public void loadSelectedCharacter() {
        try {
            for (Map.Entry<String, String> entry : getXMLFileList().entrySet()) {
                if (characterLoadList.getSelectionModel().getSelectedItem().equals(entry.getValue())) {
                    createCharacterSheetTab(new PlayerCharacter(String.format("%s/%s.xml", entry.getKey(), entry.getKey())));
                    createCharacterJournals(entry.getKey());
                }
            }
        }catch(NullPointerException e){
            makeNewCharacterFolder();
            createCharacterSheetTab(new PlayerCharacter(
                    String.format("%s/%s",dir.getName(),dir.getName())));
        }
        newTabMenu.setDisable(false);
        newJournalMenuItem.setDisable(false);
        newCharacterSheetMenuItem.setDisable(true);
        loadPane.setVisible(false);
        characterPane.setVisible(true);
    }

    private void createCharacterJournals(String directory) {
        dir = new File("assets/characters/" + directory);
        File[] fileList = dir.listFiles();
        assert fileList != null;
        for (File file : fileList)
            if (file.getName().contains(".jour"))
                createJournalTab(file.getPath());
    }


    private void makeNewCharacterFolder() {
        dir = new File("assets/characters/"+String.valueOf(System.nanoTime()));
        final boolean mkdir = dir.mkdir();
        System.out.println(mkdir);
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
    public void rcvButtonPress() {
        charTypePane.setVisible(false);
        rcvPane.setVisible(true);
        populateSendTable();
    }

    @FXML
    public void sendSelectedCharacter() {
        try {
            for (Map.Entry<String, String> entry : getXMLFileList().entrySet()) {
                if (sendView.getSelectionModel().getSelectedItem().equals(entry.getValue())) {
                    NetworkClientParser clientParser = new NetworkClientParser("10.2.152.158");
                    clientParser.writeToServer(String.format("assets/characters/%s",entry.getKey()));
                    clientParser.getMessageFromServer();
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

    private void buildNewCharacter() {
        makeNewCharacterFolder();
        PlayerCharacter character = new PlayerCharacter(String.format("%s/%s",dir.getName(),dir.getName()));
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
        /*String primaryOne = primaryAbilityOne.getValue();
        String primaryTwo = primaryAbilityTwo.getValue();
        String saveThrowOne = savingThrowOne.getValue();
        String saveThrowTwo = savingThrowTwo.getValue();
        */character.setStats(String.format("%s,%s,%s,%s,%s,%s",
                strTextBox.getText(), dexTextBox.getText(), conTextBox.getText(),
                intTextBox.getText(), wisTextBox.getText(), chaTextBox.getText()));
        parseAbilityModifiers(character, firstAbilityModified.getValue(), firstModifiedScore.getValue());
        parseAbilityModifiers(character, secondAbilityModified.getValue(), secondModifiedScore.getValue());
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

    private void parseAbilityModifiers(PlayerCharacter character, String ability, String score) {
        for (StatName statName : StatName.values()) {
            if (ability.equals(statName.toString())) {
                character.setStat(statName.getValue(), Integer.parseInt(score) +
                        character.getStats()[statName.getValue()]);
            } else if (ability.equals("None")) {
                return;
            }
        }
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