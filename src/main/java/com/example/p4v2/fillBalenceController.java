package com.example.p4v2;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class fillBalenceController {
    Users currentUser;
    Parent root;
    Label currentBalance;

    @FXML
    void theCurrentBalance(Users currentUser){
        currentBalance.setText("Current balance: " + currentUser.getBalance());
    }

    public void setUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public void addCurrency(int amount){
        currentUser.deposit(currentUser.getBalance() + amount);
    }


}
