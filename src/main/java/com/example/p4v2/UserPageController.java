package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

import static java.lang.String.valueOf;

public class UserPageController {
    public Label userBalance;
    public void setUserBalance() {
        userBalance.setText("Balance: " + valueOf(Main.getCurrentuser().getBalance()));
    }

    @FXML
    protected void updatePageButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUpdateUserInfoPage();
    }

    @FXML
    protected void fillBalencePageButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showFillBalence();
    }

    @FXML
    protected void statisticsPageButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showStatistcsPage();
    }

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showShoppingPage();
    }

    public void DeleteButtonClick(ActionEvent actionEvent) throws IOException {
        Main.showPopupDelete();
    }
}
