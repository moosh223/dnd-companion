package edu.bsu.cs222;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class companionController {

    @FXML private Button playerButton;
    @FXML private Button DMButton;
    @FXML private BorderPane welcomePane;
    @FXML private TabPane characterPane;

    @FXML
    public void playerButtonPress(){
        welcomePane.setVisible(false);
        characterPane.setVisible(true);
    }
}
