package com.example.p4v2;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.event.ActionEvent;
public class AdminProductOverviewController {

    Admin currentAdmin;

    public void setAdmin(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    @FXML
    protected void showUserOverview(ActionEvent event) throws IOException {
        Main.showUserOverview(currentAdmin);
    }

    @FXML
    protected void logoutButtonClick(ActionEvent event) throws IOException {
        // skal fjerne objektet, ellers ligger de stadig i backenden
        // kan fjernes og muligvis exploides til sikkerheds testing:
        currentAdmin = null;
        Main.showLoginView();
    }
}
