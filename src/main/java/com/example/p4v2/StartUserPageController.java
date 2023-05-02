package com.example.p4v2;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
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
    public TableColumn<Products, Products> colDelete = new TableColumn<>("Delete");
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
    public Button cclTransaction;

    private Parent root;
    public Label PrintName;
    Transaction currentTransaction = new Transaction();

    public void setPrintName(){
        PrintName.setText(Main.getCurrentuser().getName());
        userBalance.setText(valueOf(Main.getCurrentuser().getBalance()));
    }


    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        basketSum.textProperty().bind(sumValue);

        int i = -1;
        for (Products product: currentTransaction.getProducts()) {
            i++;
            if (i > 5) {
                productWarning.setText("Warning more than 6 products");
                break;
            }
            try {
                Field field = getClass().getDeclaredField("Item" + i + "Label");
                Label label = (Label) field.get(this);
                label.setText(product.name);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        colProduct.setCellValueFactory(new PropertyValueFactory<Products, String>("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Products, Double>("Price"));
        colAmount.setCellValueFactory(new PropertyValueFactory<Products, Integer>("SelectAmount"));
        colAmount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colDelete.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        colDelete.setCellFactory(param -> new TableCell<Products, Products>(){
            private final Button deleteButton = new Button("Delete");
            @Override
            protected void updateItem(Products product, boolean empty){
                super.updateItem(product,empty);
                if(product == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    currentTransaction.basket.remove(product);
                    observableList.remove(product);
                    product.setSelectAmount(1);
                    basket.refresh();
                    setSumValue();
                    setPrintName();
                });

            }
        });
        colAmount.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, Integer> event) {
                if(event.getNewValue() < 0){
                    productWarning.setText("Cheater you can not set it to negative!");
                    basket.refresh();
                    return;
                } else if (event.getNewValue() == 0) {
                }
                event.getRowValue().setSelectAmount(event.getNewValue());
                basket.refresh();
                setSumValue();
                setPrintName();
            }
        });
        colSum.setCellValueFactory(new PropertyValueFactory<Products,Double>("Sum"));
        basket.setEditable(true);
        basket.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        basket.setItems(observableList);
    }

    ObservableList<Products> observableList = FXCollections.observableArrayList(currentTransaction.getBasket());

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
    }public void AddButton3Click(ActionEvent actionEvent) {
        Products products = currentTransaction.getProducts().get(3);
        this.addProductToTransaction(products);
        setSumValue();
        basket.refresh();
    }public void AddButton4Click(ActionEvent actionEvent) {
        Products products = currentTransaction.getProducts().get(4);
        this.addProductToTransaction(products);
        setSumValue();
        basket.refresh();
    }public void AddButton5Click(ActionEvent actionEvent) {
        Products products = currentTransaction.getProducts().get(5);
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
        if (currentTransaction.storeTransaction(observableList).equals("allGood")){
        
        for (Products product: observableList) {
            product.setSelectAmount(1);
        }
        currentTransaction.basket.clear();
        observableList.clear();
        basket.refresh();
        setSumValue();
        setPrintName();

        productWarning.setText("Check out complete");
    }else if(currentTransaction.storeTransaction(observableList).equals("stockIssue")){
        productWarning.setText("Insufficient stock");
    }else if(currentTransaction.storeTransaction(observableList).equals("insufficientFunds")){
        productWarning.setText("Insufficient funds");
    }else{
        productWarning.setText("Other issue");
    }
    }

    @FXML
    protected void gotoUserPageButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage();
    }

    @FXML
    protected void logoutButtonClick(ActionEvent event) throws IOException {
        // skal fjerne objektet, ellers ligger de stadig i backenden
        // kan fjernes og muligvis exploides til sikkerheds testing:
        Main.setCurrentuser(null);
        //Change stage to user profile when the scene has been made
        Main.showLoginView();
    }
    public void CclTransaction(ActionEvent actionEvent) {
        //currentTransaction.basket.clear();
        observableList.clear();
        basket.refresh();
        setSumValue();
        setPrintName();
    }
}
