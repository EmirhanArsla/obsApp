package com.example.obsapp.Viewmodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrtalamaGorunum {
    private final SimpleStringProperty DersId;
    private final SimpleStringProperty DersAdi;
    private final SimpleDoubleProperty Ortalama;
    private final SimpleIntegerProperty Kredi;

    public OrtalamaGorunum (String dersid,String dersAd ,double Ortalama ,int Kredi){
        this.DersId = new SimpleStringProperty(dersid);
        this.Ortalama = new SimpleDoubleProperty(Ortalama);
        this.Kredi = new SimpleIntegerProperty(Kredi);
        this.DersAdi = new SimpleStringProperty(dersAd);

    }

    public SimpleStringProperty getDersId() {
        return DersId;
    }

    public SimpleDoubleProperty getOrtalama() {
        return Ortalama;
    }

    public SimpleIntegerProperty getKredi() {
        return Kredi;
    }

    public SimpleStringProperty getDersAdi() {return DersAdi;
    }

}
