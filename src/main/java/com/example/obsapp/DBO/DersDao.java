package com.example.obsapp.DBO;

import com.example.obsapp.model.Ders;
import com.example.obsapp.model.DersBase;
import com.example.obsapp.model.Not;
import com.example.obsapp.model.ZorunluDers;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class DersDao {
    private final MongoCollection<Document> collection;


    public DersDao(MongoCollection<Document> database) {
        MongoDatabase database0 = DBUtil.getInstance().getDatabase();
        if (database == null) {
            throw new IllegalArgumentException("Not koleksiyonu null olamaz.");
        }

        // Constructor'a gelen koleksiyon nesnesini, sınıfın değişkenine atayın.
        this.collection =database;
    }
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


    public boolean dersidKontrol (String dersid){
        Document filtre = new Document ("dersid",dersid);
        if(collection.find(filtre).first()!=null){
            return true;
        }
        else {
            return false;
        }
    }
}
