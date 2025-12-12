package com.example.obsapp.controller;

import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.repository.YoneticiRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import org.bson.Document;

public class LoginController {
    private OgrenciDao ogrenciDao ;
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
    private Label hataMesaji;  // Hata mesajını ekranda gösterecek label



    // ============================
    // YÖNETİCİ GİRİŞİ
    // ============================
    @FXML
    private void yoneticiGiris() {

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
    private void ogrenciGiris() {

        String user = ogrenciKullanici.getText();
        String pass = ogrenciSifre.getText();

        Document kontrol = ogrenciDao.girisKontrol(user, pass);

        if (kontrol != null) {
            hataMesaji.setText(""); // hata mesajını temizle

           FXMLLoader loader = loadPage("/com/example/obsapp/Ogrenci_sis.fxml", "Öğrenci Paneli");
            Ogrenci_sisController ogrenci_sis = new Ogrenci_sisController();
            ogrenci_sis.setOgrenciNo(user);
        } else {
            hataMesaji.setText("Hatalı öğrenci girişi!");
        }
    }


    // ============================
    // SAYFA YÜKLEYİCİ
    // ============================
    private FXMLLoader loadPage(String fxmlPath, String title) {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource(fxmlPath));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            // giriş ekranını kapat
            Stage loginStage = (Stage) yoneticiKullanici.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            hataMesaji.setText("Sayfa yüklenirken hata oluştu!");

        }
        return loader;

    }
}
