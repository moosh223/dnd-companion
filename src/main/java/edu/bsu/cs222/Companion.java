package edu.bsu.cs222;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Companion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        URL fxml = getClass().getClassLoader().getResource("fxml/MainCompanion.fxml");
        assert  fxml != null;
        Parent parent = FXMLLoader.load(fxml);
        Scene scene=new Scene(parent);
        primaryStage.setTitle("Dungeons & Dragons Companion");
        primaryStage.getIcons().add(new Image("icons/icon.png"));
        parent.getStylesheets().clear();
        parent.getStylesheets().add("themes/default.css");
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((e) -> System.exit(0));
        primaryStage.show();
    }
}