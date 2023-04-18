package com.example.p4v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginpage.fxml"));
        Scene loginpage = new Scene(fxmlLoader.load(), 1400, 900);
        stage.setTitle("Selfservice 3000!");
        stage.setScene(loginpage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}