package com.example.obsapp.model;

import com.example.obsapp.interfaces.INotHesaplayabilir;
import com.example.obsapp.util.HesaplamaUtil;

public class ZorunluDers extends Ders implements INotHesaplayabilir {

    boolean zorunluMu=true;
    // -----------------Yapıcı Metot------------------

    public ZorunluDers(String id, String dersAdi, int sinifSeviyesi,
                       double katsayi,boolean zorunluMu) {

        super(id, dersAdi, sinifSeviyesi, katsayi);
        this.zorunluMu=zorunluMu;
    }
    // --------------- INotHesaplayabilir interface'inin Override metotları--------------

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
