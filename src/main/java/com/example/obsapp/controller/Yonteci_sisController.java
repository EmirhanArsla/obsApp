package com.example.obsapp.controller;

import com.example.obsapp.dao.DersDao;
import com.example.obsapp.dao.NotDao;
import com.example.obsapp.dao.OgrenciDao;
import com.example.obsapp.dao.YoneticiDao;
import com.example.obsapp.exception.BosAlanException;
import com.example.obsapp.exception.OgrenciBulunamadiException;
import com.example.obsapp.manager.RaporlamaManager;
import com.example.obsapp.viewmodel.GnoGorunum;
import com.example.obsapp.viewmodel.OgrenciGorunum;
import com.example.obsapp.viewmodel.OrtalamaGorunum;
import com.example.obsapp.model.Not;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.model.Yonetici;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Yonteci_sisController {
    private Document yoneticiBilgisi;

    public void setYoneticiBilgisi(Document yoneticiBilgisi) {
        this.yoneticiBilgisi = yoneticiBilgisi;

        // Örnek: Label'e yazdır
        System.out.println("Giriş yapan yönetici: "
                + yoneticiBilgisi.getString("kullaniciAdi"));
    }

    @FXML
    private TabPane tabPane;
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
    @FXML
    private Tab gnoTab;
    @FXML
    private Button anaMenuDonus;

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
    private TableView<OgrenciGorunum> tableViewOgrenciBilgileri;

    @FXML
    private Label labelDurumOgrenciAra;
    @FXML
    private TableColumn<OgrenciGorunum, String> kolonAraIsim;
    @FXML
    private TableColumn<OgrenciGorunum, String> kolonAraSoyisim;
    @FXML
    private TableColumn<OgrenciGorunum, String> kolonAraTc;
    @FXML
    private TableColumn<OgrenciGorunum, String> kolonAraOgrenciNo;
    @FXML
    private TableColumn<OgrenciGorunum, Integer> kolonAraSinif;
    @FXML
    private TableColumn<OgrenciGorunum, LocalDate> kolonAraKayitTarihi;


    // ============================
    // DERS NOTU EKLE
    // ============================
    @FXML
    private TextField textFieldOgrenciTcDersEKle;

    @FXML
    private ChoiceBox<String> textFieldDersIdEkle;

    @FXML
    private TextField textFieldYazili1;

    @FXML
    private TextField textFieldYazili2;

    @FXML
    private Button buttonNotEkle;
    @FXML
    private Label labelDurumDersNot;
    @FXML
    private ChoiceBox<Integer> notEkleSinif;

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
    private Button buttonNotlariAra;
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
    @FXML
    private Label labelODurumMesaji;
    // ============================
    // GNO GÖRÜNTÜLE
    // ============================
    @FXML
    private TableView<GnoGorunum> gnoTable;
    @FXML
    private TableColumn<GnoGorunum, String> gnoOgrenciNo;
    @FXML
    private TableColumn<GnoGorunum, String> gnoIsim;
    @FXML
    private TableColumn<GnoGorunum, String> gnoSoyisim;
    @FXML
    private TableColumn<GnoGorunum, Double> gnoGenelOrt;
    // ============================
    // SINIF ORTALAMA
    // ============================
    @FXML
    private ChoiceBox<Integer> choiceBoxSinifOrt;
    @FXML
    private Button buttonSinifOrt;
    @FXML
    private Label labelSinifOrt;
    @FXML
    private Label sinifOrtLabel;


    private OgrenciDao ogrenciDao;
    private NotDao notDao;
    private YoneticiDao yoneticiDao;
    private DersDao dersDao;
    private RaporlamaManager raporlamaManager;


    @FXML
    private void handleSayfaAc(ActionEvent event) {
        try {
            // 1. FXML dosyasının yolunu belirtin (Örn: "/com/example/obsapp/DashboardView.fxml")
            String fxmlPath = "/com/example/obsapp/hello-view.fxml";
            String title = "Giris Ekranı";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // 2. Yeni sahneyi (Scene) oluşturun
            Scene scene = new Scene(root);

            // 3. Mevcut pencereyi (Stage) buton üzerinden bulun
            // Bu yöntem, 'yoneticiKullanici' gibi spesifik bir değişkene bağımlılığı ortadan kaldırır.
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 4. Mevcut pencerenin içeriğini değiştirin (Yeni pencere açmak yerine geçiş yapar)
            currentStage.setTitle(title);
            currentStage.setScene(scene);
            currentStage.show();

            // Opsiyonel: Eğer veriyi yeni Controller'a aktarmanız gerekiyorsa:
            // HedefController controller = loader.getController();
            // controller.setData("Veri...");

        } catch (IOException e) {
            System.err.println("Sayfa yüklenirken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        //seçnekli kutuların seçenekleri yazılır
        List<Integer> siniflar = Arrays.asList(9, 10, 11, 12);
        List<String> dersler = Arrays.asList("Matematik", "Türkçe", "Fizik", "Kimya", "Biyoloji", "Tarih", "Cografya", "Din Kültürü ve Ahlak Bilgisi", "İngilizce",
                "Beden Eğitimi", "Görsel Sanatlar/Müzik", "Zeka oyunları", "Felsefe");
        //seçenkeli kutulara değerler atanır
        sinifBox.setItems(FXCollections.observableArrayList(siniflar));
        choiceBoxSinifOrt.setItems(FXCollections.observableArrayList(siniflar));
        notEkleSinif.setItems(FXCollections.observableArrayList(siniflar));
        textFieldDersIdEkle.setItems(FXCollections.observableArrayList(dersler));


        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == gnoTab) {
                gnolariGetir();
            }
        });

        //Sınıfların Database sınıflarıyla bağlantı kurabilmeleri için oluşturulan nesneler
        MongoDatabase database = DBUtil.getInstance().getDatabase();
        ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));
        notDao = new NotDao(database.getCollection("Notlar"));
        yoneticiDao = new YoneticiDao(database.getCollection("Ogretmen"));
        dersDao = new DersDao(database.getCollection("Dersler"));
        raporlamaManager = new RaporlamaManager(notDao, dersDao, ogrenciDao);

        ekleButton.setOnAction(event -> ogrenciEkle());
        ogrenciSilButton.setOnAction(e -> ogrenciSil());
        buttonOgrenciAra.setOnAction(e -> ogrenciAra());
        buttonNotEkle.setOnAction(e -> notEkle());
        buttonOgretmenEkle.setOnAction(e -> yoneticiEkle());
        buttonNotlariAra.setOnAction(e -> dersNotuGoruntule());
        buttonSinifOrt.setOnAction(e -> sinifOrtlamasi());

        //Ogrenci Ara  için tablolaro değişkenle bağlıyoruz
        kolonAraIsim.setCellValueFactory(new PropertyValueFactory<>("ad"));
        kolonAraSoyisim.setCellValueFactory(new PropertyValueFactory<>("soyAd"));
        kolonAraTc.setCellValueFactory(new PropertyValueFactory<>("tc"));
        kolonAraSinif.setCellValueFactory(new PropertyValueFactory<>("sinifSeviyesi"));
        kolonAraOgrenciNo.setCellValueFactory(new PropertyValueFactory<>("ogrenciNo"));
        kolonAraKayitTarihi.setCellValueFactory(new PropertyValueFactory<>("kayitTarihi"));

        kolonAraKayitTarihi.setCellFactory(column -> new TableCell<OgrenciGorunum, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            protected void updateItem(LocalDate localDate, boolean b) {
                super.updateItem(localDate, b);
                if (localDate == null || b) {
                    setText(null);
                } else {
                    setText(formatter.format(localDate));
                }
            }
        });

        //Ders Notları için Tabloları birbirine bağlıyoruz
        kolonDersadi.setCellValueFactory(new PropertyValueFactory<>("dersAdi"));
        kolonYazili1.setCellValueFactory(new PropertyValueFactory<>("sinav1"));
        kolonYazili2.setCellValueFactory(new PropertyValueFactory<>("sinav2"));
        kolonOrtalama.setCellValueFactory(new PropertyValueFactory<>("ortalama"));

        //Tüm ganolar İçin Tablolaro biribne bağlıyoruz
        gnoOgrenciNo.setCellValueFactory(new PropertyValueFactory<>("tc"));
        gnoIsim.setCellValueFactory(new PropertyValueFactory<>("ad"));
        gnoSoyisim.setCellValueFactory(new PropertyValueFactory<>("soyAd"));
        gnoGenelOrt.setCellValueFactory(new PropertyValueFactory<>("gno"));

    }

    public void sinifOrtlamasi() {
        try {

            Integer sinif = choiceBoxSinifOrt.getValue();
            if (sinif == null) {
                throw new BosAlanException("Sınıf seçilmedi");
            }

            double sinifOrtlamasi = raporlamaManager.sinifOrtalama(sinif);
            labelSinifOrt.setText(sinif + " Sinif Ortalama: " + sinifOrtlamasi);
        } catch (BosAlanException e) {
            labelSinifOrt.setText(e.getMessage());
        } catch (Exception e) {
            System.err.println("Ortalama Getirilemedi: " + e.getMessage());

        }
    }


    public void gnolariGetir() {
        try {
            List<GnoGorunum> gnoGeti = raporlamaManager.tumGnolar();
            gnoTable.setItems(FXCollections.observableArrayList(gnoGeti));
        } catch (Exception e) {
            System.err.println("Ders notları getirilmedi ");

        }
    }


    private void dersNotuGoruntule() {
        try {
            String tc = textFieldTcNotG.getText();
            if (tc.isEmpty()) {
                throw new BosAlanException("Tc Girilmedi");
            }
            if (tc.length() != 11) {
                throw new IllegalArgumentException("TC Kimlik Numarası 11 hane olmalıdır.");
            }
            if (!ogrenciDao.tcKontrol(tc)) {
                throw new OgrenciBulunamadiException("Öğrenci Bulunamadi");
            }


            List<OrtalamaGorunum> dersOrtlama = raporlamaManager.ortlamaGoster(tc);
            tableViewDersNotG.setItems(FXCollections.observableArrayList(dersOrtlama));
            labelDersNotDurum.setText("Ders Notları Getirildi");
            textFieldTcNotG.clear();
        }
        catch (IllegalArgumentException e) {
            labelDersNotDurum.setText(e.getMessage());
        }
        catch (BosAlanException | OgrenciBulunamadiException e) {
            labelDersNotDurum.setText(e.getMessage());
        } catch (Exception e) {

            labelDersNotDurum.setText("Ders Notları Getirilemedi: " + e.getMessage());

        }

    }


    private void yoneticiEkle() {
        try {
            String isim = textfieldOgretIsim.getText();
            String soyisim = textfieldOgretSoyisim.getText();
            String tc = textfieldOgretTc.getText();
            String sifre = textfieldOgretBrans.getText();
            LocalDate ogretmenKayitTarihi = ogretKayitTarihi.getValue();


            if (tc.length() != 11) {
                throw new IllegalArgumentException("TC Kimlik Numarası 11 hane olmalıdır.");

            }
            if (yoneticiDao.tcKontrol(tc)) {
                labelODurumMesaji.setText("Aynı Tc Önceden Kullanılmış ");
                return;
            }
            if (isim.isEmpty() || soyisim.isEmpty() || sifre.isEmpty()) {
                labelODurumMesaji.setText("Lütfen tüm alanları doldurun!");
                throw new BosAlanException("Tüm Alanlar Doldurulmadı.");
            }
            Yonetici yonetici = new Yonetici(tc, isim, soyisim, sifre, ogretmenKayitTarihi);

            yoneticiDao.yoneticiAdd(yonetici);
            labelODurumMesaji.setText("Yönetici Sisteme Eklendi.");

            textfieldOgretIsim.clear();
            textfieldOgretSoyisim.clear();
            textfieldOgretTc.clear();
            textfieldOgretBrans.clear();

        }
        catch (BosAlanException | IllegalArgumentException e) {
            labelODurumMesaji.setText(e.getMessage());
        } catch (Exception e) {
            labelODurumMesaji.setText
                    ("Hata oluştu: " + e.getMessage());
        }
    }

    private void notEkle() {
        try {
            String tc = textFieldOgrenciTcDersEKle.getText();
            String dersad = textFieldDersIdEkle.getValue();
            Integer sinif = notEkleSinif.getValue();
            String sinav1Text = textFieldYazili1.getText();
            String sinav2Text = textFieldYazili2.getText();


            if (tc.length() != 11) {
                throw new IllegalArgumentException("TC Kimlik Numarası 11 hane olmalıdır.");
            }

            if (!ogrenciDao.tcKontrol(tc)) {
                labelDurumDersNot.setText("TC Bulunamadı ");
                return;
            }

            int sinav1 = Integer.parseInt(sinav1Text);
            int sinav2 = Integer.parseInt(sinav2Text);

            if (sinav1 < 0 || sinav1 > 100 || sinav2 < 0 || sinav2 > 100) {

                throw new IllegalArgumentException("Notlar 0-100 arasında olmalıdır");

            }
            if (notDao.notidKontrol(tc+"-"+dersad)) {
                labelDurumDersNot.setText(" Aynı Not Eklenmiş ");
                return;
            }

            if ( dersad == null | sinif == null || sinav2Text.isEmpty() || sinav1Text.isEmpty()) {
                labelDurumDersNot.setText("Lütfen Tüm Alanları Doldurunuz");
                throw new BosAlanException("Tüm Alanlar Doldurulmadı");
            }

            Not yeninot = new Not(tc, dersad, sinav1, sinav2, sinif);
            notDao.notadd(yeninot);
            textFieldOgrenciTcDersEKle.clear();
            textFieldDersIdEkle.setValue(" ");
            textFieldYazili1.clear();
            textFieldYazili2.clear();
            labelDurumDersNot.setText("Not Eklendi");

        } catch (BosAlanException | IllegalArgumentException e) {
            labelDurumDersNot.setText(e.getMessage());
        } catch (Exception e) {
            labelDurumDersNot.setText
                    ("Hata oluştu: " + e.getMessage());
        }
    }

    private void ogrenciAra() {
        try {
            String tc = textFieldOgrenciAraTC.getText();

            if (tc.isEmpty()) {
                throw new BosAlanException("Tc Girilmedi.");
            }
            if (tc.length() != 11) {
                throw new IllegalArgumentException("TC Kimlik Numarası 11 hane olmalıdır.");
            }


            OgrenciGorunum ogrenciGorunum = ogrenciDao.ogrenciGorunumSearch(tc);
            if (ogrenciGorunum != null) {
                ObservableList<OgrenciGorunum> ogrenciListesi = FXCollections.observableArrayList(ogrenciGorunum);

                tableViewOgrenciBilgileri.setItems(ogrenciListesi);
                labelDurumOgrenciAra.setText("Ogrenci Bilgileri Getirildi");
                textFieldOgrenciAraTC.clear();
            } else {

                throw new OgrenciBulunamadiException();
            }


        } catch (BosAlanException | IllegalArgumentException e) {
            labelDurumOgrenciAra.setText(e.getMessage());
        } catch (Exception e) {
            labelDurumOgrenciAra.setText("Hata oluştu: " + e.getMessage());
        }

    }

    private void ogrenciSil() {
        try {
            String tc = tcsilTextField.getText();

            if (tc.isEmpty()) {
                throw new BosAlanException("Tc Girilmedi.");
            }

            if (tc.length() != 11) {
                throw new IllegalArgumentException("TC Kimlik Numarası 11 hane olmalıdır.");
            }

            if (!ogrenciDao.tcKontrol(tc)) {
                durumMesajLabel.setText("TC Bulunamadı ");
                return;
            }

            String msg = ogrenciDao.ogrenciDelete(tc);
            durumMesajLabel.setText(msg);
            tcsilTextField.clear();

        } catch (BosAlanException | IllegalArgumentException e) {
            durumMesajLabel.setText(e.getMessage());
        } catch (OgrenciBulunamadiException e) {
            durumMesajLabel.setText("Hata oluştu: " + e.getMessage());
        }
    }

    private void ogrenciEkle() {
        try {

            String ad = txtAd.getText();
            String soyad = txtSoyad.getText();
            String tc = txtTc.getText();
            String ogrenciNo = txtOgrenciNo.getText();
            Integer sinifSeviyesi = sinifBox.getValue();
            LocalDate kayitTarihiOgrenciLocalDate = kayitTarihiOgrenci.getValue();

            if (tc.length() != 11) {
                throw new IllegalArgumentException("TC Kimlik Numarası 11 hane olmalıdır.");
            }

            if (ad.isEmpty() || soyad.isEmpty() || ogrenciNo.isEmpty() || sinifSeviyesi == 0) {
                throw new BosAlanException("Tüm Alanlar Doldurulmadı.");
            }


            Ogrenci yeniOgrenci = new Ogrenci(tc, ad, soyad, sinifSeviyesi, ogrenciNo, kayitTarihiOgrenciLocalDate);


            String sonuc = ogrenciDao.ogrenciAdd(yeniOgrenci);
            durumMesajLabel.setText(sonuc);

            txtAd.clear();
            txtSoyad.clear();
            txtTc.clear();
            txtOgrenciNo.clear();
            sinifBox.setValue(null);
            kayitTarihiOgrenci.setValue(null);

        } catch (BosAlanException | IllegalArgumentException e) {
            durumMesajLabel.setText(e.getMessage());
        } catch (Exception e) {
            durumMesajLabel.setText("Hata oluştu: " + e.getMessage());
        }

    }

}




