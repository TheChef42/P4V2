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

    @FXML
    private TextField passwordCheck;


    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        setAnswer(true);
        String CheckPassword = passwordCheck.getText();
        if (Users.verifyPassword(Main.getCurrentuser().getEmail(), CheckPassword)) {
            Users.deleteAccount(Main.getCurrentuser());
            Main.showLoginView();
        } else {
            PrintName.setText("Password not correct!");
        }
    }

    @FXML
    private void handleCancelButton() throws IOException {
        setAnswer(false);
        Main.showUserPage();
    }
}
