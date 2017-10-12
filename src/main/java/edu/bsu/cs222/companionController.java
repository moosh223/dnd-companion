package edu.bsu.cs222;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML private AnchorPane classPane;
    @FXML private AnchorPane statsPane;
    @FXML private Label nameErrorLabel;
    @FXML private Label classErrorLabel;
    @FXML private Label levelErrorLabel;
    @FXML private TextField playerNameTextBox;
    @FXML private TextField characterNameTextBox;
    @FXML private TextField raceTextBox;
    @FXML private TextField classTextBox;
    @FXML private TextField lvlTextBox;

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
        if (playerNameTextBox.getText().equals("") || characterNameTextBox.getText().equals("")){
            nameErrorLabel.setVisible(true);
        }
        else {
            String playerName = playerNameTextBox.getText();
            String characterName = characterNameTextBox.getText();
            namePane.setVisible(false);
            classPane.setVisible(true);
        }
    }

    @FXML
    public void classNextButtonPress(){
        try {
            if (raceTextBox.getText().equals("") || classTextBox.getText().equals("") || lvlTextBox.getText().equals("")) {
                classErrorLabel.setVisible(true);
                levelErrorLabel.setVisible(false);
            } else if (1 > Integer.parseInt(lvlTextBox.getText()) || 20 < Integer.parseInt(lvlTextBox.getText())) {
                levelErrorLabel.setVisible(true);
                classErrorLabel.setVisible(false);
            } else {
                String race = raceTextBox.getText();
                String charClass = classTextBox.getText();
                int level = Integer.parseInt(lvlTextBox.getText());
                classPane.setVisible(false);
                statsPane.setVisible(true);
            }
        }
        catch(NumberFormatException e){
            levelErrorLabel.setVisible(true);
            classErrorLabel.setVisible(false);
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
