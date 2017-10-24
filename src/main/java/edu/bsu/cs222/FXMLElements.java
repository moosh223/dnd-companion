package edu.bsu.cs222;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class FXMLElements {
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

    //Character Creator Fields
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
    
    //Miscellaneous Elements
    @FXML public ListView<String> characterLoadList;
    @FXML public CheckBox diceRollerButton;
    @FXML public Tab newTabButton;
}
