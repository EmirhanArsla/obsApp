package com.example.obsapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class HelloController {

    @FXML
        private Label welcomeText1;

        @FXML
        private Label welcomeText;

        @FXML
        private TextField usernameField;

        @FXML
        private PasswordField passwordField;

        @FXML
        private Label loginText;



        @FXML
        void onLoginClick(ActionEvent event) {
             String user = usernameField.getText();
            String pass = passwordField.getText();

            boolean kontrol = girisKontrol(user, pass);

            if (kontrol == true) {
                loginText.setText("Giriş başarılı!");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("SecondPage.fxml"));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();


                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else {
                loginText.setText("Hatalı kullanıcı adı veya şifre!");
            }
        }

      private boolean girisKontrol(String username, String password) {
          File dosya = new File("C:\\Users\\90546\\IdeaProjects\\obs\\Kullanıcılar.txt");

          try {
              // 2. Dosyayı okumak için Scanner açıyoruz (listFiles KULLANILMAZ)
              Scanner okuyucu = new Scanner(dosya);

              // 3. Dosya bitene kadar satır satır dön
              while (okuyucu.hasNextLine()) {
                  String satir = okuyucu.nextLine(); // Örn: "admin,1234" okudu

                  // 4. Virgül'den parçala
                  String[] parcalar = satir.split(",");

                  if (parcalar.length == 2) {
                      String dosyadakiUser = parcalar[0].trim();
                      String dosyadakiPass = parcalar[1].trim();

                      // 5. Hem kullanıcı adı HEM şifre eşleşiyor mu?
                      if (dosyadakiUser.equals(username) && dosyadakiPass.equals(password)) {
                          okuyucu.close();
                          return true; // Eşleşme bulundu!
                      }
                  }
              }
              okuyucu.close();

          } catch (FileNotFoundException e) {
              System.out.println("Dosya bulunamadı! 'Kullanicilar.txt' dosyasını proje ana dizinine koydun mu?");
          }

          return false; // Tüm dosyayı taradık ama bulamadık
      }

}


