package com.example.p4v2;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import static com.example.p4v2.Main.getCurrentAdmin;

public class AdminProductOverviewController implements Initializable {
    @FXML
    public TextField productName;
    @FXML
    public TextField productPrice;
    @FXML
    public TextField productStock;
    public TableView<Products> products;
    public TableColumn<Products, Integer> colProductID;
    public TableColumn<Products, String> colProduct;
    public TableColumn<Products, Float> colPrice;
    public TableColumn<Products, Integer> colStock;
    Transaction currentTransaction = new Transaction();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Products> observableList = FXCollections.observableArrayList(currentTransaction.getProducts());
        colProductID.setCellValueFactory(new PropertyValueFactory<Products, Integer>("productID"));


        colProduct.setCellValueFactory(new PropertyValueFactory<Products, String>("Name"));
        colProduct.setCellFactory(TextFieldTableCell.forTableColumn());
        colProduct.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                Products product = event.getRowValue();
                product.setName(event.getNewValue());
            }
        });

        colPrice.setCellValueFactory(new PropertyValueFactory<Products, Float>("Price"));
        colPrice.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colPrice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, Float>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, Float> event) {
                Products product = event.getRowValue();
                product.setPrice(event.getNewValue());
            }
        });

        colStock.setCellValueFactory(new PropertyValueFactory<Products, Integer>("Stock"));
        colStock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colStock.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, Integer> event) {
                Products product = event.getRowValue();
                product.setStock(event.getNewValue());
            }
        });

        products.setEditable(true);
        products.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        products.setItems(observableList);
    }

    @FXML
    public void updateButtonClicked(ActionEvent event) {
        Products product = products.getSelectionModel().getSelectedItem();

        String name = product.getName();
        float price = product.getPrice();
        int stock = product.getStock();

        getCurrentAdmin().updateProduct(product, name, price, stock);

        // update table
        ObservableList<Products> observableList = FXCollections.observableArrayList(currentTransaction.getProducts());
        products.setItems(observableList);
    }

    @FXML
    public void addButtonClicked(ActionEvent event) throws IOException {
        Products product = new Products();

        String newName = productName.getText();
        float newPrice = Float.parseFloat(productPrice.getText());
        int newStock = Integer.parseInt(productStock.getText());

        System.out.print(newName);
        System.out.println(newPrice);
        System.out.println(newStock);

        product.setName(newName);
        product.setPrice(newPrice);
        product.setStock(newStock);

        getCurrentAdmin().createProduct(product);

        // update table
        products.getItems().add(product);

        //clear fields
        productName.setText("");
        productPrice.setText("");
        productStock.setText("");
    }

    @FXML
    public void deleteButtonClicked(ActionEvent event) {
        Products product = products.getSelectionModel().getSelectedItem();
        int remove = -1;
        product.setStock(remove);

        products.getItems().remove(product);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        Main.showAdminPage();
    }

    @FXML
    protected void logoutButtonClick(ActionEvent event) throws IOException {
        // skal fjerne objektet, ellers ligger de stadig i backenden
        // kan fjernes og muligvis exploides til sikkerheds testing:
        Main.setCurrentAdmin(null);
        Main.showLoginView();
    }
}