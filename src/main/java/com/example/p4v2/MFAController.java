package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MFAController {
    @FXML
    private TextField confirmKey;

    public Label PrintName;

    private boolean answer;

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public void setStage(Stage stage) {
    }
    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        setAnswer(true);
        String str_confirmKey = confirmKey.getText();
        if (Users.verifyKey(Main.getCurrentuser().getEmail(), str_confirmKey)) {
            Main.showShoppingPage();
        } else {
            PrintName.setText("Key not correct!");
        }
    }
    @FXML
    private void handleCancelButton() throws IOException {
        setAnswer(false);
        Main.showLoginView();
    }
}
