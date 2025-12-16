package com.example.obsapp.DBO;

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
