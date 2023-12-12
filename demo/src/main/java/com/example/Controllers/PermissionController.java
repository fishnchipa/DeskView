package com.example.Controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.example.Server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PermissionController {
    
    @FXML
    private Button accept;

    @FXML
    private Button decline;

    public void accept(ActionEvent event) {
        Socket connection = Server.getSocket();
        ScreenController.activate("view");
        
        // Sending confirmation to Client
        try {
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(1);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        notifyAll();
    }

    public void decline(ActionEvent event) {
        Socket connection = Server.getSocket();
        ScreenController.activate("start");
        try {
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(0);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
