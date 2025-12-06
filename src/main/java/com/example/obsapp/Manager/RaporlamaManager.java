package com.example.obsapp.Manager;

import com.example.obsapp.DBO.DAO;
import com.example.obsapp.DBO.NotDao;
import com.example.obsapp.Viewmodel.NotGorunum;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RaporlamaManager {
     private final DAO ogrenciDao = new DAO();
     private final NotDao notDao = new NotDao();

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




     }



