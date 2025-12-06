package com.example.obsapp.Viewmodel;

public class NotGorunum {

    private String dersId;
    private String OgrenciId ;
    private int Sinif;
    private int Sinav1;
    private int Sinav2;

public NotGorunum(String dersId , String ogrenciId ,int sinif ,int sinav1 ,int sinav2){
    this.dersId=dersId;
    this.OgrenciId=ogrenciId;
    this.Sinif=sinif;
    this.Sinav1=sinav1;
    this.Sinav2=sinav2;

}

    public String getDersId() {
        return dersId;
    }

    public void setDersId(String dersId) {
        this.dersId = dersId;
    }

    public String getOgrenciId() {
        return OgrenciId;
    }

    public void setOgrenciId(String ogrenciId) {
        OgrenciId = ogrenciId;
    }

    public int getSinif() {
        return Sinif;
    }

    public void setSinif(int sinif) {
        Sinif = sinif;
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
}
