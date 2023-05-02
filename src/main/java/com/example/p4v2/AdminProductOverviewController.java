package com.example.p4v2;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminProductOverviewController implements Initializable {

    Admin currentAdmin;
    public TableView<Products> products;
    public TableColumn<Products, Integer> colProductID;
    public TableColumn<Products,String> colProduct;
    public TableColumn<Products,Double> colPrice;
    public TableColumn<Products, Integer> colStock;
    Transaction currentTransaction = new Transaction();

    public void setAdmin(Admin currentAdmin) {
        this.currentAdmin = Main.getCurrentAdmin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Products> observableList = FXCollections.observableArrayList(currentTransaction.getProducts());
        colProductID.setCellValueFactory(new PropertyValueFactory<Products, Integer>("productID"));
        colProduct.setCellValueFactory(new PropertyValueFactory<Products, String>("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Products, Double>("Price"));
        colStock.setCellValueFactory(new PropertyValueFactory<Products, Integer>("Stock"));

        products.setItems(observableList);
    }

    @FXML
    protected void showUserOverview(ActionEvent event) throws IOException {
        Main.showUserOverview(currentAdmin);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        Main.showAdminPage(currentAdmin);
    }

    @FXML
    protected void logoutButtonClick(ActionEvent event) throws IOException {
        // skal fjerne objektet, ellers ligger de stadig i backenden
        // kan fjernes og muligvis exploides til sikkerheds testing:
        currentAdmin = null;
        Main.showLoginView();
    }




}
