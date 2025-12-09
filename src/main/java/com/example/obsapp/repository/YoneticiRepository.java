package com.example.obsapp.repository;

import java.io.BufferedReader;
import java.io.FileReader;

public class YoneticiRepository {

    private static final String FILE_PATH = "src/main/txt/Yonetici.txt";

    public boolean yoneticiDogrula(String kullanici, String sifre) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parca = satir.split(";");

                if (parca.length == 2) {
                    String k = parca[0];
                    String s = parca[1];

                    if (k.equals(kullanici) && s.equals(sifre)) {
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
