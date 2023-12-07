package com.example.spotify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main view FXML file
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("main_view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/logo.png")));

        // Set the scene and stage
        primaryStage.setTitle("Spotify Track Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
