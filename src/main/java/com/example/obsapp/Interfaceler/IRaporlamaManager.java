package com.example.obsapp.Interfaceler;

import com.example.obsapp.Viewmodel.GnoGorunum;
import com.example.obsapp.Viewmodel.NotGorunum;
import com.example.obsapp.Viewmodel.OrtalamaGorunum;
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
