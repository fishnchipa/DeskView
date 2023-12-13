package com.example.Controllers;

import com.example.Client;
import com.example.Server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class UserController {
    

    @FXML
    private TextField ipConnection;

    @FXML 
    private Button enter;

    @FXML 
    private Pane permission;

    @FXML
    private Pane main;

    @FXML 
    private Pane wait;

    private Client client = new Client();

    public void initialize() {
        
    }

    public void submit(ActionEvent event) {
        System.out.println("Attemping to Connect to Server");
        if (client.connectToServer(ipConnection.getText())) {

            // Waiting for Server accept
            wait.setVisible(true);
            main.setDisable(true);

            if(client.receivePermission()) {
                ScreenController.activate("capture");
                client.sendScreen();
            }

        } else {
            System.out.println("Failed to Connect");
        }
    }
}
