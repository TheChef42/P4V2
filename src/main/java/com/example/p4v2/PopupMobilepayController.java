package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupMobilepayController {

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

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        setAnswer(true);
        fillBalenceController.fillBalence();
        Main.showFillBalence(fillBalenceController.getCurrentUser());
    }

    @FXML
    private void handleCancelButton() throws IOException {
        setAnswer(false);
        Main.showFillBalence(fillBalenceController.getCurrentUser());
    }
}

