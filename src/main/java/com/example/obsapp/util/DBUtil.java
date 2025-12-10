package com.example.obsapp.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class DBUtil {


    private final static String ConnetingString ="mongodb+srv://Emirhan:4002.Emir78@emirhanarslan.7yqfsqr.mongodb.net/?retryWrites=true&w=majority&appName=EmirhanArslan";
    private static MongoClient mongoClient;
    private static MongoDatabase datebase;
    private static DBUtil instance;
    private final String DATABASE_NAME = "Ogrenci_Bilgi_Sistemi" ;

    private DBUtil() {
        try {
            // Bağlantı dizesini kullanarak istemci ( nesnesini oluştur
            mongoClient = MongoClients.create(ConnetingString);
            mongoClient.getDatabase("admin").runCommand(new Document("ping", 1)) ;//
            System.out.println("MongoDB bağlantısı başarılı.");

        } catch (Exception e) {
            System.err.println("MongoDB bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static DBUtil getInstance() {
        if (instance == null) {
            instance = new DBUtil();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        if (mongoClient == null) {
            throw new IllegalStateException("MongoDB istemcisi başlatılmadı.");
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

}
