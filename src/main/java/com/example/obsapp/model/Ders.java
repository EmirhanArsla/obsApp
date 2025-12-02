package com.example.obsapp.model;
import com.example.obsapp.Interfaceler.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;


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

    public Ders(String id, String dersAdi, int sinifSeviyesi, int kredi, double katsayi, boolean aktif) {
        super(id, dersAdi, sinifSeviyesi, kredi, katsayi, aktif);
    }

    public Ders(String dersAdi, int sinifSeviyesi) {
        super(dersAdi, sinifSeviyesi);
    }

    // ======================== OVERRIDES ========================

    @Override
    public void dersBilgisiYazdir() {
        System.out.println("=== DERS BİLGİLERİ ===");
        System.out.println("Ders: " + formatliDersAdi());
        System.out.println("Sınıf Seviyesi: " + getSinifSeviyesi());
        System.out.println("Kredi: " + getKredi());
        System.out.println("Katsayı: " + getKatsayi());
        System.out.println("Aktif mi: " + (isAktif() ? "Evet" : "Hayır"));
        System.out.println("=======================\n");
    }

    // ======================== NOT İŞLEMLERİ ========================

    /**
     * Üç notun ortalamasını hesaplar.
     */
    public double ortalama(int yazili1, int yazili2, int performans) {
        return HesaplamaUtil.ortalama(yazili1, yazili2, performans);
    }

    /**
     * Öğrencinin geçip geçmediğini kontrol eder.
     */
    public boolean gectiMi(int y1, int y2, int perf) {
        return HesaplamaUtil.gectiMi(y1, y2, perf);
    }

    /**
     * Ağırlıklı not hesaplama (kredi × katsayı × ortalama)
     */
    public double agirlikliNot(int y1, int y2, int perf) {
        return HesaplamaUtil.agirlikliNot(
                y1, y2, perf,
                getKatsayi(),
                getKredi()
        );
    }
}
