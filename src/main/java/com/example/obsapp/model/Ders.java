package com.example.obsapp.model;

import com.example.obsapp.Interfaceler.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;
import org.bson.Document;

/**
 * Lise Öğrenci Bilgi Sistemi
 * Ortak Ders sınıfı
 *
 * - DersBase'den miras alır
 * - Not hesaplama işlemlerini HesaplamaUtil'e yönlendirir
 * - ZorunluDers ve SecmeliDers sınıfları bunun üzerinden türeyecek
 */
public class Ders extends DersBase implements INotHesaplayabilir {

    // ======================== CONSTRUCTORS ========================

    public Ders(String id, String dersAdi, int sinifSeviyesi, double katsayi) {
        super(id, dersAdi, sinifSeviyesi, katsayi);
    }


    // ======================== OVERRIDES ========================

    @Override
    public double agirlikliNot(double ortalama ,int Katsayi) {
        return HesaplamaUtil.agirlikliNot( ortalama, getKatsayi());
    }

    // ======================== NOT İŞLEMLERİ ========================

    /**
     * Üç notun ortalamasını hesaplar.
     */
    @Override
    public double ortalama(int yazili1, int yazili2) {
        return HesaplamaUtil.ortalama(yazili1, yazili2);
    }

    /**
     * Öğrencinin geçip geçmediğini kontrol eder.
     */
    @Override
    public boolean gectiMi(int y1, int y2) {
        return HesaplamaUtil.ortalama(y1, y2) >= 50;
    }
}
