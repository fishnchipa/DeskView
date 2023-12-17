package com.example.Controllers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.example.Server;
import com.example.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PermissionController {
    
    @FXML
    private Button accepting;

    @FXML
    private Button declining;

    private Server server;

    public PermissionController(Server server) {
        this.server = server;
    }

    public void accept(ActionEvent event) {
        Socket connection = Server.getSocket();

        
        System.out.println("Accepted");
        System.out.println("Loading Screen");
        ScreenController.activate("view");

        // Sending confirmation to Client
        try {

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(1);
            System.out.println("Confirmation Sent");

        } catch (IOException e) {
            System.out.println("Acception error");
            e.printStackTrace();
        }
        synchronized(server) {
            server.notify();
        }
        
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
