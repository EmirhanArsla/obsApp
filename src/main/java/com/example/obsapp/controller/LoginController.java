package com.example.obsapp.controller;

import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.repository.YoneticiRepository;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private OgrenciDao ogrenciDao;
    // ============================
    // FXML BİLEŞENLERİ
    // ============================
    @FXML
    private TextField yoneticiKullanici;

    @FXML
    private PasswordField yoneticiSifre;

    @FXML
    private TextField ogrenciKullanici;

    @FXML
    private PasswordField ogrenciSifre;

    @FXML
    private Button anaMenu;

    @FXML
    private Label hataMesaji;  // Hata mesajını ekranda gösterecek label


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MongoDatabase database = DBUtil.getInstance().getDatabase();

        this.ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));
    }

    // ============================
    // YÖNETİCİ GİRİŞİ
    // ============================
    @FXML
    private void yoneticiGiris() throws IOException {

        String user = yoneticiKullanici.getText();
        String pass = yoneticiSifre.getText();

        YoneticiRepository repo = new YoneticiRepository();

        if (repo.yoneticiDogrula(user, pass)) {
            hataMesaji.setText("");
            loadPage("/com/example/obsapp/Yonetici_sis.fxml", "Yönetici Paneli");
        } else {
            hataMesaji.setText("Hatalı yönetici girişi!");
        }
    }


    // ============================
    // ÖĞRENCİ GİRİŞİ
    // ============================
    @FXML
    private void ogrenciGiris() throws IOException {

        String user = ogrenciKullanici.getText().trim();
        String pass = ogrenciSifre.getText().trim();

        Document kontrol = ogrenciDao.girisKontrol(user, pass);

        if (kontrol != null) {
            try {


                hataMesaji.setText(""); // hata mesajını temizle

                FXMLLoader loader = loadPage("/com/example/obsapp/Ogrenci_sis.fxml", "Öğrenci Paneli");

                Ogrenci_sisController ogrenci_sis = loader.getController();

                ogrenci_sis.setGelen_ogrenciTc(pass);
            } catch (IOException e) {
                e.printStackTrace();
                hataMesaji.setText("Sayfa yüklenirken Hata oluştu" + e.getMessage());
            }

        } else {
            hataMesaji.setText("Hatalı öğrenci girişi!");
        }
    }


    // ============================
    // SAYFA YÜKLEYİCİ
    // ============================
    private FXMLLoader loadPage(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

        // giriş ekranını kapat
        Stage loginStage = (Stage) yoneticiKullanici.getScene().getWindow();
        loginStage.close();


        return loader;

    }

}
