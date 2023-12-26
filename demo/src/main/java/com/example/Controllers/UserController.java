package com.example.Controllers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.example.Client;
import com.example.HandleClientEvent;
import com.example.Server;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UserController {
    

    @FXML
    private TextField IpInput;

    @FXML 
    private Text ConnectionId;

    @FXML
    private Pane ServerSignalOn;

    @FXML
    private Pane ServerSignalOff;

    private Client client;



    public void initialize() {
        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"),12345);
            ConnectionId.setText(datagramSocket.getLocalAddress().getHostAddress());
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void submit(ActionEvent event) {
        client = new Client();

        System.out.println("Attemping to Connect to Server");
        client.connectToServer(IpInput.getText());

        // Waiting for Server accept
        System.out.println("Waiting for Server");

        if(client.receivePermission()) {
            System.out.println("Successfully Connected to Server");

            ScreenController.activate("capture");
            System.out.println("sending images");
            
            // Thread for sending events
            HandleClientEvent clientHandler = new HandleClientEvent(client);
            Thread clientHandlerThread = new Thread(clientHandler);
            clientHandlerThread.start();

            // Sending screen capture
            client.sendScreen();
        } 
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void setServerStatus(boolean status) {
        if (status) {
            ServerSignalOff.setVisible(false);
            ServerSignalOn.setVisible(true);
        } else {
            ServerSignalOff.setVisible(true);
            ServerSignalOn.setVisible(false);
        }
    }

    public void focusInput(Scene scene) {
        scene.setOnMousePressed(event -> {
            if (!IpInput.equals(event.getSource())) {
                IpInput.getParent().requestFocus();
            }
        });
    }

}
