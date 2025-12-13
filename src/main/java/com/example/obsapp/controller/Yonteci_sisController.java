package com.example.obsapp.controller;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private DatePicker KayitTarihiOgrenci;

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
    private TableView<?> tableViewOgrenciBilgileri;

    @FXML
    private Label labelDurumOgrenciAra;

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
    private TableView<?> tableViewDersNotG;

    @FXML
    private Label labelDersNotuG;

    // ============================
    // ÖĞRETMEN EKLE
    // ============================
    @FXML
    private TextField textfFieldOgretIsım;

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


