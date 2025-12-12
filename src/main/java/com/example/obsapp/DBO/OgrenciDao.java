package com.example.obsapp.DBO;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OgrenciDao {
    private final MongoCollection<Document> collection;


    public OgrenciDao() {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        this.collection = Db.getCollection("Ogrenciler");

    }

    public String ogrenciAdd(Ogrenci ogrenci) {
        Document filtre= new Document("$or", Arrays.asList(
                new Document("tc",ogrenci.getId()),
                new Document("OgrenciNo",ogrenci.getOgrenciNo())
        ));

        Document hata = collection.find(filtre).first();
        if(hata != null){
            String mesaj= "Aynı Tc sahip ögrenci var " ;
            return mesaj;
        }

        Document document = new Document();
        document.append("ad", ogrenci.getAd());
        document.append("soyAd", ogrenci.getSoyad());
        document.append("tc", ogrenci.getId());
        document.append("ogrenciNo", ogrenci.getOgrenciNo());
        document.append("sinifSeviyesi", ogrenci.getSinifSeviyesi());

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


    public List<Document> ogrencisearch2(int sinifSeviyesi){
        Document filitre = new Document("sinifSeviyesi", sinifSeviyesi);
        List<Document> documents = new  ArrayList<>();
        return collection.find(filitre).into(documents) ;
    }

    public List<Document> allOgrenci(){
        ArrayList<Document> documents = new ArrayList<>();
        collection.find().into(documents) ;
        return documents;
    }

    public Document girisKontrol(String ogrenciNo , String tc){
        Document filtre = new Document("ogrenciNo", ogrenciNo)
                .append("tc", tc);
        return collection.find(filtre).first() ;
    }
}
