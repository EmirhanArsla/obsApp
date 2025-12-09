package com.example.obsapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Ogrenci_sisController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabDersNotu;   // "Ders Notu Görüntüle"

    @FXML
    private Tab tabGenelOrtalama;  // "Genel Ortalama Görüntüle"

    @FXML
    private Tab tabSinifOrtalama;  // "Sınıf Ortalaması"

    @FXML
    private Tab tabOther; // "Untitled Tab"

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Tab değişim dinleyicisi
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {

            if (newTab == tabDersNotu) {
                loadDersNotlari();
            }
            else if (newTab == tabGenelOrtalama) {
                loadGenelOrtalama();
            }
            else if (newTab == tabSinifOrtalama) {
                loadSinifOrtalamasi();
            }
            else if (newTab == tabOther) {
                loadOtherTab();
            }
        });
    }

    // ===========================
    // TAB FONKSİYONLARI
    // ===========================

    private void loadDersNotlari() {
        System.out.println("➤ Ders notları sekmesi açıldı.");
        // Burada öğrenci notları MongoDB'den yüklenebilir
    }

    private void loadGenelOrtalama() {
        System.out.println("➤ Genel ortalama sekmesi açıldı.");
        // HesaplamaUtil ile genel ortalama hesaplanabilir
    }

    private void loadSinifOrtalamasi() {
        System.out.println("➤ Sınıf ortalaması sekmesi açıldı.");
        // Sınıf listesi üzerinden ortalama hesaplanabilir
    }

    private void loadOtherTab() {
        System.out.println("➤ Diğer sekme açıldı.");
    }
}
