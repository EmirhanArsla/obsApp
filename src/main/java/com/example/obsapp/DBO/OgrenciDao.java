package com.example.obsapp.DBO;
import com.example.obsapp.Exception.OgrenciBulunamadiException;
import com.example.obsapp.Viewmodel.OgrenciGorunum;
import com.example.obsapp.model.Ogrenci;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class OgrenciDao {
    private final MongoCollection<Document> collection;


    public OgrenciDao(MongoCollection<Document> database) {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        if (database == null) {
            throw new IllegalArgumentException("Not koleksiyonu null olamaz.");
        }

        // Constructor'a gelen koleksiyon nesnesini, sınıfın değişkenine atayın.
        this.collection = database;

    }

    //tabloya yansıtmak için mapper dönüşümü yapılır
    private OgrenciGorunum mapDocumentToOgrenciGorunum(Document document) {
        if (document == null) {
            return null;
        }
        String ad = document.getString("ad");
        String soyad = document.getString("soyAd");
        String tc= document.getString("tc");
        String ogrenciNo = document.getString("ogrenciNo");
        Integer sinifSeviyesi  = document.getInteger("sinifSeviyesi");
        Date tarihObjesi = document.getDate("kayitTarihi");

        LocalDate kayitTarihi=null;
        if (tarihObjesi != null) {
            kayitTarihi = tarihObjesi.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        return new OgrenciGorunum(ad,soyad,tc,ogrenciNo,sinifSeviyesi,kayitTarihi);


    }

    public String ogrenciAdd(Ogrenci ogrenci) {
        Document filtre= new Document("$or", Arrays.asList(
                new Document("tc",ogrenci.getTc()),
                new Document("OgrenciNo",ogrenci.getOgrenciNo())
        ));

        Document hata = collection.find(filtre).first();
        if(hata != null){
            String mesaj= "Aynı Tc sahip ögrenci var " ;
            return mesaj;
        }

        Date dbDate =Date.from(ogrenci.getKayitTarihi().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Document document = new Document();
        document.append("ad", ogrenci.getAd());
        document.append("soyAd", ogrenci.getSoyAd());
        document.append("tc", ogrenci.getTc());
        document.append("ogrenciNo", ogrenci.getOgrenciNo());
        document.append("sinifSeviyesi", ogrenci.getSinifSeviyesi());
        document.append("kayitTarihi",dbDate);


        try {
            collection.insertOne(document);
           return "Öğrenci Başarıyla Eklendi";       }
        catch (Exception e) {
            return "Öğrenci Eklenemedi" + e.getMessage() ;
        }
    }

    public String ogrenciDelete(String tc) throws OgrenciBulunamadiException {
        Document filitre = new Document("tc", tc);
        long Stc= collection.deleteOne(filitre).getDeletedCount(); // sürücü longu zorunlu tutar
        if(Stc>0){
            return "Öğrenci Başarıyla Silindi";
        }
        else{
            throw new OgrenciBulunamadiException("Öğrenci Bulunamadı");
        }
    }

    public OgrenciGorunum ogrenciGorunumSearch(String tc){

        Document filtre= new Document("tc", tc);
        Document doc = collection.find(filtre).first();
        return mapDocumentToOgrenciGorunum(doc);
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

    public boolean tcKontrol(String tc){
        Document filtre = new Document("tc", tc);
        if(collection.find(filtre).first() != null){
            return true;
        }
        else {
            return false;
        }
    }
}
