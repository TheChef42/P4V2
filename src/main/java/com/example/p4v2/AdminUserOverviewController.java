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
    private TableColumn<Users, String> colName;
    private TableColumn<Users, String> colEmail;
    private TableColumn<Users, Double> colBalance;
    private TableColumn<Users, Date> colCreatedAt;

    public void setAdmin() {
        currentAdmin = Main.getCurrentAdmin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Users> observableList = FXCollections.observableArrayList(Admin.getUsers());
        colUserID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName" + "lastName"));
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
