package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    public Label PrintName;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    protected void loginButtonClick(ActionEvent event) throws IOException {
        String str_username = username.getText();
        String str_password = password.getText();
        
        Admin currentaAdmin = Admin.login(str_username,str_password);
        if (currentaAdmin != null) {
            PrintName.setText(currentaAdmin.getName());
            System.out.println(currentaAdmin.getName());
            //Change scene to the user start page
            Main.showAdminPage(currentaAdmin); // Passing the client-object to showClientView method

        }else{

            Users currentUser = Users.login(str_username,str_password);

            if (currentUser != null) {
                PrintName.setText(currentUser.getName());
                System.out.println(currentUser.getName());
                //Change scene to the user start page
                Main.showShoppingPage(currentUser); // Passing the client-object to showClientView method

            } else {
                PrintName.setText("Denied, bitch!");
        }
    }
}
    @FXML
    protected void signUpClick(ActionEvent event) throws IOException {
        Main.showCreateUser();
    }
}