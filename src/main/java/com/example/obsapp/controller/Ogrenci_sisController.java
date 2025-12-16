package com.example.obsapp.controller;

import com.example.obsapp.DBO.DersDao;
import com.example.obsapp.DBO.NotDao;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.DBO.OgretmenDao;
import com.example.obsapp.Manager.RaporlamaManager;
import com.example.obsapp.Viewmodel.GnoGorunum;
import com.example.obsapp.Viewmodel.NotGorunum;
import com.example.obsapp.Viewmodel.OgrenciGorunum;
import com.example.obsapp.Viewmodel.OrtalamaGorunum;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
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
    private TableView<GnoGorunum> tableviewGenelOrt ;
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
    private TableView<OgrenciGorunum> tableviewOgrenciBilgi ;
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





    private OgretmenDao ogretmenDao;
    private NotDao notDao;
    private OgrenciDao ogrenciDao;
    private DersDao dersDao;


    public void setGelen_ogrenciTc (String tc) {
            this.gelen_ogrenciTc =tc;
            if (raporlamaManager != null) {

                loadDersNotlari();

                tabPane.getSelectionModel().select(tabDersNotu);
            }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        MongoDatabase database = DBUtil.getInstance().getDatabase();
        ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));
        notDao= new NotDao(database.getCollection("Notlar"));
        ogretmenDao = new OgretmenDao(database.getCollection("Ogretmen"));
        dersDao= new DersDao(database.getCollection("Dersler"));
        this.raporlamaManager = new RaporlamaManager(notDao,dersDao,ogrenciDao);



        // Tab değişim dinleyicisi
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {

            if (newTab == tabDersNotu) {
                loadDersNotlari();
            } else if (newTab == tabGenelOrtalama) {
                loadGenelOrtalama();
            } else if (newTab == tabDersOrtalama) {
                loadDersOrtalamasi();
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


        //4.Tab'ın(Ogrenci Bilgilerini Tabı)sutunları ile değerler eşleşmesi yapıldı
//        obAdSutun.setCellValueFactory(new PropertyValueFactory<>("ad"));
//        obSoyadSutun.setCellValueFactory(new PropertyValueFactory<>("soyAd"));
//        obTcSutun.setCellValueFactory(new PropertyValueFactory<>("tc"));
//        obSinifSutun.setCellValueFactory(new PropertyValueFactory<>("sinifSeviyesi"));
//        obOgrenciNoSutun.setCellValueFactory(new PropertyValueFactory<>("ogrenciNo"));
//        obKayitTarihiSutun.setCellValueFactory(new PropertyValueFactory<>("kayitTarihi"));

//        obKayitTarihiSutun.setCellFactory(column-> new TableCell<OgrenciGorunum, LocalDate>(){
//            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//
//            @Override
//            protected void updateItem(LocalDate localDate, boolean b) {
//                super.updateItem(localDate, b);
//                if (localDate == null || b  ){
//                    setText(null);
//                }else  {
//                    setText(formatter.format(localDate));
//                }
//            }
//        });

    }
    private void loadDersNotlari() {
        System.out.println("➤ Ders notları sekmesi açıldı.");
        if (gelen_ogrenciTc != null) {
            List<NotGorunum> notlarList =raporlamaManager.notGoruntule(gelen_ogrenciTc);

            dersNotuTablo.setItems(FXCollections.observableArrayList(notlarList));
        }
        else {
            System.err.println("Öğrenci Numarası Bulunamadi.");
        }
    }

    private void loadGenelOrtalama() {
        System.out.println("➤ Genel ortalama sekmesi açıldı.");
        // HesaplamaUtil ile genel ortalama hesaplanabilir
    }

    private void loadDersOrtalamasi() {
        System.out.println("➤ Sınıf ortalaması sekmesi açıldı.");
    if (gelen_ogrenciTc != null) {
        List<OrtalamaGorunum> dersOrtlama = raporlamaManager.ortlamaGoster(gelen_ogrenciTc);

        tableviewDersOrt.setItems(FXCollections.observableArrayList(dersOrtlama));
    }
    else {
        System.err.println("Öğrenci Bulunamadi.");
    }
    }

}
