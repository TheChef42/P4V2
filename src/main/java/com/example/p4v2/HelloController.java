package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public Label PrintName;
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    protected void loginButtonClick(ActionEvent event) throws IOException {
        String str_username = username.getText();
        String str_password = password.getText();
        Users currentUser = Users.login(str_username,str_password);

        if (currentUser != null) {
            PrintName.setText(currentUser.getName());
            System.out.println(currentUser.getName());

            //Change stage to the user start page
            Parent root = FXMLLoader.load(getClass().getResource("startUserPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            PrintName.setText("Denied, bitch!");
        }
    }
}