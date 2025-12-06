package com.example.obsapp.DBO;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.example.obsapp.controller.SecondController;

import java.util.Collection;

public class DAO {
    private MongoCollection<Document> collection;


    public DAO() {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        this.collection = Db.getCollection("Ogrenciler");

    }

    public void ogrenciAdd(String ad, String SoyAd , String tc , String OgrenciNo){
        Document document = new Document();
        document.append("ad", ad);
        document.append("soyAd", SoyAd);
        document.append("tc", tc);
        document.append("OgrenciNo", OgrenciNo);
        try {
            collection.insertOne(document);
            System.out.println("Öğrenci Başarıyla Eklendi ");         }
        catch (Exception e) {
            System.out.println("Öğrenci Eklenemedi" + e.getMessage());
        }
    }

    public void ogrenciDelete(String tc){
        Document filitre = new Document("tc", tc);
        long Stc= collection.deleteOne(filitre).getDeletedCount(); // sürücü longu zorunlu tutar
        if(Stc>0){
            System.out.println("Öğrenci Başarıyla Silindi");
        }
        else{
            System.out.println("Öğrenci Bulunamadı");
        }
    }

    public Document ogrencisearch(String tc){
        Document filitre = new Document("tc", tc);
        return collection.find(filitre).first() ;
    }

}
