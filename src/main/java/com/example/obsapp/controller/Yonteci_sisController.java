package com.example.obsapp.controller;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.model.Ogrenci;
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

    @FXML
    private TextField txtSinif;


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
        int sinifSeviyesi =Integer.parseInt(txtSinif.getText());

        if (ad.isEmpty() || soyad.isEmpty() || tc.isEmpty() || ogrenciNo.isEmpty() || sinifSeviyesi == 0) {
            durumMesajLabel.setText("Lütfen tüm alanları doldurun!");
            return;
        }

        try {

            Ogrenci yeniOgrenci = new Ogrenci(tc,ad,soyad,sinifSeviyesi,ogrenciNo);
                //ogrenciDao.ogrenciAdd(ad, soyad, tc, ogrenciNo,sinifSeviyesi);

                String sonuc = ogrenciDao.ogrenciAdd(yeniOgrenci);
                durumMesajLabel.setText(sonuc);

                txtAd.clear();
                txtSoyad.clear();
                txtTc.clear();
                txtOgrenciNo.clear();
                txtSinif.clear();

        } catch (Exception e) {
            durumMesajLabel.setText("Hata oluştu: " + e.getMessage());
        }

    }
}


