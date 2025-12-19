package com.example.obsapp.DBO;

import com.example.obsapp.model.Yonetici;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.ZoneId;
import java.util.Date;

public class YoneticiDao {
    private MongoCollection<Document> collection;

    public YoneticiDao(MongoCollection<Document> database ) {
        MongoDatabase database1 = DBUtil.getInstance().getDatabase();
        collection = database1.getCollection("Ogretmen");
    }

    public void yoneticiAdd(Yonetici yonetici) {
        Date dbDate =Date.from(yonetici.getKayitTarihi().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Document document = new Document();
        document.append("ad", yonetici.getAd());
        document.append("soyAd", yonetici.getSoyAd());
        document.append("tc", yonetici.getTc());
        document.append("sifre",yonetici.getSifre());
        document.append("kayitTarihi",dbDate);
        try {
            collection.insertOne(document);
            System.out.println("Ogretmen Eklendi");
        }
        catch (Exception e) {
            System.out.println("Ogretmen Eklenemedi " + e.getMessage());
        }
    }
    public Document yoneticiKontrol(String tc, String sifre){
        Document filtre = new Document("tc", tc)
                .append("sifre", sifre);
        return collection.find(filtre).first() ;
    }

}
