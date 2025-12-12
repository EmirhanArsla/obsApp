package com.example.obsapp.Viewmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GnoGorunum {
    private final StringProperty ogrenciNo;
    private final StringProperty  Ad;
    private final StringProperty  soyAd;
    private final DoubleProperty Gno;

    public GnoGorunum(String ogrenciNo, String Ad, String soyAd,  double Gno) {
        this.ogrenciNo = new SimpleStringProperty(ogrenciNo);
        this.Ad = new SimpleStringProperty(Ad);
        this.soyAd = new SimpleStringProperty(soyAd);
        this.Gno = new SimpleDoubleProperty(Gno) ;
    }


    public StringProperty getOgrenciNo() {
        return ogrenciNo;
    }

    public StringProperty getAd() {
        return Ad;
    }

    public StringProperty getSoyAd() {
        return soyAd;
    }

    public DoubleProperty getGno() {
        return Gno;
    }
}
