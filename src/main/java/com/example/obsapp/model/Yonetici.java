package com.example.obsapp.model;

import java.time.LocalDate;

public class Yonetici extends Kisi {
    private String sifre;

    // Öğretmen sadece 1 ders verebilir

    //-------------------Yapıcı Metot------------------


    public Yonetici(String id, String ad, String soyad,String sifre,LocalDate kayitTarihi) {
        super(id, ad, soyad, kayitTarihi);
        this.sifre=sifre;

    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
