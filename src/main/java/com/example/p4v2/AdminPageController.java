package com.example.p4v2;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.event.ActionEvent;
public class AdminPageController {

    Admin currentAdmin = Main.getCurrentAdmin();


    @FXML
    protected void showUserOverview(ActionEvent event) throws IOException {
        Main.showUserOverview();
    }

    @FXML
    protected void showProductOverview(ActionEvent event) throws IOException {
        Main.showProductOverview();
    }

    @FXML
    protected void backToAdminStartOverview(ActionEvent event) throws IOException {
        Main.showStartAdminPage();
    }
    @FXML
    protected void logoutButtonClick(ActionEvent event) throws IOException {
        // skal fjerne objektet, ellers ligger de stadig i backenden
        // kan fjernes og muligvis exploides til sikkerheds testing:
        Main.setCurrentAdmin(null);
        Main.showLoginView();
    }
    

    
}
