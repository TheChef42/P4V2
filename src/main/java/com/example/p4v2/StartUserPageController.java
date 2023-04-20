package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

public class StartUserPageController {
    private Parent root;
    Users currentUser;

    public Label PrintName;

    /*public StartUserPageController(){
        PrintName.setText(this.currentUser.getName());
    }*/

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
}
