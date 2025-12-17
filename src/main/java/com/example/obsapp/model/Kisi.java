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

    // ====================== PROTECTED ALANLAR ======================
    protected String tc;
    protected String ad;
    protected String soyAd;
    protected LocalDate kayitTarihi;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");



    // ====================== CONSTRUCTOR OVERLOADING ======================
    public Kisi(String id, String ad, String soyad, LocalDate kayitTarihi) {
        this.tc = id;
        setAd(ad);
        setSoyAd(soyad);
        this.kayitTarihi = kayitTarihi;
    }

    // ====================== ABSTRACT METOT ======================

    /**
     * Her alt sınıf (Ogrenci, Ogretmen) kendi bilgilerini ekrana
     * farklı şekilde yazmak zorunda olacak.
     */

    // ====================== GETTER - SETTER ======================

    public String getTc() { return tc; }

    public void setTc(String tc) { this.tc = tc; }

    public String getAd() { return ad; }

    public void setAd(String ad) {
        if (ad == null || ad.length() < 2)
            throw new IllegalArgumentException("İsim en az 2 karakter olmalıdır!");
        this.ad = ad;
    }

    public String getSoyAd() { return soyAd; }

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
