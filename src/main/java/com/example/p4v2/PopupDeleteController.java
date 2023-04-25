package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupDeleteController {
    public Label PrintName;
    private Stage stage;

    Users currentUser;

    private boolean answer;

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(Users user) {
        this.currentUser = user;
    }

    @FXML
    private TextField passwordCheck;


    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        setAnswer(true);
        String CheckPassword = passwordCheck.getText();
        Users.deleteAccount(currentUser);
        Main.showLoginView();
        if (Users.verifyPassword(currentUser.getEmail(), CheckPassword)) {
        } else {
            PrintName.setText("Password not correct!");
        }
    }

    @FXML
    private void handleCancelButton() throws IOException {
        setAnswer(false);
        Main.showUserPage(currentUser);
    }
}
