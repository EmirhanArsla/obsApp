package com.example.obsapp.Manager;

import com.example.obsapp.DBO.DersDao;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.DBO.NotDao;
import com.example.obsapp.DBO.OgrenciDao;
import com.example.obsapp.Viewmodel.NotGorunum;
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
     public List<NotGorunum> notGoruntule(String ogrencid){
         List<NotGorunum> notGorunum=new ArrayList<>();

         List<Document> notlist= notDao.notSearch(ogrencid);

         if(notlist.isEmpty() || notlist== null ){
             System.out.println("Not bulunamadi");
             return notGorunum ;// eğer dosya boşsa if Döngüsünden çıkması için
         }
         String ogrenciNo =  notlist.get(0).getString("ogrenciNo");

         Document student = ogrenciDao.ogrencisearch(ogrenciNo);

         if(student == null) {
             System.out.println(" öğrenci bulunamadi");
             return notGorunum;
         }
             for(Document not : notlist) {
                String dersId =  not.getString("dersId");
                String OgrenciId =  not.getString("OgrenciId");
                 int Sinif= not.getInteger("Sinif");
                 int Sinav1=not.getInteger("Sinav1");
                 int Sinav2=not.getInteger("Sinav2");

                 if(Sinif==0  || Sinav1==0 && Sinav2==0 ){
                     System.out.println("Ders kaydı eksik veri içeriyor (Ders : dersId");
                 continue;
                 }

                 NotGorunum detay = new NotGorunum(
                         dersId,OgrenciId,Sinif,Sinav1,Sinav2
                 );
                 notGorunum.add(detay);
             }
         return  notGorunum;
     }

     public  void genelOrtalama(String ogrenciId){
         Document student = (Document) ogrenciDao.ogrencisearch(ogrenciId);
         if(student == null){
             System.out.println("örenci bulunamadi");
            return;
         }

        List<Document> notlist= notDao.notSearch(ogrenciId);
         if(notlist.isEmpty() || notlist== null ){
             System.out.println("Not bulunamadi");
            return;
         }

     double toplamAgirlik=0.0;
     double toplamKredi=0.0;
     double gno = 0.0;

     for(Document not : notlist){
         String dersId =  not.getString("dersId");
         int sinav1 = not.getInteger("sinav1",0 );
         int sinav2 = not.getInteger("sinav2",0);
         int kredi = not.getInteger("kredi",0);

         toplamKredi= toplamKredi + kredi;

         double dersOrtalamasi = HesaplamaUtil.ortalama(sinav1, sinav2);

         double agirlikliNot = HesaplamaUtil.agirlikliNot(dersOrtalamasi, kredi);

         toplamAgirlik= toplamAgirlik + agirlikliNot;


         //buraya tablo şeklinde yazdıracakmış gibi yaz

     }
    if (toplamKredi > 0 ){
        gno =  toplamAgirlik/toplamKredi;
        System.out.println("GNO hesaplandı ");
    }
    else {
        System.out.println("GNO hesaplanmadı");
    }
     }




     }



