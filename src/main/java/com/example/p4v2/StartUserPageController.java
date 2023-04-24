package com.example.p4v2;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import java.lang.reflect.Field;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class StartUserPageController implements Initializable {
    public TableView<Products> basket;
    public TableColumn<Products,String> colProduct;
    public TableColumn<Products,Double> colPrice;
    public TableColumn<Products, Integer> colAmount;
    public TableColumn<Products,Double> colSum;
    public Label Item0Label;
    public Button Item0Button;
    public Label Item1Label;
    public Button Item1Button;
    public Label Item2Label;
    public Button Item2Button;
    public Label Item3Label;
    public Button Item3Button;
    public Label Item4Label;
    public Button Item4Button;
    public Label Item5Label;
    public Button Item5Button;
    public Label productWarning;
    public Label basketSum;
    final StringProperty sumValue = new SimpleStringProperty("0.0");
    public Label userBalance;

    private Parent root;
    Users currentUser;
    public Label PrintName;
    Transaction currentTransaction = new Transaction();



    public void setPrintName(Users currentUser){
        PrintName.setText(currentUser.getName());
        userBalance.setText(valueOf(currentUser.getBalance()));
    }

    public void setUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        basketSum.textProperty().bind(sumValue);
        int i = -1;
        for (Products products1: currentTransaction.getProducts()) {
            i++;
            switch(i){
                case 0:
                    Item0Label.setText(products1.name);
                    continue;
                case 1:
                    Item1Label.setText(products1.name);
                    continue;
                case 2:
                    Item2Label.setText(products1.name);
                    continue;
                case 3:
                    Item3Label.setText(products1.name);
                    continue;
                case 4:
                    Item4Label.setText(products1.name);
                    continue;
                case 5:
                    Item5Label.setText(products1.name);
                    continue;
                default:
                    productWarning.setText("Warning more than 6 products");
            }
            Item0Label.setText(products1.name);
        }
        colProduct.setCellValueFactory(new PropertyValueFactory<Products, String>("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Products, Double>("Price"));
        colAmount.setCellValueFactory(new PropertyValueFactory<Products, Integer>("SelectAmount"));
        colSum.setCellValueFactory(new PropertyValueFactory<Products,Double>("Sum"));
        basket.setItems(observableList);
    }

    ObservableList<Products> observableList = FXCollections.observableArrayList(
            currentTransaction.getBasket()
    );

    public void AddButton0Click(ActionEvent actionEvent) {
        Products products = currentTransaction.getProducts().get(0);
        this.addProductToTransaction(products);
        setSumValue();
        basket.refresh();
    }public void AddButton1Click(ActionEvent actionEvent) {
        Products products = currentTransaction.getProducts().get(1);
        this.addProductToTransaction(products);
        setSumValue();
        basket.refresh();
    }public void AddButton2Click(ActionEvent actionEvent) {
        Products products = currentTransaction.getProducts().get(2);
        this.addProductToTransaction(products);
        setSumValue();
        basket.refresh();
    }

    private void addProductToTransaction(Products product){
        //TODO: implement to add product to transaction
        if(searchBasketID(product.getProductID()) != null){
            product.selectAmount++;
        } else {
            observableList.add(product);
        }
    }
    private Products searchBasketID (int parameterValue){
        Products product = null;
        for (Products products1: observableList) {
            if (products1.getProductID() == parameterValue)
                product = products1;
        }
        return product;
    }

    public String getSumValue() {
        return sumValue.get();
    }

    public StringProperty sumValueProperty() {
        return sumValue;
    }

    public void setSumValue() {
        float sum = 0;
        for (Products product: observableList) {
            sum += product.getSum();
        }
        String sumString = valueOf(sum);
        this.sumValue.set(sumString);
    }

    public void CheckOutClick(ActionEvent actionEvent) {
        currentTransaction.storeTransaction(currentUser.getId(), observableList);
        currentTransaction.basket.clear();
        observableList.clear();
        basket.refresh();
        setSumValue();
        productWarning.setText("Check out complete");
    }

    @FXML
    protected void gotoUserPageButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage(currentUser);
    }

    @FXML
    protected void logoutButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showLoginView();
    }

}
