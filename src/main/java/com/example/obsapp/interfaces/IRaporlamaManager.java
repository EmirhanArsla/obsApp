package com.example.obsapp.interfaces;

import com.example.obsapp.viewmodel.GnoGorunum;
import com.example.obsapp.viewmodel.NotGorunum;
import com.example.obsapp.viewmodel.OrtalamaGorunum;
import java.util.List;
public interface IRaporlamaManager {

    List<NotGorunum> notGoruntule(String tc);

    // Ders ortalamaları
    List<OrtalamaGorunum> ortlamaGoster(String tc);

    // GNO işlemleri
    double gnoHesapla(String tc);
    List<GnoGorunum> gnoGetir(String tc);
    List<GnoGorunum> tumGnolar();

}
