package com.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserController {
    

    @FXML
    private TextField ipConnection;

    @FXML 
    private Button enter;



    public void submit(ActionEvent event) {
        System.out.println("hi");
        ScreenController.activate("view");
    }
}
