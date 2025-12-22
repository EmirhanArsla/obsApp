package com.example.obsapp.dao;

import com.example.obsapp.model.Not;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

//NotDao sınıfı, öğrencilere ait not bilgilerinin
//MongoDB veritabanı üzerinde yönetilmesini sağlar.
public class NotDao {
    // MongoDB üzerinde "Notlar" koleksiyonunu temsil eder
    private final MongoCollection<Document> collection;
    //---------------Yapıcı Metot-----------------
    public NotDao(MongoCollection<Document> database) {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        if (database == null) {
            throw new IllegalArgumentException("Not koleksiyonu null olamaz.");
        }
        this.collection = database;

    }
     // Öğrenciye ait yeni bir not kaydını veritabanına ekler.
    public void notadd(Not not) {
        //yazdığımız alanlara göre veritabanına ekler
        Document document = new Document();
        document.append("notid", not.getNotId());
        document.append("dersid", not.getDersId());
        document.append("tc", not.getTc());
        document.append("sinav1", not.getSinav1());
        document.append("sinav2", not.getSinav2());
        document.append("sinif", not.getSinif());
        document.append("dersAdi", not.getDersad());

        try {
            collection.insertOne(document);
            System.out.println("Not başarıyla eklendi");
        } catch (Exception e) {
            System.out.println("Not eklenemedi " + e.getMessage());
        }
    }
   //Not ID bilgisine göre ilgili not kaydını veritabanından siler.
    public void notdelete(int notid) {
        Document filitre = new Document("notid", notid);
        long SId = collection.deleteOne(filitre).getDeletedCount();
        if (SId > 0) {
            System.out.println("Not Silindi");
        } else {
            System.out.println("Silinmedi");
        }
    }
  //Not ID bilgisine göre ilgili not kaydını veritabanından siler.
    public List<Document> notSearch(String tc) {
        Document filitre = new Document("tc", tc);
        List<Document> notlist = new ArrayList<>();
        return collection.find(filitre).into(notlist);
    }
  //Veritabanında kayıtlı olan tüm notları listeler.
    public List<Document> allNot() {
        List<Document> documents = new ArrayList<>();
        return collection.find().into(documents);

    }
  //Girilen not ID bilgisinin veritabanında mevcut olup olmadığını kontrol eder.
    public boolean notidKontrol(String notid) {
        Document filtre = new Document("notid", notid);
        if (collection.find(filtre).first() != null) {
            return true;
        } else {
            return false;
        }
    }

    public static <T extends Comparable<T>> boolean araliktaMi(T deger, T min, T max) {
        if (deger == null || min == null || max == null) {
            return false;
        }
        return deger.compareTo(min) >= 0 && deger.compareTo(max) <= 0;
    }
}

