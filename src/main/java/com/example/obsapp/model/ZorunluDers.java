package com.example.obsapp.model;

import com.example.obsapp.Interfaceler.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;

public class ZorunluDers extends Ders implements INotHesaplayabilir {

    // ==================== CONSTRUCTORS ====================

    public ZorunluDers(String id, String dersAdi, int sinifSeviyesi,
                       int kredi, double katsayi, boolean aktif,
                       String zorunlulukNedeni) {

        super(id, dersAdi, sinifSeviyesi, katsayi);
    }

    public ZorunluDers(String dersAdi, int sinifSeviyesi) {
        super(dersAdi, sinifSeviyesi);
    }


    // ==================== ZORUNLU OVERRIDE ====================

    @Override
    public void dersBilgisiYazdir() {
        System.out.println("=== ZORUNLU DERS ===");
        System.out.println("Ders Adı: " + formatliDersAdi());
        System.out.println("Sınıf Seviyesi: " + getSinifSeviyesi());
        System.out.println("Katsayı: " + getKatsayi());
        System.out.println("=====================\n");
    }

    // ==================== INotHesaplayabilir METOTLARI ====================

    @Override
    public double ortalama(int y1, int y2, int performans) {
        return HesaplamaUtil.ortalama(y1, y2, performans);
    }

    @Override
    public boolean gectiMi(int y1, int y2, int performans) {
        return HesaplamaUtil.ortalama(y1, y2, performans)>=50;
    }

    @Override
    public double agirlikliNot(int y1, int y2, int performans, int Katsayi) {
        return HesaplamaUtil.agirlikliNot(y1,y2,performans,getKatsayi());
    }

}
