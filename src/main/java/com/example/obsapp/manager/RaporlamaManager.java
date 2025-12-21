package com.example.obsapp.manager;

import com.example.obsapp.dao.DersDao;
import com.example.obsapp.dao.OgrenciDao;
import com.example.obsapp.dao.NotDao;
import com.example.obsapp.interfaces.IRaporlamaManager;
import com.example.obsapp.viewmodel.GnoGorunum;
import com.example.obsapp.viewmodel.NotGorunum;
import com.example.obsapp.viewmodel.OrtalamaGorunum;
import com.example.obsapp.util.HesaplamaUtil;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RaporlamaManager implements IRaporlamaManager {


    private final OgrenciDao ogrenciDao;
    private final NotDao notDao;
    private final DersDao dersDao;

    public RaporlamaManager(NotDao notDao, DersDao dersDao, OgrenciDao ogrenciDao) {
        this.notDao = notDao;
        this.dersDao = dersDao;
        this.ogrenciDao = ogrenciDao;
    }


    //Notları çekebilmek ve Tabloda görüntülemek için kullanabilceğimiz fonksiyon
    @Override
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
@Override
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
            Document dersBilgisi = derslistesi.get(0);


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
@Override
    public double gnoHesapla(String tc) {
            Document student = ogrenciDao.ogrencisearch(tc);
            double toplamagirlikliNot = 0.0;
            double toplamKredi = 0.0;

            if (student == null) {
                System.out.println("Öğrenci bulunamadı");
                return 0.0;
            }

            List<Document> notlist = notDao.notSearch(tc);
            if (notlist == null || notlist.isEmpty()) {
                System.out.println("Not bulunamadı");
                return 0.0;
            }

            for (Document not : notlist) {
                String dersId = not.getString("dersid");
                double sinav1_double = ((Number) not.get("sinav1")).doubleValue();
                double sinav2_double = ((Number) not.get("sinav2")).doubleValue();

                List<Document> derslistesi = dersDao.dersSearch(dersId);

                Document dersBilgisi = null;
                if (derslistesi != null && !derslistesi.isEmpty()) {
                    dersBilgisi = derslistesi.get(0);
                }

                if (dersBilgisi != null && dersBilgisi.containsKey("katsayi")) {
                    String dersAdi = dersBilgisi.getString("dersAdi");

                    // ✅ DÜZELTME: getInteger() kaldırıldı
                    double katsayi_double = ((Number) dersBilgisi.get("katsayi")).doubleValue();

                    double dersOrtalama = HesaplamaUtil.ortalama(sinav1_double, sinav2_double);
                    double agirlikliNot = HesaplamaUtil.agirlikliNot(dersOrtalama, katsayi_double);

                    toplamagirlikliNot = toplamagirlikliNot + agirlikliNot;
                    toplamKredi = toplamKredi + katsayi_double;
                }
            }

            if (toplamKredi == 0.0) {
                System.out.println("Toplam kredi 0");
                return 0.0;
            }

            return Math.round((toplamagirlikliNot / toplamKredi) * 100.0) / 100.0;
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
    @Override
    public List<GnoGorunum> tumGnolar(){
        List<GnoGorunum> gnoRapor = new ArrayList<>(); //Gno'ların çekileceği liste
        List<Document> tumOgrenciler = ogrenciDao.allOgrenci();
        List<Document> tumNotlar = notDao.allNot();
        List<Document> tumDersler = dersDao.allDers();

        if (tumOgrenciler == null || tumOgrenciler.isEmpty()) {
            System.out.println("Ogrenci bulunamadi");
            return gnoRapor;
        }
        //Dersler Map'e çevrilir (diğer yönteme göre daha hızlı getirmek için)
        Map<String, Document> dersMap = new HashMap<>();
        for (Document ders : tumDersler) {
            dersMap.put(ders.getString("dersid"), ders);
        }
        //Notlar öğrenci tc'sine göre gruplanır
        Map<String, List<Document>> notlarByTc = tumNotlar.stream()
                .collect(Collectors.groupingBy(doc -> doc.getString("tc")));

        for (Document ogrenci : tumOgrenciler) {
            String Tc = ogrenci.getString("tc");
            String ad = ogrenci.getString("ad");
            String soyad = ogrenci.getString("soyAd");

            //Belirlenen öğrencinin tc si iel notlar listeye aktarılır
            List<Document> ogrenciNotlari = notlarByTc.getOrDefault(Tc, new ArrayList<>());

            /* Bu yöntemde listeden çekilen verile gnohesaplada her öğrenci için ayrı ayrı sorugular ile database
            kısmına yollanıyorudu sonra getiriliyordu buda hali hazrıda işlemin uzun sürmesini sebeb oluyordu
            aynı zamanda kullanılan hashmap ile list içinde tekrar tekrar dönemkten kurtuluyoruz
            */
//          double gno =gnoHesapla(Tc);
            double gno =gnoHesaplaHizli(ogrenciNotlari,dersMap);

            GnoGorunum detay = new GnoGorunum(
                    Tc,ad,soyad,gno
            );
            gnoRapor.add(detay);
        }
        return gnoRapor;
    }
@Override
    public List<GnoGorunum> gnoGetir(String tc) {
        List<GnoGorunum> gnoList = new ArrayList<>(); //Gno'ların çekileceği liste

        Document ogrenciBilgi = ogrenciDao.ogrencisearch(tc);

        if (ogrenciBilgi == null) {
            System.out.println("Ogrenci bulunamadi");
            return gnoList;
        }
        String ad = ogrenciBilgi.getString("ad");
        String soyad = ogrenciBilgi.getString("soyAd");

        if (ad == null || soyad == null ) {
            System.out.println("Öğrenci bilgileri eksik");
            return gnoList;
        }

            double gno =gnoHesapla(tc);
            if (gno <0){
                System.out.println("Gno Hesaplanmadı");
                return gnoList;
            }

            GnoGorunum detay = new GnoGorunum(
                    tc,ad,soyad,gno
            );
            gnoList.add(detay);

        return gnoList;
    }


//    public double dersOrtalama(String dersid) {
//         double toplamDersOrtlama = 0.0;
//         double ogrenciSayisi=0;
//        List<Document> notlar = notDao.allNot(dersid);
//        if (notlar == null) {
//            return 0.0;
//        }
//        for (Document notlar1 : notlar) {
//            String Dersid = notlar1.getString("dersAdi");
//            int sinav1 = notlar1.getInteger("sinav1",0);
//            int sinav2 = notlar1.getInteger("sinav2",0);
//
//            double ortlama=HesaplamaUtil.ortalama(sinav1,sinav2);
//            toplamDersOrtlama+=ortlama;
//            ogrenciSayisi++;
//
//        }
//        if (ogrenciSayisi == 0.0) {
//            return 0.0;
//        }
//        return toplamDersOrtlama/ogrenciSayisi;
//    }

        private double gnoHesaplaHizli(List<Document> notlar, Map<String, Document> dersMap) {
        double toplamAgirlikliNot = 0.0;
        double toplamKredi = 0.0;

        for (Document not : notlar) {
            String dersId = not.getString("dersid");
            double sinav1 = ((Number) not.get("sinav1")).doubleValue();
            double sinav2 = ((Number) not.get("sinav2")).doubleValue();

            // Map'ten ders bilgisini al (çok hızlı!)
            Document ders = dersMap.get(dersId);

            if (ders != null && ders.containsKey("katsayi")) {
                double katsayi = ((Number) ders.get("katsayi")).doubleValue();
                double dersOrtalama = HesaplamaUtil.ortalama(sinav1, sinav2);
                double agirlikliNot = HesaplamaUtil.agirlikliNot(dersOrtalama, katsayi);

                toplamAgirlikliNot += agirlikliNot;
                toplamKredi += katsayi;
            }
        }

        if (toplamKredi == 0.0) {
            return 0.0;
        }

        return (toplamAgirlikliNot / toplamKredi) ;
    }



}