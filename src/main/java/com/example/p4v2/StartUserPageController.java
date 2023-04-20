package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartUserPageController implements Initializable {
    public TableView<Products> basket;
    public TableColumn<Products,String> colProduct;
    public TableColumn<Products,Double> colPrice;
    public TableColumn<Products, Integer> colAmount;
    public TableColumn<Products,Double> colSum;
    private Parent root;
    Users currentUser;
    public Label PrintName;


    public void setPrintName(Users currentUser){
        PrintName.setText(currentUser.getName());
    }

    public void setUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    protected void AddButtonClick(ActionEvent event) {
        //Add the product chosen to the list on the right
    }

    @FXML
    protected void logOutButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showLoginView();
    }

    public void screenClick(javafx.scene.input.MouseEvent mouseEvent) {
        PrintName.setText(this.currentUser.getName());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProduct.setCellValueFactory(new PropertyValueFactory<Products, String>("ProductName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Products, Double>("ProductPrice"));
        colAmount.setCellValueFactory(new PropertyValueFactory<Products, Integer>("ProductAmount"));
        colSum.setCellValueFactory(new PropertyValueFactory<Products, Double>("ProductSum"));

    }
}
