package com.example.obsapp.model;


import java.util.Scanner;
import com.example.obsapp.util.DBUtil;
import com.mongodb.client.MongoDatabase;

public class Main {
    public static void main(String[] args) {
        MongoDatabase Db = DBUtil.getInstance().getDatabase();

        String metin ;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the metin name: ");
        System.out.println("JAva proje");

    }
}
