package com.example.obsapp.Viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class NotGorunum { // Sınıf adını karışmaması için NotGorunumFx olarak değiştirdim

    // 1. Alanlar SimpleProperty olarak tanımlanır
    private final StringProperty dersid;
    private final StringProperty tc;
    private final IntegerProperty sinif;
    private final IntegerProperty sinav1;
    private final IntegerProperty sinav2;

    // Kurucu (Constructor)
    public NotGorunum(String dersid, String tc, int sinif, int sinav1, int sinav2) {
        // 2. Kurucuda, temel Java değerleri Property nesnelerine sarılır (wrap)
        this.dersid = new SimpleStringProperty(dersid);
        this.tc = new SimpleStringProperty(tc);
        this.sinif = new SimpleIntegerProperty(sinif);
        this.sinav1 = new SimpleIntegerProperty(sinav1);
        this.sinav2 = new SimpleIntegerProperty(sinav2);
    }

    // --- 3. Property Metotları (Binding için ZORUNLU) ---

    // JavaFX TableView, bu metotları (alan adı + "Property") çağırarak
    // Property nesnesinin kendisini alır ve binding'i kurar.

    public StringProperty dersidProperty() {
        return dersid;
    }

    public StringProperty tcProperty() {
        return tc;
    }

    public IntegerProperty sinifProperty() {
        return sinif;
    }

    public IntegerProperty sinav1Property() {
        return sinav1;
    }

    public IntegerProperty sinav2Property() {
        return sinav2;
    }

    public String getTc() {return tc.get();}

    public int getSinif() {return sinif.get();}

    public String getDersid() {
        return dersid.get();
    }

    public int getSinav1() {
        return sinav1.get();
    }

    public int getSinav2() {return sinav2.get();}

}