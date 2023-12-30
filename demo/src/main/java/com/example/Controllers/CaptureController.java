package com.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CaptureController {
    @FXML
    private Pane Body;

    public void initialize() {
        Stage stage = (Stage) Body.getScene().getWindow();
        stage.setFullScreen(false);
        stage.setMaximized(false);
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }
}
