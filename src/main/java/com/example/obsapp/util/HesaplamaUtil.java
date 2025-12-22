package com.example.obsapp.util;

public class  HesaplamaUtil {

    public static boolean notGecerliMi(double not) {
        return not >= 0 && not <= 100;
    }

    public static boolean notlarGecerliMi(double y1, double y2) {
        return notGecerliMi(y1) && notGecerliMi(y2) ;
    }

    public static double ortalama(double y1, double y2) {

        if (!notlarGecerliMi(y1, y2) ){
            throw new IllegalArgumentException("Notlar 0–100 arasında olmalıdır!");
        }

        return (double) (y1 + y2 ) / 2.0 ;
    }

    // ======================== AĞIRLIKLI NOT ========================

    /*
      Katsayı ve kredi kullanılarak ağırlıklı ortalama hesaplar.
      Formül: ağırlıklı = ortalama * katsayı + kredi
     */
    public static double agirlikliNot(double ortalama, double katsayi) {
        if (katsayi <= 0 || katsayi > 5)
            throw new IllegalArgumentException("Katsayı 1–5 arası olmalıdır!");

        return ortalama * katsayi;
    }
    // ======================== TİP DÖNÜŞÜMLERİ ========================

    /*
      Double ortalamayı int değere çevirir.
      Explicit casting
     */
    public static int ortalamaInt(double ort) {
        return (int) ort;   // explicit cast
    }



}

