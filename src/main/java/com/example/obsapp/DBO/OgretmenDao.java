package com.example.obsapp.DBO;

import com.example.obsapp.model.Ogretmen;
import com.example.obsapp.util.DBUtil;
import com.mongodb.MongoIncompatibleDriverException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.ZoneId;
import java.util.Date;

public class OgretmenDao {
    private MongoCollection<Document> collection;

    public OgretmenDao(MongoCollection<Document> database ) {
        MongoDatabase database1 = DBUtil.getInstance().getDatabase();
        collection = database1.getCollection("Ogretmen");
    }

    public void ogretmenAdd(Ogretmen ogretmen) {
        Date dbDate =Date.from(ogretmen.getKayitTarihi().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Document document = new Document();
        document.append("ad",ogretmen.getAd());
        document.append("soyAd",ogretmen.getSoyad());
        document.append("tc",ogretmen.getId());
        document.append("brans",ogretmen.getBrans());
        document.append("dersid",ogretmen.getVerdigiDers());
        document.append("kayitTarihi",dbDate);
        try {
            collection.insertOne(document);
            System.out.println("Ogretmen Eklendi");
        }
        catch (Exception e) {
            System.out.println("Ogretmen Eklenemedi " + e.getMessage());
        }
    }

}
