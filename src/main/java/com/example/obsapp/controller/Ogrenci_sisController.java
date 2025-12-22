package com.example.obsapp.controller;

import com.example.obsapp.dao.DersDao;
import com.example.obsapp.dao.NotDao;
import com.example.obsapp.dao.OgrenciDao;
import com.example.obsapp.dao.YoneticiDao;
import com.example.obsapp.manager.RaporlamaManager;
import com.example.obsapp.viewmodel.GnoGorunum;
import com.example.obsapp.viewmodel.NotGorunum;
import com.example.obsapp.viewmodel.OgrenciGorunum;
import com.example.obsapp.viewmodel.OrtalamaGorunum;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


public class Ogrenci_sisController implements Initializable {

    private RaporlamaManager raporlamaManager;
    private String gelen_ogrenciTc;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabDersNotu;   // "Ders Notu Görüntüle"

    @FXML
    private Tab tabGenelOrtalama;  // "Genel Ortalama Görüntüle"

    @FXML
    private Tab tabDersOrtalama;  // "Ders Ortalaması"
    @FXML
    private Tab tapOgrenciBilgileri;

    // =========================
    // DERS NOTU TABLOSU
    // =========================
    @FXML
    private TableView<NotGorunum> dersNotuTablo;
    @FXML
    private TableColumn<NotGorunum,String> dersSutun ;
    @FXML
    private TableColumn<NotGorunum,String> ogrenciNoSutun;
    @FXML
    private TableColumn<NotGorunum,Integer> SinifSutun;
    @FXML
    private TableColumn<NotGorunum,Integer> sinav1Sutun;
    @FXML
    private TableColumn<NotGorunum,Integer> sinav2Sutun;

    // =========================
    // DERS ORTALAMASI TABLOSU
    // =========================
    @FXML
    private TableView<OrtalamaGorunum> tableviewDersOrt;
    @FXML
    private TableColumn<OrtalamaGorunum,String> dersOrtDersSutun;
    @FXML
    private TableColumn<OrtalamaGorunum,Integer> dersOrtSinav1;
    @FXML
    private TableColumn<OrtalamaGorunum,Integer> dersOrtSinav2;
    @FXML
    private TableColumn<OrtalamaGorunum,Double> dersOrtOrtalamaSutun;
    // ============================
    // Genel Ortalama görüntüle Tablosu
    // ============================
    @FXML
    private TableView<GnoGorunum> tableGenelOrt ;
    @FXML
    private TableColumn<GnoGorunum, String> genelOrt9Sutun;
    @FXML
    private TableColumn<GnoGorunum, String > genelOrt10Sutun;
    @FXML
    private TableColumn<GnoGorunum, String> genelOrt11Sutun;
    @FXML
    private TableColumn<GnoGorunum, Double> genelOrt12Sutun;
    // ============================
    // Ogrenci Bilgisi Tablosu
    // ============================
    @FXML
    private TableView<OgrenciGorunum> tableOgrenciBilgi ;
    @FXML
    private TableColumn<OgrenciGorunum, String> obAdSutun;
    @FXML
    private TableColumn<OgrenciGorunum, String> obSoyadSutun;
    @FXML
    private TableColumn<OgrenciGorunum, String> obTcSutun;
    @FXML
    private TableColumn<OgrenciGorunum, String> obOgrenciNoSutun;
    @FXML
    private TableColumn<OgrenciGorunum, Integer> obSinifSutun;
    @FXML
    private TableColumn<OgrenciGorunum, LocalDate> obKayitTarihiSutun;





    private YoneticiDao yoneticiDao;
    private NotDao notDao;
    private OgrenciDao ogrenciDao;
    private DersDao dersDao;


