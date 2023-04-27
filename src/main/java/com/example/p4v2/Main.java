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
    private static Stage primaryStage;
    private static BorderPane mainLayout;
    private static TableView table = new TableView<Products>();
    private static TableView tableFill = new TableView<Payment>();

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

    public static void showPopupMobilpay(Users user, Integer id, Float amount) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("popupMobilepay.fxml"));
        mainLayout = loader.load();

        PopupMobilepayController pmc = loader.getController();
        pmc.setUser(user);
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

    public static void showShoppingPage(Users user) throws IOException {
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
        supc.setUser(user);
        supc.setPrintName(user);
        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showFillBalence(Users user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fillBalence.fxml"));
        mainLayout = loader.load();

        tableFill.setEditable(true);
        TableColumn date = new TableColumn<>("date");
        TableColumn amount = new TableColumn<>("amount");

        tableFill.getColumns().addAll(date, amount);


        fillBalenceController fbc = loader.getController();
        fbc.setUser(user);
        fbc.setPrintName(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showUserPage(Users user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("User.fxml"));
        mainLayout = loader.load();

        UserPageController upc = loader.getController();
        upc.setUser(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showUpdateUserInfoPage(Users user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("updateinfo.fxml"));
        mainLayout = loader.load();

        UserUpdatesController uuc = loader.getController();
        uuc.setUser(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showStatistcsPage(Users user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("statestik.fxml"));
        mainLayout = loader.load();

        StatisticsController sc = loader.getController();
        sc.setUser(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showAdminPage(Admin user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("adminPage.fxml"));
        mainLayout = loader.load();

        AdminPageController upc = loader.getController();
        upc.setAdmin(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showUserOverview(Admin user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("adminUserOverviewPage.fxml"));
        mainLayout = loader.load();

        AdminUserOverviewController upc = loader.getController();
        upc.setAdmin(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void showProductOverview(Admin user) throws IOException {
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
        upc.setAdmin(user);

        Scene scene = new Scene(mainLayout, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public static void showPopupDelete(Users user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("popupDelete.fxml"));
        mainLayout = loader.load();

        PopupDeleteController pdc = loader.getController();
        pdc.setUser(user);

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}