package com.example.obsapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ogrenci extends Kisi {

    private int sinifSeviyesi;
    private String ogrenciNo;
    private int[] notlar = new int[2];   // [0] = yazılı1, [1] = yazılı2

    // --------------Yapıcı Metot-------------

    public Ogrenci(String tc, String ad, String soyad, int sinifSeviyesi, String ogrenciNo , LocalDate kayitTarihi) {
        super(tc, ad, soyad,kayitTarihi);
        setSinifSeviyesi(sinifSeviyesi);
        this.ogrenciNo = ogrenciNo;
    }

    // -------------------- GETTER - SETTER --------------------

    public int getSinifSeviyesi() { return sinifSeviyesi; }

    public void setSinifSeviyesi(int sinifSeviyesi) {
        if (sinifSeviyesi < 9 || sinifSeviyesi > 12)
            throw new IllegalArgumentException("Lise sınıf seviyesi 9-12 arasında olmalıdır!");
        this.sinifSeviyesi = sinifSeviyesi;
    }

    public String getOgrenciNo() { return ogrenciNo ; }

    public void setOkulNo(int okulNo) { this.ogrenciNo = ogrenciNo; }

    public int[] getNotlar() { return notlar; }
    //throw yapısıyla IllegalArgumentException hatsı fırlatıyoruz
    public void setNotlar(int yazili1, int yazili2) {

        if (yazili1 < 0 || yazili1 > 100 || yazili2 < 0 || yazili2 > 100)
            throw new IllegalArgumentException("Notlar 0-100 arasında olmalıdır!");

        notlar[0] = yazili1;
        notlar[1] = yazili2;
    }

    }

