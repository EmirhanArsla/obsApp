package com.example.obsapp.controller;
import com.example.obsapp.DBO.DersDao;
import com.example.obsapp.DBO.NotDao;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.DBO.OgretmenDao;
import com.example.obsapp.Viewmodel.OrtalamaGorunum;
import com.example.obsapp.model.DersBase;
import com.example.obsapp.model.Not;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.model.Ogretmen;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Yonteci_sisController {
    @FXML
    private Tab ogrenciDuzenle;

    @FXML
    private Tab ogrenciAra;

    @FXML
    private Tab dersNotuEKle;

    @FXML
    private Tab dersNotuGoruntule;

    @FXML
    private Tab ogretmenEkle;

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
    private ChoiceBox<Integer> sinifBox;

    @FXML
    private DatePicker kayitTarihiOgrenci;

    @FXML
    private Button ekleButton;

    @FXML
    private TextField tcsilTextField;

    @FXML
    private Button ogrenciSilButton;

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
    @FXML
    private TableColumn<Ogrenci, String> kolonAraIsim;
    @FXML
    private TableColumn<Ogrenci, String> kolonAraSoyisim;
    @FXML
    private TableColumn<Ogrenci, String> kolonAraTc;
    @FXML
    private TableColumn<Ogrenci, LocalDate> kolonAraKayitTarihi;
    @FXML
    private TableColumn<Ogrenci, String> kolonAraSinif;


    // ============================
    // DERS NOTU EKLE
    // ============================
    @FXML
    private TextField textFieldOgrenciTcDersEKle;

    @FXML
    private TextField textFieldDersIdEkle;

    @FXML
    private TextField textFieldYazili1;

    @FXML
    private TextField textFieldYazili2;

    @FXML
    private Button buttonNotEkle;
    @FXML
    private Label labelDurumDersNot;
    @FXML
    private TextField notEkleSinif;

    // ============================
    // DERS NOTU GÖRÜNTÜLE
    // ============================
    @FXML
    private TextField textFieldTcNotG;

    @FXML
    private TableView<OrtalamaGorunum> tableViewDersNotG;
    @FXML
    private TableColumn<OrtalamaGorunum, String> kolonDersadi;
    @FXML
    private TableColumn<OrtalamaGorunum, Integer> kolonYazili1;
    @FXML
    private TableColumn<OrtalamaGorunum, Integer> kolonYazili2;
    @FXML
    private TableColumn<OrtalamaGorunum, Double> kolonOrtalama;
    @FXML
    private Label labelDersNotuG;


    @FXML
    private Label labelDersNotDurum;

    // ============================
    // ÖĞRETMEN EKLE
    // ============================
    @FXML
    private TextField textfieldOgretIsim;

    @FXML
    private TextField textfieldOgretSoyisim;

    @FXML
    private TextField textfieldOgretTc;

    @FXML
    private TextField textfieldOgretBrans;

    @FXML
    private TextField textfieldOgretDersID;

    @FXML
    private DatePicker ogretKayitTarihi;

    @FXML
    private Button buttonOgretmenEkle;

    private Label labeldurumMesajiOgret;

    private OgrenciDao ogrenciDao;
    private NotDao notDao;
    private OgretmenDao ogretmenDao;
    private DersDao dersDao;

    public Yonteci_sisController() {
        // DAO sınıfını burada initialize ederiz

    }

    @FXML
    public void initialize() {

        List<Integer> siniflar = Arrays.asList(9,10,11,12);
        sinifBox.setItems(FXCollections.observableArrayList(siniflar));

        //Sınıfların Database sınıflarıyla bağlantı kurabilmeleri için oluşturulan nesneler
        MongoDatabase database = DBUtil.getInstance().getDatabase();
        ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));
        notDao = new NotDao(database.getCollection("Notlar"));
        ogretmenDao = new OgretmenDao(database.getCollection("Ogretmen"));
        dersDao = new DersDao(database.getCollection("Dersler"));

        ekleButton.setOnAction(event -> ogrenciEkle());
        ogrenciSilButton.setOnAction(e -> ogrenciSil());
        buttonOgrenciAra.setOnAction(e-> ogrenciAra());
        buttonNotEkle.setOnAction(e->notEkle());
        buttonOgretmenEkle.setOnAction(e->ogretmenEkle());

    }

    private void ogretmenEkle() {
        String isim = textfieldOgretIsim.getText();
        String soyisim = textfieldOgretSoyisim.getText();
        String tc = textfieldOgretTc.getText();
        String bransh = textfieldOgretBrans.getText();
        String dersid =  textfieldOgretDersID.getText();
        LocalDate ogretmenKayitTarihi = ogretKayitTarihi.getValue();

        if (isim.isEmpty() || soyisim.isEmpty() || tc.isEmpty() || bransh.isEmpty() || dersid.isEmpty() ){
            labeldurumMesajiOgret.setText("Lütfen tüm alanları doldurun!");
            return;
        }
        try{
            Ogretmen ogretmen = new Ogretmen(tc,isim,soyisim,bransh,ogretmenKayitTarihi);

            ogretmenDao.ogretmenAdd(ogretmen);



        }
        catch (Exception e ){
            labeldurumMesajiOgret.setText
                    ("Hata oluştu: " + e.getMessage());
        }
    }

    private void notEkle() {
        String tc = textFieldOgrenciTcDersEKle.getText();
        String dersad = textFieldDersIdEkle.getText();
        int sinif =Integer.parseInt(notEkleSinif.getText());
        int sinav1=Integer.parseInt(textFieldYazili1.getText());
        int sinav2=Integer.parseInt(textFieldYazili2.getText());
        if (tc.isEmpty()||dersad.isEmpty()||sinif==0||sinav2==0||sinav1==0){
            labelDurumDersNot.setText("Lütfen Tüm Alanları Doldurunuz");
            return;
        }
        if (!ogrenciDao.tcKontrol(tc)){
            labelDurumDersNot.setText("Tc Bulunamadı ");
        }

        if (dersDao.dersidKontrol(sinif+dersad)){
            labelDurumDersNot.setText("Ders Bulunamadı");
        }
        try{
            Not yeninot = new Not(tc,dersad,sinav1,sinav2,sinif);
            notDao.notadd(yeninot);

        }
        catch (Exception e) {
            labelDurumDersNot.setText
            ("Hata oluştu: " + e.getMessage());
        }

    }

    private void ogrenciAra() {
        String tc = textFieldOgrenciAraTC.getText();
        if(tc.isEmpty()){
            labelDurumOgrenciAra.setText("Lütfen Alanı Doldurunuz");
            return;
        }
        try{
            Document doc = ogrenciDao.ogrencisearch(tc);
            textFieldOgrenciAraTC.clear();


        }catch (Exception e){
            labelDurumOgrenciAra.setText("Hata oluştu: " + e.getMessage());
        }

    }

    private void ogrenciSil() {
        String tc = tcsilTextField.getText();
        if(tc.isEmpty()){
            durumMesajLabel.setText("Lütfen tüm alanları doldurun!");
            return;
        }
        try{
            ogrenciDao.ogrenciDelete(tc);
            tcsilTextField.clear();
        }
        catch (Exception e){
            durumMesajLabel.setText("Hata oluştu: " + e.getMessage());
        }
    }

    private void ogrenciEkle() {


        String ad = txtAd.getText();
        String soyad = txtSoyad.getText();
        String tc = txtTc.getText();
        String ogrenciNo = txtOgrenciNo.getText();
        Integer sinifSeviyesi = sinifBox.getValue();
        LocalDate kayitTarihiOgrenciLocalDate = kayitTarihiOgrenci.getValue();

        if (ad.isEmpty() || soyad.isEmpty() || tc.isEmpty() || ogrenciNo.isEmpty() || sinifSeviyesi == 0) {
            durumMesajLabel.setText("Lütfen tüm alanları doldurun!");
            return;
        }

        try {

            Ogrenci yeniOgrenci = new Ogrenci(tc, ad, soyad, sinifSeviyesi, ogrenciNo, kayitTarihiOgrenciLocalDate);
            //ogrenciDao.ogrenciAdd(ad, soyad, tc, ogrenciNo,sinifSeviyesi);

            String sonuc = ogrenciDao.ogrenciAdd(yeniOgrenci);
            durumMesajLabel.setText(sonuc);

            txtAd.clear();
            txtSoyad.clear();
            txtTc.clear();
            txtOgrenciNo.clear();
            sinifBox.setValue(null);
            kayitTarihiOgrenci.setValue(null);

        } catch (Exception e) {
            durumMesajLabel.setText("Hata oluştu: " + e.getMessage());
        }

    }
}




