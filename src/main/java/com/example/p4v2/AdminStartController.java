package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class AdminStartController {

    Label noUser;

    @FXML
    protected void shoppingPageButtonClick(ActionEvent event) throws IOException {
        // skal fjerne objektet, ellers ligger de stadig i backenden
        // kan fjernes og muligvis exploides til sikkerheds testing:
        Main.showShoppingPage();
    }

    @FXML
    protected void adminPageButtonClick(ActionEvent event) throws IOException {
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
