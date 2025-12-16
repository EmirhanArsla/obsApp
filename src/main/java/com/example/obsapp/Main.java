package com.example.obsapp;


import java.util.Scanner;

import com.example.obsapp.DBO.DersDao;
import com.example.obsapp.controller.LoginController;
import com.example.obsapp.model.SecmeliDers;
import com.example.obsapp.model.ZorunluDers;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

        String metin ;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the metin name: ");
        System.out.println("JAva proje");
        launch(args);
        MongoDatabase database = DBUtil.getInstance().getDatabase();
        DersDao dersDao = new DersDao(database.getCollection("Dersler"));
        ZorunluDers matematik =
                new ZorunluDers("MAT9", "Matematik", 9, 5.0, true);
        dersDao.dersAdd(matematik);
        ZorunluDers turkce =
                new ZorunluDers("TURKCE9", "Türkce", 9, 5.0, true);
        dersDao.dersAdd(turkce);
        ZorunluDers fizik =
        new ZorunluDers("FIZIK9", "Fizik", 9, 4.0, true);
        dersDao.dersAdd(fizik);
        ZorunluDers kimya =
        new ZorunluDers("KIMYA9", "Kimya", 9, 4.0, true);
        dersDao.dersAdd(kimya);
        ZorunluDers biyoloji =
        new ZorunluDers("BIYOLOJI9", "Biyoloji", 9, 4.0, true);
        dersDao.dersAdd(biyoloji);
        ZorunluDers tarih =
        new ZorunluDers("TARIH9", "Tarih", 9, 4.0, true);
        dersDao.dersAdd(tarih);
        ZorunluDers cografya =
        new ZorunluDers("COGRAFYA9", "Cografya", 9, 4.0, true);
        dersDao.dersAdd(cografya);
        ZorunluDers din =
        new ZorunluDers("DIN9", "Din Kültürü ve Ahlak Bilgisi", 9, 3.0, true);
        dersDao.dersAdd(din);
        ZorunluDers ingilizce =
        new ZorunluDers("ING9", "İngilizce", 9, 3.0, true);
        dersDao.dersAdd(ingilizce);
        ZorunluDers beden =
        new ZorunluDers("BEDEN9", "Beden Eğitimi", 9, 3.0, true);
        dersDao.dersAdd(beden);
        ZorunluDers muzik =
        new ZorunluDers("MUZİK9", "Görsel Sanatlar/Müzik", 9, 3.0, true);
        dersDao.dersAdd(muzik);
        SecmeliDers zeka =
        new SecmeliDers("ZEKA9", "Zeka oyunları", 9, 3.0, false);
        dersDao.dersAdd(zeka);
        SecmeliDers felsefe =
        new SecmeliDers("FELSEFE9", "Felsefe", 9, 3.0, false);
        dersDao.dersAdd(felsefe);









    }
}
