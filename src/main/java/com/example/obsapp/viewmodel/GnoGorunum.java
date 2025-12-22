package com.example.obsapp.viewmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
// 1. Alanlar SimpleProperty olarak tanımlanır

public class GnoGorunum {
    private final StringProperty tc;
    private final StringProperty ad;
    private final StringProperty  soyAd;
    private final DoubleProperty gno;
    // Kurucu (Constructor)

    public GnoGorunum(String tc, String Ad, String soyAd, double Gno) {
        this.tc = new SimpleStringProperty(tc);
        this.ad = new SimpleStringProperty(Ad);
        this.soyAd = new SimpleStringProperty(soyAd);
        this.gno = new SimpleDoubleProperty(Gno) ;
    }

    public StringProperty tcProperty() {return tc;}

    public StringProperty adProperty() {return ad;}

    public StringProperty soyAdProperty() {return soyAd;}

    public DoubleProperty gnoProperty() {return gno;}

    // --- 2. Property Metotları (Binding için ZORUNLU) ---

    // JavaFX TableView, bu metotları (alan adı + "Property") çağırarak
    // Property nesnesinin kendisini alır ve binding'i kurar.

    public String getTc() {
        return tc.get();
    }

    public String getAd() {return ad.get();}

    public String getSoyAd() {
        return soyAd.get();
    }

    public double getGno() {
        return gno.get();
    }
}
