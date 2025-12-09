package com.example.obsapp.model;

import com.example.obsapp.Interfaceler.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;

public class SecmeliDers extends Ders implements INotHesaplayabilir {
    boolean zorunluMu=false;
    // ==================== CONSTRUCTORS ====================


    public SecmeliDers(String id, String dersAdi, int sinifSeviyesi,
                       double katsayi,boolean zorunluMu) {

        super(id, dersAdi, sinifSeviyesi, katsayi);
    }



    // ==================== OVERRIDES ====================

    @Override
    public void dersBilgisiYazdir() {
        System.out.println("=== SEÇMELİ DERS ===");
        System.out.println("Ders Adı: " + formatliDersAdi());
        System.out.println("Sınıf Seviyesi: " + getSinifSeviyesi());
        System.out.println("Katsayı: " + getKatsayi());
        System.out.println("======================\n");
    }

    // ==================== INotHesaplayabilir ====================

    @Override
    public double ortalama(int y1, int y2) {
        return HesaplamaUtil.ortalama(y1, y2);
    }

    @Override
    public boolean gectiMi(int y1, int y2) {
        return HesaplamaUtil.ortalama(y1, y2)>=40;
    }

    @Override
    public double agirlikliNot(double ortalama ,int Katsayi) {
        return HesaplamaUtil.agirlikliNot(  ortalama,getKatsayi());

    }
}
