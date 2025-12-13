package com.example.obsapp.DBO;

import com.example.obsapp.model.Not;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class NotDao {
    private MongoCollection<Document> collection;

    public NotDao(MongoCollection<Document> database) {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        this.collection = Db.getCollection("Notlar");

    }

    public void notadd(Not not){
        Document document = new Document();
        document.append("notid",not.getNotId() );
        document.append("dersid",not.getDersId());
        document.append("ogrencid",not.getOgrenciId());
        document.append("sinav1",not.getSinav1());
        document.append("sinav2",not.getSinav2());
        document.append("sinif",not.getSinif());
        document.append("dersad",not.getDersad());

        try {
            collection.insertOne(document);
            System.out.println("Not başarıyla eklendi");
        }
        catch (Exception e){
            System.out.println("Not eklenemedi " +e.getMessage());
        }
    }

    public void notdelete(int notid) {
        Document filitre = new Document("notid",notid);
        long SId = collection.deleteOne(filitre).getDeletedCount();
        if (SId > 0) {
            System.out.println("Not Silindi");
        }
        else  {
            System.out.println("Silinmedi");
        }
    }

    public List<Document> notSearch (String ogrencid) {
        Document filitre = new Document("ogrencid",ogrencid);
        List<Document> notlist= new ArrayList<>();
        return collection.find(filitre).into(notlist);
    }

    public List<Document> allNot(String dersid) {
        Document filitre = new Document("dersid",dersid);
        List<Document> notlist= new ArrayList<>();
        return collection.find(filitre).into(notlist);
    }
}

