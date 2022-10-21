package com.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DisMatchPopController {
    @FXML
    private Button Retry;

    @FXML
    void onRetryButtonClick(MouseEvent event) {
        Stage stage = (Stage)Retry.getScene().getWindow();
        stage.close();
    }

}
