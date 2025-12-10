package com.example.obsapp.DBO;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class OgrenciDao {
    private final MongoCollection<Document> collection;


    public OgrenciDao() {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        this.collection = Db.getCollection("Ogrenciler");

    }

    public String ogrenciAdd(String ad, String SoyAd , String tc , String OgrenciNo , int sinifSeviyesi) {
        Document filtre= new Document("$or", Arrays.asList(
                new Document("tc",tc),
                new Document("OgrenciNo",OgrenciNo)
        ));

        Document hata = collection.find(filtre).first();
        if(hata != null){
            String mesaj= "Aynı Tc sahip ögrenci var " ;
            return mesaj;
        }

        Document document = new Document();
        document.append("ad", ad);
        document.append("soyAd", SoyAd);
        document.append("tc", tc);
        document.append("OgrenciNo", OgrenciNo);
        document.append("sinifSeviyesi", sinifSeviyesi);

        try {
            collection.insertOne(document);
           return "Öğrenci Başarıyla Eklendi";       }
        catch (Exception e) {
            return "Öğrenci Eklenemedi" + e.getMessage() ;
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
