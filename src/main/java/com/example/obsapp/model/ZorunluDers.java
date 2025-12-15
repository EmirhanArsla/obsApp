package com.example.obsapp.model;

import com.example.obsapp.Interfaceler.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;
import org.bson.Document;

public class ZorunluDers extends Ders implements INotHesaplayabilir {

    boolean zorunluMu=true;
    // ==================== CONSTRUCTORS ====================

    public ZorunluDers(String id, String dersAdi, int sinifSeviyesi,
                       double katsayi,boolean zorunluMu) {

        super(id, dersAdi, sinifSeviyesi, katsayi);
        this.zorunluMu=zorunluMu;
    }


    // ==================== ZORUNLU OVERRIDE ====================

    public Document toDocument() {
        return new Document("dersAdi", getDersAdi())
                .append("sinif", getSinifSeviyesi())
                .append("tur", "Zorunlu");
    }
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
    public double ortalama(int y1, int y2) {
        return HesaplamaUtil.ortalama(y1, y2);
    }

    @Override
    public boolean gectiMi(int y1, int y2) {
        return HesaplamaUtil.ortalama(y1, y2)>=50;
    }

    @Override
    public double agirlikliNot(double ortalama , int Katsayi) {
        return HesaplamaUtil.agirlikliNot(ortalama,getKatsayi());
    }

}
