package com.example.p4v2;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class CreateUserController {
    public Label PrintName;
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
        String firstName = Firstname.getText();
        String lastName = Lastname.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();
        String CheckPassword = passwordCheck.getText();

        if (!userPassword.equals(CheckPassword)){
            PrintName.setText("Passwords do not match!");
        }
        else if(userPassword.length() < 10){
            PrintName.setText("Error: password must be at least 10");
        }
        else if(!userEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            PrintName.setText("Error: email must be a valid email address");
        }
        else if (Users.createUser(userEmail, userPassword, firstName, lastName)){
                Main.showLoginView();
        }
    }

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showLoginView();
    }


}
