package com.example.obsapp.dao;

import com.example.obsapp.model.Ders;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
//DersDao sınıfı, derslere ait veritabanı işlemlerini
//(ekleme, arama, listeleme, kontrol) gerçekleştirmek için kullanılır.

public class DersDao {
    // MongoDB üzerinde "Dersler" koleksiyonunu temsil eder
    private final MongoCollection<Document> collection;

    //-----------Yapıcı Metot---------------
    public DersDao(MongoCollection<Document> database) {
        MongoDatabase database0 = DBUtil.getInstance().getDatabase();
        if (database == null) {
            throw new IllegalArgumentException("Not koleksiyonu null olamaz.");
        }

        // Constructor'a gelen koleksiyon nesnesini, sınıfın değişkenine atayın.
        this.collection =database;
    }
    //Yeni bir dersi veritabanına ekler.
    public boolean dersAdd(Ders ders){
        if (dersidKontrol(ders.getDersid())) {
            return false; // Zaten var → ekleme
        }
        Document document = new Document();
        document.append("dersAdi",ders.getDersAdi() );
        document.append("dersid",ders.getDersid());
        document.append("sinifSeviyesi",ders.getSinifSeviyesi());
        document.append("katsayi",ders.getKatsayi());

        try {
            collection.insertOne(document);
            System.out.println("Not başarıyla eklendi");
        }
        catch (Exception e){
            System.out.println("Not eklenemedi " +e.getMessage());
        }
        return true;
    }

    public List<Document> dersSearch (String dersid ){
            Document filtere = new Document("dersid",dersid);
            List<Document> documents = new  ArrayList<>();

            return collection.find(filtere).into(documents);

    }

    // Girilen ders ID'sinin veritabanında mevcut olup olmadığını kontrol eder.
    public boolean dersidKontrol (String dersid){
        Document filtre = new Document ("dersid",dersid);
        if(collection.find(filtre).first()!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public List <Document> allDers(){
        List<Document> documents = new  ArrayList<>();
        return collection.find().into(documents);

    }
}
