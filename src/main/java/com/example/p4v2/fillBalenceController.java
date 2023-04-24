package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class fillBalenceController {
    Users currentUser;
    @FXML
    Label currentBalance;
    @FXML
    public Button id50button;
    public Button id100button;
    public Button id150button;
    public Button id200button;
    @FXML
    public void setUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    void theCurrentBalance(Users currentUser){
        currentBalance.setText("Current balance: " + currentUser.getBalance());
    }


    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage(currentUser);
    }


    public void addCurrency50(ActionEvent actionEvent) {
        currentUser.deposit(50);
    }

    public void addCurrency100(ActionEvent actionEvent) {
        currentUser.deposit(100);
    }

    public void addCurrency150(ActionEvent actionEvent) {
        currentUser.deposit(150);
    }

    public void addCurrency200(ActionEvent actionEvent) {
        currentUser.deposit(200);
    }
}

