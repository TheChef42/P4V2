package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class fillBalenceController {
    Users currentUser;
    Label currentBalance;

    public void setUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    void theCurrentBalance(Users currentUser){
        currentBalance.setText("Current balance: " + currentUser.getBalance());
    }

    public void addCurrency(int amount){
        currentUser.deposit(currentUser.getBalance() + amount);
    }

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage(currentUser);
    }


}
