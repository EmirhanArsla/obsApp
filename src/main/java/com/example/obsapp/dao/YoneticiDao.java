package com.example.obsapp.dao;

import com.example.obsapp.model.Yonetici;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.ZoneId;
import java.util.Date;
// YoneticiDao sınıfı, yönetici/öğretmen bilgilerinin
// MongoDB veritabanı üzerinde yönetilmesini sağlar.

public class YoneticiDao {
    // MongoDB üzerinde "Yönetici" koleksiyonunu temsil eder
    private MongoCollection<Document> collection;
    //------------Yapıcı metot-------
    public YoneticiDao(MongoCollection<Document> database ) {
        MongoDatabase database1 = DBUtil.getInstance().getDatabase();
        collection = database1.getCollection("Yönetici");
    }
    //Yeni bir yönetici kaydını veritabanına ekler.
    public void yoneticiAdd(Yonetici yonetici) {

        Date dbDate =Date.from(yonetici.getKayitTarihi().atStartOfDay(ZoneId.systemDefault()).toInstant());
//Girdiğimiz alanlara göre veri tabanına ekler
        Document document = new Document();
        document.append("ad", yonetici.getAd());
        document.append("soyAd", yonetici.getSoyAd());
        document.append("tc", yonetici.getTc());
        document.append("sifre",yonetici.getSifre());
        document.append("kayitTarihi",dbDate);
        try {
            collection.insertOne(document);
            System.out.println("Yönetici Eklendi");
        }
        catch (Exception e) {
            System.out.println("Yönetici Eklenemedi " + e.getMessage());
        }
    }
    //TC ve şifre bilgilerine göre yönetici giriş kontrolü yapar.
    public Document yoneticiKontrol(String tc, String sifre){
        Document filtre = new Document("tc", tc)
                .append("sifre", sifre);
        return collection.find(filtre).first() ;
    }
    //Girilen TC bilgisinin veritabanında mevcut olup olmadığını kontrol eder.
    public boolean tcKontrol(String tc){
        Document filtre = new Document("tc", tc);
        if(collection.find(filtre).first() != null){
            return true;
        }
        else {
            return false;
        }
    }

}
