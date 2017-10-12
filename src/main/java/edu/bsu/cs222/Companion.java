package edu.bsu.cs222;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Companion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("dnd.fxml"));
        Scene scene=new Scene(parent);
        scene.getStylesheets().add("tap.css");
        primaryStage.setTitle("Dnd Companion");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
