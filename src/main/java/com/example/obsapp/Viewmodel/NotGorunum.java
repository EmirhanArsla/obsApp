package com.example.obsapp.Viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class NotGorunum { // Sınıf adını karışmaması için NotGorunumFx olarak değiştirdim

    // 1. Alanlar SimpleProperty olarak tanımlanır
    private final StringProperty dersId;
    private final StringProperty ogrenciId;
    private final IntegerProperty sinif;
    private final IntegerProperty sinav1;
    private final IntegerProperty sinav2;

    // Kurucu (Constructor)
    public NotGorunum(String dersId, String ogrenciId, int sinif, int sinav1, int sinav2) {
        // 2. Kurucuda, temel Java değerleri Property nesnelerine sarılır (wrap)
        this.dersId = new SimpleStringProperty(dersId);
        this.ogrenciId = new SimpleStringProperty(ogrenciId);
        this.sinif = new SimpleIntegerProperty(sinif);
        this.sinav1 = new SimpleIntegerProperty(sinav1);
        this.sinav2 = new SimpleIntegerProperty(sinav2);
    }

    // --- 3. Property Metotları (Binding için ZORUNLU) ---

    // JavaFX TableView, bu metotları (alan adı + "Property") çağırarak
    // Property nesnesinin kendisini alır ve binding'i kurar.

    public StringProperty dersIdProperty() {
        return dersId;
    }

    public StringProperty ogrenciIdProperty() {
        return ogrenciId;
    }

    public IntegerProperty sinifProperty() {
        return sinif;
    }

    public IntegerProperty sinav1Property() {
        return sinav1;
    }

    public IntegerProperty sinav2Property() {
        return sinav2;
    }

    // --- 4. Getter ve Setter Metotları (İsteğe bağlı, ancak tavsiye edilir) ---

    // Veriyi normal Java kodu içinde kullanmak (get) veya değiştirmek (set) için kullanılır.

    // Getter'lar: Property içindeki gerçek değeri döndürür
    public String getDersId() {
        return dersId.get();
    }

    public int getSinav1() {
        return sinav1.get();
    }

    // Setter'lar: Property içindeki değeri değiştirir (bu değişiklik otomatik olarak arayüze yansır)
    public void setSinav1(int sinav1) {
        this.sinav1.set(sinav1);
    }

    // Diğer getter ve setter metotlarını aynı şekilde tanımlayabilirsiniz...
}