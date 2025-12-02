package com.example.obsapp.model;

import java.util.Objects;

public abstract class DersBase {
    private String id;
    // ===================== ALANLAR =====================
             // MongoDB ID (String olarak tutulabilir)
    private String dersAdi;     // Türkçe, Matematik, Fizik...
    private int sinifSeviyesi;  // 9,10,11,12
    private int kredi;          // 1–5 arası
    private double katsayi;     // 0–5 arası pozitif değer
    private boolean aktif;      // Ders aktif mi?
    public DersBase(String id, String dersAdi, int sinifSeviyesi, int kredi, double katsayi, boolean aktif) {
        setId(id);
        setDersAdi(dersAdi);
        setSinifSeviyesi(sinifSeviyesi);
        setKredi(kredi);
        setKatsayi(katsayi);
        setAktif(aktif);
    }

    public DersBase(String dersAdi, int sinifSeviyesi) {
        this(null, dersAdi, sinifSeviyesi, 1, 1.0, true);
    }


    public abstract void dersBilgisiYazdir();

    public String formatliDersAdi() {
        return dersAdi
                .trim()
                .toUpperCase()
                .replace("İ", "I");
    }
    public int katsayiInt() {
        return (int) katsayi;  // Explicit cast
    }
    public String tamBilgi() {
        return formatliDersAdi() + " (" + sinifSeviyesi + ". Sınıf)";
    }
    public String getId() { return id; }

    public void setId(String id) {
        if (id != null && id.trim().isEmpty())
            throw new IllegalArgumentException("ID boş olamaz!");
        this.id = id;
    }

    public String getDersAdi() { return dersAdi; }

    public void setDersAdi(String dersAdi) {
        if (dersAdi == null || dersAdi.trim().length() < 3)
            throw new IllegalArgumentException("Ders adı en az 3 karakter olmalıdır!");

        if (dersAdi.substring(0,1).equalsIgnoreCase("x"))
            throw new IllegalArgumentException("Ders adı X ile başlayamaz!");

        this.dersAdi = dersAdi;
    }
    public int getSinifSeviyesi() { return sinifSeviyesi; }

    public void setSinifSeviyesi(int sinifSeviyesi) {
        if (sinifSeviyesi < 9 || sinifSeviyesi > 12)
            throw new IllegalArgumentException("Sınıf seviyesi 9–12 arasında olmalıdır!");
        this.sinifSeviyesi = sinifSeviyesi;
    }
    public int getKredi() { return kredi; }

    public void setKredi(int kredi) {
        if (kredi < 1 || kredi > 5)
            throw new IllegalArgumentException("Kredi 1–5 arasında olmalıdır!");
        this.kredi = kredi;
    }

    public double getKatsayi() { return katsayi; }

    public void setKatsayi(double katsayi) {
        if (katsayi <= 0 || katsayi > 5)
            throw new IllegalArgumentException("Katsayı 1.0–5.0 aralığında olmalıdır!");
        this.katsayi = katsayi;
    }

    public boolean isAktif() { return aktif; }

    public void setAktif(boolean aktif) { this.aktif = aktif; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DersBase)) return false;
        DersBase d = (DersBase) o;
        return Objects.equals(id, d.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


