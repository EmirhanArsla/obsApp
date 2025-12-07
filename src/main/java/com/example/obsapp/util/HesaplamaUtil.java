package com.example.obsapp.util;

public class  HesaplamaUtil {

    public static boolean notGecerliMi(int not) {
        return not >= 0 && not <= 100;   // karşılaştırma + mantıksal
    }

    public static boolean notlarGecerliMi(int y1, int y2) {
        return notGecerliMi(y1) && notGecerliMi(y2) ;
    }

    public static double ortalama(int y1, int y2) {

        if (!notlarGecerliMi(y1, y2) ){
            throw new IllegalArgumentException("Notlar 0–100 arasında olmalıdır!");
        }

        return (y1 + y2 ) / 3.0; // int -> double dönüşümü (implicit)
    }

    // ======================== AĞIRLIKLI NOT ========================

    /**
     * Katsayı ve kredi kullanılarak ağırlıklı ortalama hesaplar.
     * Formül: ağırlıklı = ortalama * katsayı + kredi
     */
    public static double agirlikliNot(int y1, int y2, double katsayi) {

        double ort = ortalama(y1, y2); // util içi çağrı

        if (katsayi <= 0 || katsayi > 5)
            throw new IllegalArgumentException("Katsayı 1–5 arası olmalıdır!");

        return ort * katsayi;   // aritmetik operatörler
    }
    // ======================== TİP DÖNÜŞÜMLERİ ========================

    /**
     * Double ortalamayı int değere çevirir.
     * → Explicit casting
     */
    public static int ortalamaInt(double ort) {
        return (int) ort;   // explicit cast
    }


    // ======================== NOTU SINIRLANDIRMA ========================

    /**
     * Bir notun 0–100 aralığında kalmasını sağlar.
     * Örn: 120 girilirse → 100 yapılır.
     */
    public static int normalize(int not) {
        if (not < 0) return 0;
        if (not > 100) return 100;
        return not;
    }
}

