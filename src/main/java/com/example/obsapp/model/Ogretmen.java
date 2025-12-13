package com.example.obsapp.model;

import java.time.LocalDate;

public class Ogretmen extends Kisi {

    private String brans;
    private DersBase verdigiDers;   // Öğretmen sadece 1 ders verebilir

    // ====================== CONSTRUCTOR OVERLOADING ======================

    // 1) Branşlı öğretmen
    public Ogretmen(String ad, String soyad, String brans) {
        super(ad, soyad);
        setBrans(brans);
    }

    // 2) ID ile
    public Ogretmen(String id, String ad, String soyad, String brans) {
        super(id, ad, soyad);
        setBrans(brans);
    }

    // 3) Kayıt tarihli öğretmen
    public Ogretmen(String id, String ad, String soyad, String brans, LocalDate kayitTarihi) {
        super(id, ad, soyad, kayitTarihi);
        setBrans(brans);

    }

    // ====================== GETTER - SETTER ======================

    public String getBrans() { return brans; }

    public void setBrans(String brans) {
        if (brans == null || brans.length() < 2)
            throw new IllegalArgumentException("Branş en az 2 karakter olmalıdır!");
        this.brans = brans.trim();
    }

    public DersBase getVerdigiDers() { return verdigiDers; }

    // Her öğretmen sadece 1 ders verebilir
    public void setVerdigiDers(DersBase ders) {
        if (this.verdigiDers != null)
            throw new IllegalStateException("Bu öğretmene zaten bir ders atanmış!");

        this.verdigiDers = ders;
    }

    // ====================== OVERRIDE METOT ======================

    @Override
    public void bilgileriYazdir() {
        System.out.println("===== ÖĞRETMEN BİLGİLERİ =====");
        System.out.println("Ad Soyad      : " + ad + " " + soyad);
        System.out.println("Branş         : " + brans);
        System.out.println("Kayıt Tarihi  : " + getKayitTarihiFormatted());

        if (verdigiDers == null)
            System.out.println("Verdiği Ders  : Henüz ders atanmamış.");
        else
            System.out.println("Verdiği Ders  : " + verdigiDers.getDersAdi());

        System.out.println("==============================\n");
    }
}
