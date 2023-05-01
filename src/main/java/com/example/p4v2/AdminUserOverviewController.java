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

    public Admin currentAdmin;
    public TableView<Users> usersTable;
    public TableColumn<Users, Integer> colUserID;
    public TableColumn<Users, String> colName;
    public TableColumn<Users, String> colEmail;
    public TableColumn<Users, Double> colBalance;
    public TableColumn<Users, Date> colCreatedAt;

    public void setAdmin() {
        currentAdmin = Main.getCurrentAdmin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Users> observableList = FXCollections.observableArrayList(Admin.getUsers());
        colUserID.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Users, String>("firstName" + "lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
        colBalance.setCellValueFactory(new PropertyValueFactory<Users, Double>("balance"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<Users, Date>("createdAt"));

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
