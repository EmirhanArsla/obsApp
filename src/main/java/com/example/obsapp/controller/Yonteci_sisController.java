package com.example.obsapp.controller;
import com.example.obsapp.DBO.OgrenciDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Yonteci_sisController {
    @FXML
    private TextField txtAd;


    @FXML
    private TextField txtSoyad;


    @FXML
    private TextField txtTc;


    @FXML
    private TextField txtOgrenciNo;


    @FXML
    private Button ekleButton;


    @FXML
    private Label durumMesajLabel;


    private OgrenciDao ogrenciDao;

    public Yonteci_sisController() {
        // DAO sınıfını burada initialize ederiz
        ogrenciDao = new OgrenciDao();
    }

    @FXML
    public void initialize() {
        ekleButton.setOnAction(event -> ogrenciEkle());
    }

    private void ogrenciEkle() {

        String ad = txtAd.getText();
        String soyad = txtSoyad.getText();
        String tc = txtTc.getText();
        String ogrenciNo = txtOgrenciNo.getText();

        if (ad.isEmpty() || soyad.isEmpty() || tc.isEmpty() || ogrenciNo.isEmpty()) {
            durumMesajLabel.setText("Lütfen tüm alanları doldurun!");
            return;
        }

        try {
            ogrenciDao.ogrenciAdd(ad, soyad, tc, ogrenciNo);
            durumMesajLabel.setText("Öğrenci başarıyla eklendi!");

            txtAd.clear();
            txtSoyad.clear();
            txtTc.clear();
            txtOgrenciNo.clear();

        } catch (Exception e) {
            durumMesajLabel.setText("Hata oluştu: " + e.getMessage());
        }
    }
}


