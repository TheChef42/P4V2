package com.example.p4v2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static java.lang.String.valueOf;

public class fillBalenceController {
    static Users currentUser;
    private static float amount;

    @FXML
    public Label currentBalance;
    @FXML
    public Button id50button;
    public Button id100button;
    public Button id150button;
    public Button id200button;

    @FXML
    private TextField customAmount;
    public static void setUser(Users currentUser) {
        fillBalenceController.currentUser = currentUser;
        //currentBalance.setText(" ");
    }



    public static float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        fillBalenceController.amount = amount;
    }

    public Users getCurrentUser() {
        return fillBalenceController.currentUser;
    }

    public Label getCurrentBalance() {
        return currentBalance;
    }


    /*public static void setCurrentUser(Users currentUser) {
        fillBalenceController.currentUser = currentUser;
    }*/


    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage(currentUser);
    }

    public static void fillBalence(){
        fillBalenceController.currentUser.deposit(getAmount());
    }

    public void addCurrency50(ActionEvent actionEvent) throws IOException {
        setAmount(50);
        Main.showPopupMobilpay(currentUser);
    }

    public void addCurrency100(ActionEvent actionEvent) throws IOException {
        setAmount(100);
        Main.showPopupMobilpay(currentUser);
    }
    public void addCurrency150(ActionEvent actionEvent) throws IOException {
        setAmount(150);
        Main.showPopupMobilpay(currentUser);
    }
    public void addCurrency200 (ActionEvent actionEvent) throws IOException {
        setAmount(200);
        Main.showPopupMobilpay(currentUser);
    }



    public void addCurrencyCustom (ActionEvent actionEvent) throws IOException {
        Float inputAmount = Float.parseFloat(customAmount.getText());
        setAmount(inputAmount);
        Main.showPopupMobilpay(currentUser);
    }
}
