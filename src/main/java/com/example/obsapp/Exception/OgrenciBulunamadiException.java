package com.example.obsapp.Exception;

public class OgrenciBulunamadiException extends Exception {

    public OgrenciBulunamadiException() {
        super("Öğrenci bulunamadı.");
    }
    public OgrenciBulunamadiException(String message) {
        super(message);
    }
}
