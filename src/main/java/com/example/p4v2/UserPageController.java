package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class UserPageController {
    Users currentUser;
    public void setUser(Users currentUser) {
        this.currentUser = currentUser;
    }
    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showShoppingPage(currentUser);
    }
}
