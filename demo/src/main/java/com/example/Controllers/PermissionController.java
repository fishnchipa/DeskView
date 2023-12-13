package com.example.Controllers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.example.Server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PermissionController {
    
    @FXML
    private Button accepting;

    @FXML
    private Button declining;

    public void accept(ActionEvent event) {
        Socket connection = Server.getSocket();
        
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
