package com.example.obsapp.DBO;

import com.example.obsapp.model.Ogretmen;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class OgretmenDao {
    private MongoCollection<Document> collection;

    public OgretmenDao() {
        MongoDatabase database = DBUtil.getInstance().getDatabase();
        collection = database.getCollection("Ogretmen");
    }

    public void ogretmenAdd(Ogretmen ogretmen) {
        Document document = new Document();
        document.append("ad",ogretmen.getAd());
        document.append("soyAd",ogretmen.getSoyad());
        document.append("tc",ogretmen.getId());
        document.append("brans",ogretmen.getBrans());
        try {
            collection.insertOne(document);
            System.out.println("Ogretmen Eklendi");
        }
        catch (Exception e) {
            System.out.println("Ogretmen Eklenemedi " + e.getMessage());
        }
    }

}
