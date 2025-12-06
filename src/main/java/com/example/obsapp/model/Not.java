package com.example.obsapp.model;

public class Not {
    private int NotId;
    private String DersId;
    private String OgrenciId;
    private String Sinav1;
    private String Sinav2;
    private int sinif;


    public int getNotId() {
        return NotId;
    }

    public void setNotId(int notId) {
        NotId = notId;
    }

    public String getDersId() {
        return DersId;
    }

    public void setDersId(String dersId) {
        DersId = dersId;
    }

    public String getOgrenciId() {
        return OgrenciId;
    }

    public void setOgrenciId(String ogrenciId) {
        OgrenciId = ogrenciId;
    }

    public String getSinav1() {
        return Sinav1;
    }

    public void setSinav1(String sinav1) {
        Sinav1 = sinav1;
    }

    public String getSinav2() {
        return Sinav2;
    }

    public void setSinav2(String sinav2) {
        Sinav2 = sinav2;
    }

    public int getSinif() {
        return sinif;
    }

    public void setSinif(int sinif) {
        this.sinif = sinif;
    }
}