    //Önceki ekrandan gelen öğrenci TC bilgisini alır.
    //TC değeri başarıyla alındığında, ders notları otomatik olarak yüklenir
    // kullanıcı ders notları sekmesine yönlendirilir.
    public void setGelen_ogrenciTc (String tc) {
            this.gelen_ogrenciTc =tc;
            if (raporlamaManager != null) {

                loadDersNotlari();

                tabPane.getSelectionModel().select(tabDersNotu);
            }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Controller yüklendiğinde veritabanı bağlantısını kurar
        // ve DAO ile raporlama katmanlarını hazırlar

        MongoDatabase database = DBUtil.getInstance().getDatabase();
        ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));
        notDao= new NotDao(database.getCollection("Notlar"));
        yoneticiDao = new YoneticiDao(database.getCollection("Ogretmen"));
        dersDao= new DersDao(database.getCollection("Dersler"));
        this.raporlamaManager = new RaporlamaManager(notDao,dersDao,ogrenciDao);



        // Tab değişim dinleyicisi(tab her değiştinde otamtik olarak o tabı yükliyecek)
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {

            if (newTab == tabDersNotu) {
                loadDersNotlari();
            } else if (newTab == tabGenelOrtalama) {
                loadGenelOrtalama();
            } else if (newTab == tabDersOrtalama) {
                loadDersOrtalamasi();
            } else if (newTab == tapOgrenciBilgileri) {
                loadOgrenciBilgi();
            }
        });


        //1.Tab'ın (Ders Notları Tabı )Sutunları ile Değerler eşleşmesi yapıldı

        dersSutun.setCellValueFactory(new PropertyValueFactory<>("dersid"));
        ogrenciNoSutun.setCellValueFactory(new PropertyValueFactory<>("tc"));
        SinifSutun.setCellValueFactory(new PropertyValueFactory<>("sinif"));
        sinav1Sutun.setCellValueFactory(new PropertyValueFactory<>("sinav1"));
        sinav2Sutun.setCellValueFactory(new PropertyValueFactory<>("sinav2"));

        //2.Tab'ın (Ders Ortlama Tabı )Sutunları ile Değerler eşleşmesi yapıldı
        dersOrtDersSutun.setCellValueFactory(new PropertyValueFactory<>("dersAdi"));
        dersOrtSinav1.setCellValueFactory(new PropertyValueFactory<>("sinav1"));
        dersOrtSinav2.setCellValueFactory(new PropertyValueFactory<>("sinav2"));
        dersOrtOrtalamaSutun.setCellValueFactory(new PropertyValueFactory<>("ortalama"));
        //3.Tab'ın (Genel Ortalama Görüntüle ) Sutunları ile Değerle eşleşmesi yapıldı
        genelOrt9Sutun.setCellValueFactory(new PropertyValueFactory<>("ad"));
        genelOrt10Sutun.setCellValueFactory(new PropertyValueFactory<>("soyAd"));
        genelOrt11Sutun.setCellValueFactory(new PropertyValueFactory<>("tc"));
        genelOrt12Sutun.setCellValueFactory(new PropertyValueFactory<>("Gno"));

   //     4.Tab'ın(Ogrenci Bilgilerini Tabı)sutunları ile değerler eşleşmesi yapıldı
        obAdSutun.setCellValueFactory(new PropertyValueFactory<>("ad"));
        obSoyadSutun.setCellValueFactory(new PropertyValueFactory<>("soyAd"));
        obTcSutun.setCellValueFactory(new PropertyValueFactory<>("tc"));
        obSinifSutun.setCellValueFactory(new PropertyValueFactory<>("sinifSeviyesi"));
        obOgrenciNoSutun.setCellValueFactory(new PropertyValueFactory<>("ogrenciNo"));
        obKayitTarihiSutun.setCellValueFactory(new PropertyValueFactory<>("kayitTarihi"));

        obKayitTarihiSutun.setCellFactory(column-> new TableCell<OgrenciGorunum, LocalDate>(){
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            protected void updateItem(LocalDate localDate, boolean b) {
                super.updateItem(localDate, b);
                if (localDate == null || b  ){
                    setText(null);
                }else  {
                    setText(formatter.format(localDate));
                }
            }
        });

    }

    private void loadDersNotlari() {
        // Ders notları sekmesi açıldığında, ilgili öğrencinin ders notlarını tabloya yükler
        System.out.println("➤ Ders notları sekmesi açıldı.");
        if (gelen_ogrenciTc != null) {
            List<NotGorunum> notlarList =raporlamaManager.notGoruntule(gelen_ogrenciTc);

            dersNotuTablo.setItems(FXCollections.observableArrayList(notlarList));
        }
        else {
            System.err.println("Öğrenci TC'si Bulunamadi.");
        }
    }

    private void loadGenelOrtalama() {
        // Genel ortalama sekmesi açıldığında öğrencinin Genel Ortalama bilgisini yükler
        System.out.println("➤ Genel ortalama sekmesi açıldı.");
        if (gelen_ogrenciTc != null) {
            List<GnoGorunum> gnoList = raporlamaManager.gnoGetir(gelen_ogrenciTc);

            tableGenelOrt.setItems(FXCollections.observableArrayList(gnoList));
        }
        else {
            System.err.println("Öğrenci Bulunamadi.");
        }

    }

    private void loadDersOrtalamasi() {
        // Ders ortalama sekmesi açıldığında ilgili verileri yükler
        System.out.println("➤ Sınıf ortalaması sekmesi açıldı.");
    if (gelen_ogrenciTc != null) {
        List<OrtalamaGorunum> dersOrtlama = raporlamaManager.ortlamaGoster(gelen_ogrenciTc);

        tableviewDersOrt.setItems(FXCollections.observableArrayList(dersOrtlama));
    }
    else {
        System.err.println("Öğrenci Bulunamadi.");
    }
    }
    private void loadOgrenciBilgi() {
        // Öğrenci bilgi sekmesi açıldığında temel öğrenci bilgilerini yükler
        if (gelen_ogrenciTc != null) {
           OgrenciGorunum OgrenciB = ogrenciDao.ogrenciGorunumSearch(gelen_ogrenciTc);
            if(OgrenciB != null){
                tableOgrenciBilgi.setItems(FXCollections.observableArrayList(OgrenciB));
                System.out.println("Öğrenci bilgileri yüklendi");
            }
            else {
                System.err.println("Ogrenci Bulunamadi.");
            }
        }
        else {
            System.err.println("Ogrenci Bulunamadi.");
        }
    }

}
