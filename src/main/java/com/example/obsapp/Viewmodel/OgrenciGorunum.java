package com.example.obsapp.Viewmodel;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class OgrenciGorunum {
    private final StringProperty ad;
    private final StringProperty soyAd;
    private final StringProperty tc;
    private final StringProperty ogrenciNo;
    private final IntegerProperty sinifSeviyesi;
    private final ObjectProperty kayitTarihi;

    public OgrenciGorunum(String ad,String soyAd,String tc,String ogrenciNo,Integer sinifSeviyesi,LocalDate kayitTarihi) {
        this.ad = new SimpleStringProperty(ad);
        this.soyAd = new SimpleStringProperty(soyAd);
        this.tc = new SimpleStringProperty(tc);
        this.ogrenciNo = new SimpleStringProperty(ogrenciNo);
        this.sinifSeviyesi = new SimpleIntegerProperty(sinifSeviyesi);
        this.kayitTarihi = new SimpleObjectProperty<>(kayitTarihi);
    }



    public StringProperty adProperty() {
        return ad;
    }

    public StringProperty soyAdProperty() {
        return soyAd;
    }

    public StringProperty tcProperty() {
        return tc;
    }

    public StringProperty ogrenciNoProperty() {
        return ogrenciNo;
    }

    public IntegerProperty sinifSeviyesiProperty() {
        return sinifSeviyesi;
    }

    public ObjectProperty kayitTarihiProperty() {
        return kayitTarihi;
    }

    public Integer getSinifSeviyesi() {
        return sinifSeviyesi.get();
    }
    public String getAd() {
        return ad.get();
    }
    public String getSoyAd() {
        return soyAd.get();
    }
    public String getTc() {
        return tc.get();
    }
    public String getOgrenciNo() {
        return ogrenciNo.get();
    }
    public LocalDate getKayitTarihi() {
        return (LocalDate) kayitTarihi.get();
    }
    public Integer getSinifSeviyes() {
        return sinifSeviyesi.get();

    }


}
