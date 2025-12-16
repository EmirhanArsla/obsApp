package com.example.obsapp.Manager;

import com.example.obsapp.DBO.DersDao;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.DBO.NotDao;
import com.example.obsapp.Viewmodel.GnoGorunum;
import com.example.obsapp.Viewmodel.NotGorunum;
import com.example.obsapp.Viewmodel.OrtalamaGorunum;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.util.DBUtil;
import com.example.obsapp.util.HesaplamaUtil;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class RaporlamaManager {
//    MongoDatabase database = DBUtil.getInstance().getDatabase();
//    private final OgrenciDao ogrenciDao = new OgrenciDao(database.getCollection("Ogrenciler"));
//    private final NotDao notDao = new NotDao(database.getCollection("Notlar"));
//    private final DersDao dersDao = new DersDao(database.getCollection("Dersler"));

    private final OgrenciDao ogrenciDao;
    private final NotDao notDao;
    private final DersDao dersDao;

    public RaporlamaManager(NotDao notDao, DersDao dersDao, OgrenciDao ogrenciDao) {
        this.notDao = notDao;
        this.dersDao = dersDao;
        this.ogrenciDao = ogrenciDao;
    }


    //Notları çekebilmek ve Tabloda görüntülemek için kullanabilceğimiz fonksiyon
    public List<NotGorunum> notGoruntule(String tc) {
        List<NotGorunum> notGorunum = new ArrayList<>();
        List<Document> notlist = notDao.notSearch(tc);

        if ( notlist == null || notlist.isEmpty()  ) {
            System.out.println("Not bulunamadi");
            return notGorunum;// eğer dosya boşsa if Döngüsünden çıkması için
        }
        String Tc = notlist.getFirst().getString("tc");

        Document student = ogrenciDao.ogrencisearch(Tc);

        if (student == null) {
            System.out.println(" öğrenci bulunamadi");
            return notGorunum;
        }
        for (Document not : notlist) {
            String dersId = not.getString("dersid");
            String OgrenciId = not.getString("tc");
            int Sinif = not.getInteger("sinif");
            int Sinav1 = not.getInteger("sinav1");
            int Sinav2 = not.getInteger("sinav2");

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

    public List<OrtalamaGorunum> ortlamaGoster(String tc) {
        List<OrtalamaGorunum> ortlama = new ArrayList<>();

        Document student = ogrenciDao.ogrencisearch(tc);

        List<Document> tumNotKaydi = notDao.notSearch(tc);

        if (student == null || tumNotKaydi.isEmpty()) {
            System.out.println("Not bulunamadi veya öğrenci bulunamadi");
            return ortlama;
        }

        for (Document not : tumNotKaydi) {
            String dersId = not.getString("dersid");

            double sinav1_double = ((Number )not.get("sinav1")).doubleValue() ;
            double sinav2_double = ((Number )not.get("sinav2")).doubleValue() ;

            List<Document> derslistesi = dersDao.dersSearch(dersId);
            Document dersBilgisi = derslistesi.getFirst();


            if (dersBilgisi != null ) {
                String dersAdi = dersBilgisi.getString("dersAdi");


                double dersOrtalama = HesaplamaUtil.ortalama(sinav1_double, sinav2_double);

                int sinav1 =(int) sinav1_double;
                int sinav2 =(int) sinav2_double;

                OrtalamaGorunum detay = new OrtalamaGorunum(
                         sinav1,dersAdi,dersOrtalama,sinav2
                );

                ortlama.add(detay);
            }
        }
        return ortlama;
    }

    public double gnoHesapla(String tc) {
        Document student = ogrenciDao.ogrencisearch(tc);
        double toplamagirlikliNot = 0.0;
        double toplamKredi = 0.0;
        if (student == null) {
            System.out.println("öğrenci bulunamadi");
            return 0.0;
        }
        List<Document> notlist = notDao.notSearch(tc);
        if (notlist.isEmpty()) {
            System.out.println("Not bulunamadi");
            return 0.0;
        }

        for (Document not : notlist) {
            String dersId = not.getString("dersid");
            int sinav1 = not.getInteger("sinav1", 0);
            int sinav2 = not.getInteger("sinav2", 0);

            List<Document> derslistesi = dersDao.dersSearch(dersId);

            Document dersBilgisi = null;
            if (derslistesi != null) {
                dersBilgisi = derslistesi.getFirst();
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
            String Tc = ogrenci.getString("tc");

            double ogrenciGno= gnoHesapla(Tc);

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

    public List<GnoGorunum> tumGnolar(){
        List<GnoGorunum> gnoRapor = new ArrayList<>(); //Gno'ların çekileceği liste
        List<Document> tumOgrenciler = ogrenciDao.allOgrenci();

        if (tumOgrenciler == null) {
            System.out.println("Ogrenci bulunamadi");
            return gnoRapor;
        }

        for (Document ogrenci : tumOgrenciler) {
            String OgrenciNo = ogrenci.getString("ogrenciNo");
            String ad = ogrenci.getString("ad");
            String soyad = ogrenci.getString("soyAd");

            double gno =gnoHesapla(OgrenciNo);

            GnoGorunum detay = new GnoGorunum(
                    OgrenciNo,ad,soyad,gno
            );
            gnoRapor.add(detay);
        }
        return gnoRapor;
    }


    public double dersOrtalama(String dersid) {
         double toplamDersOrtlama = 0.0;
         double ogrenciSayisi=0;
        List<Document> notlar = notDao.allNot(dersid);
        if (notlar == null) {
            return 0.0;
        }
        for (Document notlar1 : notlar) {
            String Dersid = notlar1.getString("dersAdi");
            int sinav1 = notlar1.getInteger("sinav1",0);
            int sinav2 = notlar1.getInteger("sinav2",0);

            double ortlama=HesaplamaUtil.ortalama(sinav1,sinav2);
            toplamDersOrtlama+=ortlama;
            ogrenciSayisi++;

        }
        if (ogrenciSayisi == 0.0) {
            return 0.0;
        }
        return toplamDersOrtlama/ogrenciSayisi;
    }




}