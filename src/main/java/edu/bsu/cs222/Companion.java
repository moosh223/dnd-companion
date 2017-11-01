package edu.bsu.cs222;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        primaryStage.setTitle("Dungeons & Dragons Companion");
        primaryStage.getIcons().add(new Image("icon.png"));
        parent.getStylesheets().clear();
        parent.getStylesheets().add("themes/default.css");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}
