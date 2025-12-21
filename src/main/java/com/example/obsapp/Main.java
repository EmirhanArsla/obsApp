package com.example.obsapp;


import java.util.Scanner;

import com.example.obsapp.dao.DersDao;
import com.example.obsapp.model.SecmeliDers;
import com.example.obsapp.model.ZorunluDers;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/obsapp/hello-view.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("OBS Giriş");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();









    }
}
