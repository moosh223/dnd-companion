package edu.bsu.cs222;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class CompanionController {

    @FXML private BorderPane welcomePane;
    @FXML private TabPane characterPane;
    @FXML private AnchorPane charTypePane;
    @FXML private AnchorPane loadPane;
    @FXML private AnchorPane namePane;
    @FXML private AnchorPane classPane;
    @FXML private Label errorLabel;
    @FXML private TextField playerNameTextBox;
    @FXML private TextField characterNameTextBox;

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
            errorLabel.setVisible(true);
        }
        else {
            String playerName = playerNameTextBox.getText();
            String CharacterName = characterNameTextBox.getText();
            namePane.setVisible(false);
            classPane.setVisible(true);
        }
    }
}
