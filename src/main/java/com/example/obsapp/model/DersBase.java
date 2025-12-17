package com.example.obsapp.model;

import java.util.Objects;

public abstract class DersBase {

    // -------------Alanlar---------------

    private String dersid;
    private String dersAdi;     // Türkçe, Matematik, Fizik...
    private int sinifSeviyesi;  // 9,10,11,12
    private double katsayi;// 0–5 arası pozitif değer
    //-----------Yapıcı Metot----------------
    public DersBase(String id, String dersAdi, int sinifSeviyesi, double katsayi) {
        setDersid(id);
        setDersAdi(dersAdi);
        setSinifSeviyesi(sinifSeviyesi);
        setKatsayi(katsayi);

    }
    //----------Getter-Setterlar---------------

    public String getDersid() { return dersid; }

    public void setDersid(String dersid) {
        if (dersid != null && dersid.trim().isEmpty())
            throw new IllegalArgumentException("ID boş olamaz!");
        this.dersid = dersid;
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


    public double getKatsayi() { return katsayi; }

    public void setKatsayi(double katsayi) {
        if (katsayi <= 0 || katsayi > 5)
            throw new IllegalArgumentException("Katsayı 1.0–5.0 aralığında olmalıdır!");
        this.katsayi = katsayi;
    }

    //-------------equals ve hashCode---------------
    //Bu yapı sayesinde koleksiyonlarda tekrar eden nesneler eklenmesi engellenmiş olur.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DersBase)) return false;
        DersBase d = (DersBase) o;
        return Objects.equals(dersid, d.dersid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dersid);
    }
}


