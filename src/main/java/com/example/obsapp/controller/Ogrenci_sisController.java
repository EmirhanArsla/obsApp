package com.example.obsapp.controller;

import com.example.obsapp.Manager.RaporlamaManager;
import com.example.obsapp.Viewmodel.NotGorunum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Ogrenci_sisController implements Initializable {
    private RaporlamaManager roporlamaManager;
    private String gelen_ogrenciNo;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabDersNotu;   // "Ders Notu Görüntüle"

    @FXML
    private Tab tabGenelOrtalama;  // "Genel Ortalama Görüntüle"

    @FXML
    private Tab tabSinifOrtalama;  // "Sınıf Ortalaması"
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


    public void setOgrenciNo(String ogrenciNo) {
            this.gelen_ogrenciNo =ogrenciNo;
          //  loadOgrenciBilgiler();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Tab değişim dinleyicisi
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {

            if (newTab == tabDersNotu) {
                loadDersNotlari();
            } else if (newTab == tabGenelOrtalama) {
                loadGenelOrtalama();
            } else if (newTab == tabSinifOrtalama) {
                loadSinifOrtalamasi();
            }
        });


        //1.Tab'ın Sutunları ile Değerler eşleşmesi yapıldı

        dersSutun.setCellValueFactory(new PropertyValueFactory<>("dersId"));
        ogrenciNoSutun.setCellValueFactory(new PropertyValueFactory<>("ogrenciId"));
        SinifSutun.setCellValueFactory(new PropertyValueFactory<>("sinif"));
        sinav1Sutun.setCellValueFactory(new PropertyValueFactory<>("sinav1"));
        sinav2Sutun.setCellValueFactory(new PropertyValueFactory<>("sinav2"));


    }
    private void loadDersNotlari() {
        System.out.println("➤ Ders notları sekmesi açıldı.");
        if (gelen_ogrenciNo == null) {
            List<NotGorunum> notlarList = roporlamaManager.notGoruntule(gelen_ogrenciNo);

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

    private void loadSinifOrtalamasi() {
        System.out.println("➤ Sınıf ortalaması sekmesi açıldı.");
        // Sınıf listesi üzerinden ortalama hesaplanabilir
    }

}
