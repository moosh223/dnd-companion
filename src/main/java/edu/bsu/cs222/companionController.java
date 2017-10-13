package edu.bsu.cs222;

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


public class companionController {

    @FXML private BorderPane welcomePane;
    @FXML private TabPane characterPane;
    @FXML private AnchorPane charTypePane;
    @FXML private AnchorPane loadPane;
    @FXML private AnchorPane namePane;
    @FXML private AnchorPane racePane;
    @FXML private AnchorPane languagePane;
    @FXML private AnchorPane classPane;
    @FXML private Label nameErrorLabel;
    @FXML private Label raceErrorLabel;
    @FXML private Label languageErrorLabel;
    @FXML private TextField playerNameTextBox;
    @FXML private TextField characterNameTextBox;
    @FXML private TextField raceTextBox;
    @FXML private TextField ageTextBox;
    @FXML private TextField heightTextBox;
    @FXML private TextField speedTextBox;
    @FXML private TextArea languageTextArea;
    @FXML private ComboBox<String> firstAbilityModified;
    @FXML private ComboBox<Integer> firstModifiedScore;
    @FXML private ComboBox<String> secondAbilityModified;
    @FXML private ComboBox<Integer> secondModifiedScore;
    @FXML private ComboBox<String> alignmentBox;
    @FXML private ComboBox<String> sizeBox;

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
    }

    @FXML
    public void newButtonPress(){
        charTypePane.setVisible(false);
        namePane.setVisible(true);
    }

    @FXML
    public void rcvButtonPress(){
        charTypePane.setVisible(false);
        characterPane.setVisible(true);
    }

    @FXML
    public void nameNextButtonPress(){
        if(playerNameTextBox.getText().equals("") || characterNameTextBox.getText().equals("")) {
            nameErrorLabel.setVisible(true);
        }
        else {
            String playerName = playerNameTextBox.getText();
            String characterName = characterNameTextBox.getText();
            namePane.setVisible(false);
            racePane.setVisible(true);
        }
    }

    @FXML
    public void raceNextButtonPress(){
        if (raceTextBox.getText().equals("") || firstAbilityModified.getValue().equals("") || firstModifiedScore.getValue()==null
                || secondAbilityModified.getValue().equals("") || secondModifiedScore.getValue()==null || ageTextBox.getText().equals("")
                || alignmentBox.getValue().equals("") || sizeBox.getValue().equals("") || heightTextBox.getText().equals("")
                || speedTextBox.getText().equals("")) {
            raceErrorLabel.setVisible(true);
        }
        else{
            String race = raceTextBox.getText();
            String firstAbility = firstAbilityModified.getValue();
            int firstAbilityValue = firstModifiedScore.getValue();
            String secondAbility = secondAbilityModified.getValue();
            int secondAbilityValue = secondModifiedScore.getValue();
            String age = ageTextBox.getText();
            String alignment = alignmentBox.getValue();
            String size = sizeBox.getValue();
            String height = heightTextBox.getText();
            String speed = speedTextBox.getText();
            racePane.setVisible(false);
            languagePane.setVisible(true);
        }
    }

    @FXML
    public void languageNextButtonPress(){
        if (languageTextArea.getText().equals("")) {
            languageErrorLabel.setVisible(true);
        }
        else{
            String languages = languageTextArea.getText();
            languagePane.setVisible(false);
            classPane.setVisible(true);
        }
    }

    @FXML
    public void openPlayersHandbook(){
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File("Players_Handbook.pdf");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
            }
        }
    }
}
