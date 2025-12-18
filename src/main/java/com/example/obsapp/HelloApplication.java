package com.example.obsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        String cssPath = HelloApplication.class.getResource("style.css").toExternalForm();


        scene.getStylesheets().add(cssPath);
    }
}
