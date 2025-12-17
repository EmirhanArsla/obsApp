package com.example.obsapp.model;

public class Not {
    //-------------------Alanlar---------------------
    private String NotId;
    private String DersId;
    private String Dersad;
    private String OgrenciId;
    private int Sinav1;
    private int Sinav2;
    private int sinif;
    //-----------------Yapıcı Metot----------------------
    public Not(String ogrenciId , String dersad,int sinav1,int sinav2,int sinif){
        this.OgrenciId=ogrenciId;
        this.Dersad=dersad;
        this.Sinav1=sinav1;
        this.Sinav2=sinav2;
        this.sinif=sinif;
        this.DersId =dersad.toUpperCase()+sinif;
        this.NotId= ogrenciId + "-" + ogrenciId ;
    }
    //--------------Getter Setter-------------------

    public String getNotId() {
        return NotId;
    }

    public void setNotId(String notId) {
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

    public int getSinav1() {
        return Sinav1;
    }

    public void setSinav1(int sinav1) {
        Sinav1 = sinav1;
    }

    public int getSinav2() {
        return Sinav2;
    }

    public void setSinav2(int sinav2) {
        Sinav2 = sinav2;
    }

    public int getSinif() {
        return sinif;
    }

    public void setSinif(int sinif) {
        this.sinif =sinif;}

    public String getDersad() {
        return Dersad;
    }

    public void setDersad(String dersad) {
        Dersad = dersad;
    }
}