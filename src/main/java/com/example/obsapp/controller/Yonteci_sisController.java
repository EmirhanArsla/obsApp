package com.example.obsapp.controller;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.Viewmodel.OrtalamaGorunum;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Yonteci_sisController {
    @FXML
    private Tab OgrenciDuzenle;

    @FXML
    private Tab OgrenciAra;

    @FXML
    private Tab DersNotuEKle;

    @FXML
    private Tab DersNotuGoruntule;

    @FXML
    private Tab OgretmenEkle;

    // ============================
    // ORTAK LABEL
    // ============================
    @FXML
    private Label loginText2;

    // ============================
    // ÖĞRENCİ EKLE / SİL
    // ============================
    @FXML
    private TextField txtAd;

    @FXML
    private TextField txtSoyad;

    @FXML
    private TextField txtTc;

    @FXML
    private TextField txtOgrenciNo;

    @FXML
    private TextField txtSinif;

    @FXML
    private DatePicker kayitTarihiOgrenci;

    @FXML
    private Button ekleButton;

    @FXML
    private TextField tcsilTextField;

    @FXML
    private Button OgrenciSilButton;

    @FXML
    private Label durumMesajLabel;

    // ============================
    // ÖĞRENCİ ARA
    // ============================
    @FXML
    private TextField textFieldOgrenciAraTC;

    @FXML
    private Button buttonOgrenciAra;

    @FXML
    private TableView<Ogrenci> tableViewOgrenciBilgileri;

    @FXML
    private Label labelDurumOgrenciAra;
    @FXML private TableColumn<Ogrenci, String> kolonAraIsim;
    @FXML private TableColumn<Ogrenci, String> kolonAraSoyisim;
    @FXML private TableColumn<Ogrenci, String> kolonAraTc;
    @FXML private TableColumn<Ogrenci, LocalDate> kolonAraKayittarihi;
    @FXML private TableColumn<Ogrenci, String> kolonAraSinif;



    // ============================
    // DERS NOTU EKLE
    // ============================
    @FXML
    private TextField textFieldOgrenciTcDersEKle;

    @FXML
    private TextField textFieldDersİDekle;

    @FXML
    private TextField textFieldYazili1;

    @FXML
    private TextField TextfieldYazili2;

    @FXML
    private Button buttonNotEkle;
    @FXML
    private Label labelDurumDersNot;

    // ============================
    // DERS NOTU GÖRÜNTÜLE
    // ============================
    @FXML
    private TextField textFieldTcNotG;

    @FXML
    private TableView<OrtalamaGorunum> tableViewDersNotG;
    @FXML private TableColumn<OrtalamaGorunum, String> kolonDersadi;
    @FXML private TableColumn<OrtalamaGorunum, Integer> kolonYazili1;
    @FXML private TableColumn<OrtalamaGorunum, Integer> kolonYazili2;
    @FXML private TableColumn<OrtalamaGorunum, Double> kolonOrtalama;
    @FXML private Label labelDersNotuG;


    @FXML
    private Label labelDersNotDurum;

    // ============================
    // ÖĞRETMEN EKLE
    // ============================
    @FXML
    private TextField textfFieldOgretIsim;

    @FXML
    private TextField textfieldOgretSoyisim;

    @FXML
    private TextField textfleldOgretTc;

    @FXML
    private TextField textFieldOgretBrans;

    @FXML
    private TextField TextFieldogretDersID;

    @FXML
    private DatePicker ogretKayitTarihi;

    @FXML
    private Button buttonogretmenEkle;

    private Label labeldurumMesajiOgret;

    private OgrenciDao ogrenciDao;

    public Yonteci_sisController() {
        // DAO sınıfını burada initialize ederiz

    }

    @FXML
    public void initialize() {
        MongoDatabase database = DBUtil.getInstance().getDatabase();
        ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));

        ekleButton.setOnAction(event -> ogrenciEkle());
    }

    private void ogrenciEkle() {


        String ad = txtAd.getText();
        String soyad = txtSoyad.getText();
        String tc = txtTc.getText();
        String ogrenciNo = txtOgrenciNo.getText();
        int sinifSeviyesi =Integer.parseInt(txtSinif.getText());
        LocalDate kayitTarihiOgrenciLocalDate = kayitTarihiOgrenci.getValue();

        if (ad.isEmpty() || soyad.isEmpty() || tc.isEmpty() || ogrenciNo.isEmpty() || sinifSeviyesi == 0) {
            durumMesajLabel.setText("Lütfen tüm alanları doldurun!");
            return;
        }

        try {

            Ogrenci yeniOgrenci = new Ogrenci(tc,ad,soyad,sinifSeviyesi,ogrenciNo,kayitTarihiOgrenciLocalDate);
                //ogrenciDao.ogrenciAdd(ad, soyad, tc, ogrenciNo,sinifSeviyesi);

                String sonuc = ogrenciDao.ogrenciAdd(yeniOgrenci);
                durumMesajLabel.setText(sonuc);

                txtAd.clear();
                txtSoyad.clear();
                txtTc.clear();
                txtOgrenciNo.clear();
                txtSinif.clear();
                kayitTarihiOgrenci.setValue(null);

        } catch (Exception e) {
            durumMesajLabel.setText("Hata oluştu: " + e.getMessage());
        }

    }
}


