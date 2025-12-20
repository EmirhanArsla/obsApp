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
    // MongoDB üzerinde "Ogrenciler" koleksiyonunu temsil eder
    private final MongoCollection<Document> collection;
   //-------------------Yapıcı Metot----------------
    public OgrenciDao(MongoCollection<Document> database) {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();
        if (database == null) {
            throw new IllegalArgumentException("Not koleksiyonu null olamaz.");
        }
        this.collection = database;

    }
    //Veritabanından gelen Document nesnesini,
    //TableView üzerinde kullanılacak OgrenciGorunum nesnesine dönüştürür.
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
    //Yeni bir öğrenciyi veritabanına ekler.
    // Aynı TC veya öğrenci numarasına sahip kayıt varsa ekleme yapılmaz.
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
    //TC kimlik numarasına göre öğrenciyi veritabanından siler.
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
    //TC bilgisine göre öğrenci araması yapar ve
    //sonucu OgrenciGorunum tipine dönüştürür.
    public OgrenciGorunum ogrenciGorunumSearch(String tc){

        Document filtre= new Document("tc", tc);
        Document doc = collection.find(filtre).first();
        return mapDocumentToOgrenciGorunum(doc);
    }
    //TC bilgisine göre öğrenciyi ham Document olarak döndürür.
    public Document ogrencisearch(String tc){
        Document filitre = new Document("tc", tc);
        return collection.find(filitre).first() ;
    }
    //Sınıf seviyesine göre öğrenci araması yapar.
    public List<Document> ogrencisearch2(int sinifSeviyesi){
        Document filitre = new Document("sinifSeviyesi", sinifSeviyesi);
        List<Document> documents = new  ArrayList<>();
        return collection.find(filitre).into(documents) ;
    }
    //Veritabanındaki tüm öğrencileri listeler
    public List<Document> allOgrenci(){
        ArrayList<Document> documents = new ArrayList<>();
        collection.find().into(documents) ;
        return documents;
    }
    // Öğrenci numarası ve TC bilgisine göre giriş kontrolü yapar.
    public Document girisKontrol(String ogrenciNo , String tc){
        Document filtre = new Document("ogrenciNo", ogrenciNo)
                .append("tc", tc);
        return collection.find(filtre).first() ;
    }
    //Girilen TC bilgisinin veritabanında mevcut olup olmadığını kontrol eder.
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
