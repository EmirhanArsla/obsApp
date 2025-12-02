module com.example.obsapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires org.mongodb.driver.sync.client;

    opens com.example.obsapp to javafx.fxml;
    exports com.example.obsapp;
    exports com.example.obsapp.controller;
    opens com.example.obsapp.controller to javafx.fxml;
}