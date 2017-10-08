package edu.bsu.cs222;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class companion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("DND.fxml"));
            primaryStage.setTitle("Dnd Companion");
            primaryStage.setResizable(true);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
