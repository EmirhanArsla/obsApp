package com.example.obsapp.viewmodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
// 1. Alanlar SimpleProperty olarak tanımlanır

public class OrtalamaGorunum {
    private final SimpleStringProperty dersAdi;
    private final SimpleIntegerProperty sinav1;
    private final SimpleIntegerProperty sinav2;
    private final SimpleDoubleProperty ortalama;
    // Kurucu (Constructor)

    public OrtalamaGorunum (int sinav1 ,String dersAd ,double ortalama ,int sinav2){
        this.sinav1 = new SimpleIntegerProperty(sinav1);
        this.ortalama = new SimpleDoubleProperty(ortalama);
        this.sinav2= new SimpleIntegerProperty(sinav2);
        this.dersAdi = new SimpleStringProperty(dersAd);

    }
    // --- 3. Property Metotları (Binding için ZORUNLU) ---

    // JavaFX TableView, bu metotları (alan adı + "Property") çağırarak
    // Property nesnesinin kendisini alır ve binding'i kurar.
    public SimpleIntegerProperty sinav1Property() {return sinav1;
    }

    public SimpleDoubleProperty ortalamaProperty() {return ortalama;
    }

    public SimpleIntegerProperty sinav2Property() {return sinav2;
    }

    public SimpleStringProperty dersAdiProperty() {return dersAdi;
    }

    public String getDersAdi() {
        return dersAdi.get();
    }

    public int getSinav1() {
        return sinav1.get();
    }
    public int getSinav2() {
        return sinav2.get();
    }
    public double getOrtalama() {
        return ortalama.get();
    }


}
