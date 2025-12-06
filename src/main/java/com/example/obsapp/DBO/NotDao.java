package com.example.obsapp.DBO;

import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class NotDao {
    private MongoCollection<Document> collection;

    public NotDao() {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        this.collection = Db.getCollection("Notlar");

    }

    public void notadd(int notid , int dersid , String ogrencid , int sinav1 ,int sinav2,int sinif){
        Document document = new Document();
        document.append("notid",notid);
        document.append("dersid",dersid);
        document.append("ogrencid",ogrencid);
        document.append("sinav1",sinav1);
        document.append("sinav2",sinav2);
        document.append("sinif",sinif);

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
}

