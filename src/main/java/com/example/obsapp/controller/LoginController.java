package com.example.obsapp.controller;

import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.DBO.YoneticiDao;
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
    private YoneticiDao yoneticiDao;
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
        this.yoneticiDao = new YoneticiDao(database.getCollection("Ogretmen"));

        this.ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));
    }

    // ============================
    // YÖNETİCİ GİRİŞİ
    // ============================
    @FXML
    private void yoneticiGiris() {

        String user = yoneticiKullanici.getText().trim();
        String pass = yoneticiSifre.getText().trim();

        Document kontrol = yoneticiDao.yoneticiKontrol(user, pass);

        if (kontrol != null) {
            try {
                hataMesaji.setText("");

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/obsapp/Yonetici_sis.fxml")
                );

                Parent root = loader.load();

                // 🎯 Controller'a erişim
                Yonteci_sisController controller = loader.getController();

                // 🎯 Yönetici bilgisini controller'a gönder
                controller.setYoneticiBilgisi(kontrol);

                Stage stage = new Stage();
                stage.setTitle("Yönetici Paneli");
                stage.setScene(new Scene(root));
                stage.show();

                // Login ekranını kapat
                Stage loginStage = (Stage) yoneticiKullanici.getScene().getWindow();
                loginStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                hataMesaji.setText("Sayfa yüklenirken hata oluştu!");
            }

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
