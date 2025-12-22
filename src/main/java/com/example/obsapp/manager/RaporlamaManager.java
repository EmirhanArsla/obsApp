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

import java.util.*;
import java.util.stream.Collectors;
//Raporlamamanger genel olarak arayüzde kullandığımız kısımların kod ve fonksiyonlarının bulunduğu yerdir
public class RaporlamaManager implements IRaporlamaManager {


    private final OgrenciDao ogrenciDao;
    private final NotDao notDao;
    private final DersDao dersDao;
//Yapıcı sınıf
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
    //if kontolü ile notlist kontol edilir eğer boş döndürülse uyarı verir.
        if ( notlist == null || notlist.isEmpty()  ) {
            System.out.println("Not bulunamadi");
            return notGorunum;// eğer dosya boşsa if Döngüsünden çıkması için
        }
    // notlist listesinde tc ile eşleşen değeri aktarır
        String Tc = notlist.getFirst().getString("tc");
    //öğrenciDaodaki öğrenci arama fonksiyonu ile aldığımız tc paretmesini atayrak öğrenciyi buluruz
        Document student = ogrenciDao.ogrencisearch(Tc);
        //student kontrol edilir
        if (student == null) {
            System.out.println(" öğrenci bulunamadi");
            return notGorunum;
        }
        //notlist içinde dönerek bize aradığımız değerleri veriri
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
        //burda notgorunum ile tabloya okunabilir bi şekilde getirmeye çalışıyoruz
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
        //Ogrencidaodaki öğrenci ara fonksiyonu ile bize öğrenciyi getirir
        Document student = ogrenciDao.ogrencisearch(tc);
        //notdaodaki not ara fonksiyonu ile notlar getirilir
        List<Document> tumNotKaydi = notDao.notSearch(tc);
    //student kontrol edilir
    if (student == null || tumNotKaydi.isEmpty()) {
            System.out.println("Not bulunamadi veya öğrenci bulunamadi");
            return ortlama;
        }
    //tumnotkaydi ile tüm notlar arasında gezer ve aranan değerler bulunur
        for (Document not : tumNotKaydi) {
            String dersId = not.getString("dersid");

            double sinav1_double = ((Number )not.get("sinav1")).doubleValue() ;
            double sinav2_double = ((Number )not.get("sinav2")).doubleValue() ;
    //dersdao dersarama kısmı ile dersler getirilir
            List<Document> derslistesi = dersDao.dersSearch(dersId);
            Document dersBilgisi = derslistesi.get(0);

            //dersbilgisi kontrol edilir
            if (dersBilgisi != null ) {
                String dersAdi = dersBilgisi.getString("dersAdi");

            //dersOrtlaması Hesapla utildeki ortlama fonksiyonu ile hesaplanır
                double dersOrtalama = HesaplamaUtil.ortalama(sinav1_double, sinav2_double);

                int sinav1 =(int) sinav1_double;
                int sinav2 =(int) sinav2_double;
                //burda Ortalamagörünüm ile tabloya okunabilir bi şekilde getirmeye çalışıyoruz
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
    //student kontrol edilir
            if (student == null) {
                System.out.println("Öğrenci bulunamadı");
                return 0.0;
            }
    //notlar getirilir
            List<Document> notlist = notDao.notSearch(tc);
            if (notlist == null || notlist.isEmpty()) {
                System.out.println("Not bulunamadı");
                return 0.0;
            }
    //notlist içinde dönerek bize aradığımız değerli getirir
            for (Document not : notlist) {
                String dersId = not.getString("dersid");
                double sinav1_double = ((Number) not.get("sinav1")).doubleValue();
                double sinav2_double = ((Number) not.get("sinav2")).doubleValue();
    //dersdaodaki dersarama ile dersleri bize getirir
                List<Document> derslistesi = dersDao.dersSearch(dersId);

                Document dersBilgisi = null;
                if (derslistesi != null && !derslistesi.isEmpty()) {
                    dersBilgisi = derslistesi.get(0);
                }

                if (dersBilgisi != null && dersBilgisi.containsKey("katsayi")) {
                    String dersAdi = dersBilgisi.getString("dersAdi");


                    double katsayi_double = ((Number) dersBilgisi.get("katsayi")).doubleValue();
    //bize ortlama ve ağırlıklı not hesaplamautildeki fonksiyonlarla hesaplarız
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
            //burda gno hesaplanır ve gönderilir
            return Math.round((toplamagirlikliNot / toplamKredi) * 100.0) / 100.0;
        }


    public double sinifOrtalama(int sinif) {
        double toplamSinifOrtalama = 0.0;
        int ogernciSayisi=0;
        //öğrenciler sınıflara göre getirlir
        List<Document> ogrenciBilgi = ogrenciDao.ogrencisearch(sinif);
        if (ogrenciBilgi == null) {
            System.out.println("sınıf bulunamadi");
            return 0.0;
        }
        //öğrencibilgi içindeki aranan değer çekilir
        Iterator<Document> iterator = ogrenciBilgi.iterator();

        while (iterator.hasNext()) { // "Sırada başka öğrenci olduğu sürece devam et"
            Document ogrenci = iterator.next(); // Sıradaki öğrenciyi al

            String Tc = ogrenci.getString("tc");
            double ogrenciGno = gnoHesapla(Tc);

            if (ogrenciGno >= 0.0) {
                toplamSinifOrtalama += ogrenciGno; // Daha kısa yazım: +=
                ogernciSayisi++;
            }
        }
        if (ogernciSayisi == 0.0) {
            return 0.0;
        }//tüm sınıfının toplam gnosu getirlird
        return toplamSinifOrtalama/ogernciSayisi;
    }
    @Override
    public List<GnoGorunum> tumGnolar(){
        List<GnoGorunum> gnoRapor = new ArrayList<>(); //Gno'ların çekileceği liste
        //burda tüm öğrenciler,notlar,dersler çekilir ve listelere aktarılır
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
            double gno =gnoHesapla(ogrenciNotlari,dersMap);

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
    //öğrenciler getirilir
        Document ogrenciBilgi = ogrenciDao.ogrencisearch(tc);
    //öğrenci bilgi kontol edilir
        if (ogrenciBilgi == null) {
            System.out.println("Ogrenci bulunamadi");
            return gnoList;
        }
        String ad = ogrenciBilgi.getString("ad");
        String soyad = ogrenciBilgi.getString("soyAd");
        //ad ve soyad konrtrol edilri
        if (ad == null || soyad == null ) {
            System.out.println("Öğrenci bilgileri eksik");
            return gnoList;
        }
        //daha önceden oluşturduğumuz gnoheapla fonksiyonu kullanılarak gno hesaplanır
            double gno =gnoHesapla(tc);
            if (gno <0){
                System.out.println("Gno Hesaplanmadı");
                return gnoList;
            }
//burda Gnogorunum ile tabloya okunabilir bi şekilde getirmeye çalışıyoruz
            GnoGorunum detay = new GnoGorunum(
                    tc,ad,soyad,gno
            );
            gnoList.add(detay);

        return gnoList;
    }

//burda yukarıdaki gno hesapla overload ediliyor burdaki fonksiyonlarda birden fazla öğrenci getirildiği için daha hızlı olması için
    //kullanılan fonksşyondur
        private double gnoHesapla(List<Document> notlar, Map<String, Document> dersMap) {
        double toplamAgirlikliNot = 0.0;
        double toplamKredi = 0.0;
    //notlar içindeki aradığımız değerler getirilir
        for (Document not : notlar) {
            String dersId = not.getString("dersid");
            double sinav1 = ((Number) not.get("sinav1")).doubleValue();
            double sinav2 = ((Number) not.get("sinav2")).doubleValue();

            // Map'ten ders bilgisini al (daha hızlı!)
            Document ders = dersMap.get(dersId);
        //katsayı değişkeni aranır ve hesaplautildeki ortalama ağırlıklınot hesaplanır
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