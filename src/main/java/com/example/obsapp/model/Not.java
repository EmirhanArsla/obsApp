package com.example.obsapp.model;

public class Not {
    //-------------------Alanlar---------------------
    private String NotId;
    private String DersId;
    private String Dersad;
    private String tc;
    private int Sinav1;
    private int Sinav2;
    private int sinif;
    //-----------------Yapıcı Metot----------------------
    public Not(String ogrenciId , String dersad,int sinav1,int sinav2,int sinif) {
        this.tc = ogrenciId;
        this.Dersad = dersad;
        this.Sinav1 = sinav1;
        this.Sinav2 = sinav2;
        this.sinif = sinif;
        this.DersId = dersad.replace("i", "ı")
                .replace("ğ", "g")
                .replace("ü", "u")
                .replace("ş", "s")
                .replace("ö", "o")
                .replace("ç", "c")
                .replace("İ", "I")

                .toUpperCase() + sinif;
        this.NotId = ogrenciId + "-" + dersad;
    }

    public Not(String tc, String dersad) {
            this.tc = tc;
            this.Dersad = dersad;
            this.Sinav1 = 0;
            this.Sinav2 = 0;
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

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
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