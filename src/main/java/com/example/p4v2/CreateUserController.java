package com.example.p4v2;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class CreateUserController {
    public Label PrintName;
    public Label emailWarning;
    @FXML
    private TextField Firstname;
    @FXML
    private TextField Lastname;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordCheck;


    @FXML
    public void handleCreateUser(ActionEvent event) throws IOException {
        //validate the email
        if (!Users.emailRegex(email.getText())) {
            emailWarning.setText("Wrong email format! Must be aau email.");
            emailWarning.setMinHeight(17);
        } else {
            emailWarning.setMinHeight(0);
            String firstName = Firstname.getText();
            String lastName = Lastname.getText();
            String userEmail = email.getText();
            String userPassword = password.getText();
            String CheckPassword = passwordCheck.getText();

            if (userPassword.equals(CheckPassword)) {
                if (Users.createUser(userEmail, userPassword, firstName, lastName)) {
                    Main.showLoginView();
                } else if (userPassword.length() < 10) {
                    PrintName.setText("Error: password must be at least 10");
                } else {
                    PrintName.setText("Error: email already in use");
                }
            } else {
                PrintName.setText("Passwords do not match!");
            }
        }
    }

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showLoginView();
    }


}
