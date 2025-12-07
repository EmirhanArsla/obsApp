package com.example.obsapp.model;

import com.example.obsapp.Interfaceler.IKayitOlabilir;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Kisi (Abstract Class)
 *
 * Ogrenci ve Ogretmen sınıflarının ortak üst sınıfıdır.
 * PDF'teki abstract class, inheritance, interface, overloading ve LocalDate
 * gereksinimlerini doğrudan karşılar.
 */
public abstract class Kisi implements IKayitOlabilir {

    // ====================== PROTECTED ALANLAR ======================
    protected String id;
    protected String ad;
    protected String soyad;
    protected LocalDate kayitTarihi;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");



    // ====================== CONSTRUCTOR OVERLOADING ======================
    public Kisi(String ad, String soyad) {
        setAd(ad);
        setSoyad(soyad);
        this.kayitTarihi = LocalDate.now();
    }
    public Kisi(String id, String ad, String soyad, LocalDate kayitTarihi) {
        this.id = id;
        setAd(ad);
        setSoyad(soyad);
        this.kayitTarihi = kayitTarihi;
    }
    public Kisi(String id, String ad, String soyad) {
        this.id = id;
        setAd(ad);
        setSoyad(soyad);
        this.kayitTarihi = LocalDate.now();
    }
    // ====================== ABSTRACT METOT ======================

    /**
     * Her alt sınıf (Ogrenci, Ogretmen) kendi bilgilerini ekrana
     * farklı şekilde yazmak zorunda olacak.
     */
    public abstract void bilgileriYazdir();

    // ====================== GETTER - SETTER ======================

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAd() { return ad; }

    public void setAd(String ad) {
        if (ad == null || ad.length() < 2)
            throw new IllegalArgumentException("İsim en az 2 karakter olmalıdır!");
        this.ad = ad;
    }

    public String getSoyad() { return soyad; }

    public void setSoyad(String soyad) {
        if (soyad == null || soyad.length() < 2)
            throw new IllegalArgumentException("Soyad en az 2 karakter olmalıdır!");
        this.soyad = soyad;
    }

    public LocalDate getKayitTarihi() { return kayitTarihi; }

    public String getKayitTarihiFormatted() {
        return kayitTarihi.format(FORMATTER);
    }


    // ====================== INTERFACE IMPLEMENTATION ======================

    @Override
    public void sistemeKayit() {
        System.out.println(ad + " " + soyad + " sisteme kaydedildi. (Tarih: " + kayitTarihi + ")");
    }
}
