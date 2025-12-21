package com.example.obsapp.exception;

public class OgrenciBulunamadiException extends Exception {

    public OgrenciBulunamadiException() {
        super("Öğrenci bulunamadı.");
    }
    public OgrenciBulunamadiException(String message) {
        super(message);
    }
}
