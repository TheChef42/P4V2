package com.example.p4v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(HelloApplication.class.getResource("loginpage.fxml"));
        Scene login_page = new Scene(root.load(), 1400, 900);
        stage.setTitle("Selfservice 3000!");
        stage.setScene(login_page);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}