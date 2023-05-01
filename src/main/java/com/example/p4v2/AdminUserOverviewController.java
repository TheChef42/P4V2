package com.example.p4v2;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminUserOverviewController implements Initializable {

    private Admin currentAdmin;
    private TableView<Users> usersTable;
    private TableColumn<Users, Integer> colUserID;
    private TableColumn<Users, String> colFirstName;
    private TableColumn<Users, String> colLastName;
    private TableColumn<Users, String> colEmail;
    private TableColumn<Users, Double> colBalance;
    private TableColumn<Users, Date> colCreatedAt;

    public void setAdmin(Admin currentAdmin) {
        this.currentAdmin = Main.getCurrentAdmin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(currentAdmin == null){
            System.out.println("admin is null");
        }
        ObservableList<Users> observableList = FXCollections.observableArrayList(currentAdmin.getUsers());
        colUserID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        usersTable.setItems(observableList);
    }

    @FXML
    protected void showProductOverview() throws IOException {
        Main.showProductOverview(currentAdmin);
    }

    @FXML
    protected void goBack() throws IOException {
        Main.showAdminPage(currentAdmin);
    }

    @FXML
    protected void logoutButtonClick() throws IOException {
        currentAdmin = null;
        Main.showLoginView();
    }
}
