package com.example.p4v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static BorderPane mainLayout;

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showLoginView();

        // Old start page
        /*FXMLLoader root = new FXMLLoader(HelloApplication.class.getResource("loginpage.fxml"));
        Scene login_page = new Scene(root.load(), 1400, 900);
        stage.setTitle("Selfservice 3000!");
        stage.setScene(login_page);
        stage.show();*/
    }

    public static void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("loginpage.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showShoppingPage(Users user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("startUserPage.fxml"));
        mainLayout = loader.load();

        StartUserPageController supc = loader.getController();
        supc.setUser(user);
        supc.setPrintName(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}