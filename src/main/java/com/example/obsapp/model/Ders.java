package com.example.obsapp.model;

import com.example.obsapp.Interfaceler.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;
import org.bson.Document;

public class Ders extends DersBase implements INotHesaplayabilir {

    // -------------Yapıcı Metot-------------

    public Ders(String id, String dersAdi, int sinifSeviyesi, double katsayi) {
        super(id, dersAdi, sinifSeviyesi, katsayi);
    }


    // -------IHesaplayabilir interface'inin Override metotları--------

    @Override
    public double agirlikliNot(double ortalama ,int Katsayi) {
        return HesaplamaUtil.agirlikliNot( ortalama, getKatsayi());
    }

    @Override
    public double ortalama(int yazili1, int yazili2) {
        return HesaplamaUtil.ortalama(yazili1, yazili2);
    }


    @Override
    public boolean gectiMi(int y1, int y2) {
        return HesaplamaUtil.ortalama(y1, y2) >= 50;
    }
}
