package com.example.p4v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Users currentuser;
    private static Admin currentAdmin;
    private static Stage primaryStage;
    private static BorderPane mainLayout;
    private static TableView table = new TableView<Products>();
    private static TableView tableFill = new TableView<Payment>();

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }
    public static void setCurrentAdmin(Admin currentaAdmin) {
        Main.currentAdmin = currentAdmin;
    }

    
    public static Users getCurrentuser() {
        return currentuser;
    }
    public static void setCurrentuser(Users currentuser) {
        Main.currentuser = currentuser;
    }

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showLoginView();
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

    public static void showPopupMobilpay(Integer id, Float amount) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("popupMobilepay.fxml"));
        mainLayout = loader.load();

        PopupMobilepayController pmc = loader.getController();
        pmc.setId(id);

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showCreateUser() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("createUser.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showShoppingPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("startUserPage.fxml"));
        mainLayout = loader.load();

        table.setEditable(true);
        TableColumn product = new TableColumn<>("Product");
        TableColumn price = new TableColumn<>("Price");
        TableColumn amount = new TableColumn<>("Amount");
        TableColumn sum = new TableColumn<>("Sum");

        table.getColumns().addAll(product,price,amount,sum);

        StartUserPageController supc = loader.getController();
        supc.setPrintName();

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showFillBalence() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fillBalence.fxml"));
        mainLayout = loader.load();

        tableFill.setEditable(true);
        TableColumn date = new TableColumn<>("date");
        TableColumn amount = new TableColumn<>("amount");

        tableFill.getColumns().addAll(date, amount);

        fillBalenceController fbc = loader.getController();
        fbc.setPrintName();

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showUserPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("User.fxml"));
        mainLayout = loader.load();

        UserPageController upc = loader.getController();
        upc.setUserBalance();

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showUpdateUserInfoPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("updateinfo.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showStatistcsPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("statestik.fxml"));
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showStartAdminPage(Admin admin) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("adminStartPage.fxml"));
        mainLayout = loader.load();

        AdminStartController asc = loader.getController();
        asc.setAdmin(admin);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showAdminPage(Admin admin) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("adminPage.fxml"));
        mainLayout = loader.load();

        AdminPageController apc = loader.getController();
        apc.setAdmin(admin);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showUserOverview(Admin admin) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("adminUserOverviewPage.fxml"));
        mainLayout = loader.load();

        AdminUserOverviewController upc = loader.getController();
        upc.setAdmin();

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showProductOverview(Admin admin) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("adminProductOverviewPage.fxml"));
        mainLayout = loader.load();

        table.setEditable(true);
        TableColumn product = new TableColumn<>("Product");
        TableColumn price = new TableColumn<>("Price");
        TableColumn stock = new TableColumn<>("Stock");
        TableColumn id = new TableColumn<>("Id");
        table.getColumns().addAll(product,price,stock,id);

        AdminProductOverviewController upc = loader.getController();
        upc.setAdmin(admin);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public static void showPopupDelete() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("popupDelete.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}