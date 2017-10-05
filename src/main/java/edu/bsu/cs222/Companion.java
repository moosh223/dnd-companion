package edu.bsu.cs222;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Companion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("exampleDND.fxml"));
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("example.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
