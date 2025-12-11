package com.example.obsapp.DBO;

import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class DersDao {
    private MongoCollection<Document> collection;

    public DersDao() {
        MongoDatabase database = DBUtil.getInstance().getDatabase();
        this.collection = database.getCollection("Ders");
    }

        public List<Document> dersSearch (String dersid ){
            Document filtere = new Document("dersid",dersid);
            List<Document> documents = new  ArrayList<>();

            return collection.find(filtere).into(documents);

    }
    public List<Document> dersSearc2 (int sinif ){
        Document filtere = new Document("sinif",sinif);
        List<Document> documents = new  ArrayList<>();

        return collection.find(filtere).into(documents);

    }
}
