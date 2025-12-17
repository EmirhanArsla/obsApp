package com.example.obsapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Kisi (Abstract Class)
 *
 * Ogrenci ve Ogretmen sınıflarının ortak üst sınıfıdır.
 * PDF'teki abstract class, inheritance, interface, overloading ve LocalDate
 * gereksinimlerini doğrudan karşılar.
 */
public abstract class Kisi {

    // ---------------Alanlar------------
    private String tc;
    private String ad;
    private String soyAd;
    private LocalDate kayitTarihi;
    //DateTimeFormater Sınıfyla kayitTarihine format belirliyoruz.
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");



    // --------------Yapıcı Metot--------------------
    public Kisi(String id, String ad, String soyad, LocalDate kayitTarihi) {
        this.tc = id;
        setAd(ad);
        setSoyAd(soyad);
        this.kayitTarihi = kayitTarihi;
    }

    // ------------------Getter Setter-----------------

    public String getTc() { return tc; }

    public void setTc(String tc) { this.tc = tc; }

    public String getAd() { return ad; }

    //Isim en az iki karekterli olmalıdır IllegalArgumentException fırlatıyoruz.
    public void setAd(String ad) {
        if (ad == null || ad.length() < 2)
            throw new IllegalArgumentException("İsim en az 2 karakter olmalıdır!");
        this.ad = ad;
    }

    public String getSoyAd() { return soyAd; }

    //Isim en az iki karekterli olmalıdır IllegalArgumentException fırlatıyoruz.
    public void setSoyAd(String soyAd) {
        if (soyAd == null || soyAd.length() < 2)
            throw new IllegalArgumentException("Soyad en az 2 karakter olmalıdır!");
        this.soyAd = soyAd;
    }

    public LocalDate getKayitTarihi() { return kayitTarihi; }

    public String getKayitTarihiFormatted() {
        return kayitTarihi.format(FORMATTER);
    }


}
