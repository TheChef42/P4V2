package com.example.p4v2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    public Label PrintName;
    @FXML
    private TextField username;

    @FXML
    private TextField password;


    @FXML
    protected void loginButtonClick() {
        String str_username = username.getText();
        String str_password = password.getText();
        Users currentUser = Users.login(str_username,str_password);

        if (currentUser != null) {
            PrintName.setText(currentUser.getName());
            System.out.println(currentUser.getName());
        } else {
            PrintName.setText("Denied, bitch!");
        }

    }
}