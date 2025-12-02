package com.example.obsapp.model;

import com.example.obsapp.Interfaceler.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;

public class SecmeliDers extends Ders implements INotHesaplayabilir {

    private final String alanAdi;   // örn: "Sanat", "Spor", "Bilişim", "Müzik"

    // ==================== CONSTRUCTORS ====================

    public SecmeliDers(String id, String dersAdi, int sinifSeviyesi,
                       int kredi, double katsayi, boolean aktif,
                       String alanAdi) {

        super(id, dersAdi, sinifSeviyesi, kredi, katsayi, aktif);
        this.alanAdi = alanAdi;
    }

    public SecmeliDers(String dersAdi, int sinifSeviyesi) {
        super(dersAdi, sinifSeviyesi);
        this.alanAdi = "Genel Seçmeli Ders";
    }

    // ==================== GETTER ====================

    public String getAlanAdi() {
        return alanAdi;
    }

    // ==================== OVERRIDES ====================

    @Override
    public void dersBilgisiYazdir() {
        System.out.println("=== SEÇMELİ DERS ===");
        System.out.println("Ders Adı: " + formatliDersAdi());
        System.out.println("Sınıf Seviyesi: " + getSinifSeviyesi());
        System.out.println("Kredi: " + getKredi());
        System.out.println("Katsayı: " + getKatsayi());
        System.out.println("Alan / Branş: " + alanAdi);
        System.out.println("Aktif mi: " + (isAktif() ? "Evet" : "Hayır"));
        System.out.println("======================\n");
    }

    // ==================== INotHesaplayabilir ====================

    @Override
    public double ortalama(int y1, int y2, int performans) {
        return HesaplamaUtil.ortalama(y1, y2, performans);
    }

    @Override
    public boolean gectiMi(int y1, int y2, int performans) {
        return HesaplamaUtil.gectiMi(y1, y2, performans);
    }

    @Override
    public double agirlikliNot(int y1, int y2, int performans) {
        return HesaplamaUtil.agirlikliNot(
                y1, y2, performans,
                getKatsayi(),
                getKredi()
        );
    }
}
