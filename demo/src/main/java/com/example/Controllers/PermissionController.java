package com.example.Controllers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.example.Server;
import com.example.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PermissionController extends User{
    
    @FXML
    private Button accepting;

    @FXML
    private Button declining;

    private ScreenController screenController = new ScreenController();

    public void accept(ActionEvent event) {
        Socket connection = Server.getSocket();
        System.out.println("Accepted");
        screenController.activate("view");
        // Sending confirmation to Client
        try {
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(1);
            writer.close();

        } catch (IOException e) {
            System.out.println("Acception error");
            e.printStackTrace();
        }
        synchronized(server) {
            notifyAll();
        }
        
    }

    public void decline(ActionEvent event) {
        Socket connection = Server.getSocket();
        screenController.activate("start");
        try {
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(0);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
