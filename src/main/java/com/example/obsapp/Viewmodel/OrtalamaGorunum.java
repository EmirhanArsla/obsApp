package com.example.obsapp.Viewmodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrtalamaGorunum {
    private final SimpleStringProperty dersAdi;
    private final SimpleIntegerProperty sinav1;
    private final SimpleIntegerProperty sinav2;
    private final SimpleDoubleProperty ortalama;

    public OrtalamaGorunum (int sinav1 ,String dersAd ,double ortalama ,int sinav2){
        this.sinav1 = new SimpleIntegerProperty(sinav1);
        this.ortalama = new SimpleDoubleProperty(ortalama);
        this.sinav2= new SimpleIntegerProperty(sinav2);
        this.dersAdi = new SimpleStringProperty(dersAd);

    }

    public SimpleIntegerProperty getDersId() {
        return sinav1;
    }

    public SimpleDoubleProperty getOrtalama() {
        return ortalama;
    }

    public SimpleIntegerProperty getKredi() {
        return sinav2;
    }

    public SimpleStringProperty getDersAdi() {return dersAdi;
    }

}
