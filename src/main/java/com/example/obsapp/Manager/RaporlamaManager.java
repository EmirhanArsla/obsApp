package com.example.obsapp.Manager;

import com.example.obsapp.DBO.DersDao;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.DBO.NotDao;
import com.example.obsapp.Viewmodel.NotGorunum;
import com.example.obsapp.Viewmodel.OrtalamaGorunum;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.util.HesaplamaUtil;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class RaporlamaManager {
    private final OgrenciDao ogrenciDao = new OgrenciDao();
    private final NotDao notDao = new NotDao();
    private final DersDao dersDao = new DersDao();

    //Notları çekebilmek ve Tabloda görüntülemek için kullanabilceğimiz fonksiyon
    public List<NotGorunum> notGoruntule(String ogrencid) {
        List<NotGorunum> notGorunum = new ArrayList<>();
        List<Document> notlist = notDao.notSearch(ogrencid);

        if (notlist.isEmpty() || notlist == null) {
            System.out.println("Not bulunamadi");
            return notGorunum;// eğer dosya boşsa if Döngüsünden çıkması için
        }
        String ogrenciNo = notlist.get(0).getString("ogrenciNo");

        Document student = ogrenciDao.ogrencisearch(ogrenciNo);

        if (student == null) {
            System.out.println(" öğrenci bulunamadi");
            return notGorunum;
        }
        for (Document not : notlist) {
            String dersId = not.getString("dersId");
            String OgrenciId = not.getString("OgrenciId");
            int Sinif = not.getInteger("Sinif");
            int Sinav1 = not.getInteger("Sinav1");
            int Sinav2 = not.getInteger("Sinav2");

            if (Sinif == 0 || Sinav1 == 0 && Sinav2 == 0) {
                System.out.println("Ders kaydı eksik veri içeriyor (Ders : dersId");
                continue;
            }

            NotGorunum detay = new NotGorunum(
                    dersId, OgrenciId, Sinif, Sinav1, Sinav2
            );
            notGorunum.add(detay);
        }
        return notGorunum;
    }

    public List<OrtalamaGorunum> ortlamaGoster(String ogrencid) {
        List<OrtalamaGorunum> ortlama = new ArrayList<>();

        Document student = ogrenciDao.ogrencisearch(ogrencid);

        List<Document> tumNotKaydi = notDao.notSearch(ogrencid);

        if (student == null || tumNotKaydi.isEmpty()) {
            System.out.println("Not bulunamadi veya öğrenci bulunamadi");
            return ortlama;
        }

        for (Document not : tumNotKaydi) {
            String dersId = not.getString("dersId");
            int sinav1 = not.getInteger("Sinav1", 0);
            int sinav2 = not.getInteger("Sinav2", 0);

            List<Document> derslistesi = dersDao.dersSearch(dersId);
            Document dersBilgisi = derslistesi.get(0);


            if (dersBilgisi != null && dersBilgisi.containsKey("katsayi")) {
                String dersAdi = dersBilgisi.getString("dersAdi");
                int kredi = dersBilgisi.getInteger("katsayi");

                double dersOrtalama = HesaplamaUtil.ortalama(sinav1, sinav2);

                OrtalamaGorunum detay = new OrtalamaGorunum(
                        dersId, dersAdi, dersOrtalama, kredi
                );

                ortlama.add(detay);
            }
        }
        return ortlama;
    }

    public double gnoHesapla(String ogrenciNo) {
        Document student = ogrenciDao.ogrencisearch(ogrenciNo);
        double toplamagirlikliNot = 0.0;
        double toplamKredi = 0.0;
        if (student == null) {
            System.out.println("öğrenci bulunamadi");
            return 0.0;
        }
        List<Document> notlist = notDao.notSearch(ogrenciNo);
        if (notlist.isEmpty()) {
            System.out.println("Not bulunamadi");
            return 0.0;
        }

        for (Document not : notlist) {
            String dersId = not.getString("dersId");
            int sinav1 = not.getInteger("Sinav1", 0);
            int sinav2 = not.getInteger("Sinav2", 0);

            List<Document> derslistesi = dersDao.dersSearch(dersId);

            Document dersBilgisi = null;
            if (derslistesi != null) {
                dersBilgisi = derslistesi.get(0);
            }

            if (dersBilgisi != null && dersBilgisi.containsKey("katsayi")) {
                String dersAdi = dersBilgisi.getString("dersAdi");
                int kredi = dersBilgisi.getInteger("katsayi");

                double dersOrtalama = HesaplamaUtil.ortalama(sinav1, sinav2);
                double agirlikliNot = HesaplamaUtil.agirlikliNot(dersOrtalama, kredi);

                toplamagirlikliNot = toplamagirlikliNot + agirlikliNot;
                toplamKredi = toplamKredi + kredi;

            }
        }

        if (toplamKredi == 0.0) {
            return 0.0;
        }
        return toplamagirlikliNot / toplamKredi;

    }

    public double sinifOrtalama(int sinif) {
        double toplamSinifOrtalama = 0.0;
        int ogernciSayisi=0;
        List<Document> ogrenciBilgi = ogrenciDao.ogrencisearch2(sinif);
        if (ogrenciBilgi == null) {
            System.out.println("sınıf bulunamadi");
            return 0.0;
        }
        for (Document ogrenci : ogrenciBilgi) {
            String OgrenciNo = ogrenci.getString("OgrenciNo");

            double ogrenciGno= gnoHesapla(OgrenciNo);

            if (ogrenciGno >= 0.0) {
                toplamSinifOrtalama = toplamSinifOrtalama + ogrenciGno;
                ogernciSayisi++;
            }
        }
        if (ogernciSayisi == 0.0) {
            return 0.0;
        }
        return toplamSinifOrtalama/ogernciSayisi;
    }
}