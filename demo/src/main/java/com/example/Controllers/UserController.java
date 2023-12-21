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
import com.example.Server;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private Text connectionId;

    private Client client;



    public void initialize() {

        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"),12345);
            connectionId.setText(datagramSocket.getLocalAddress().getHostAddress());
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void submit(ActionEvent event) {
        client = new Client();

        System.out.println("Attemping to Connect to Server");
        client.connectToServer(ipConnection.getText());

        // Waiting for Server accept
        System.out.println("Waiting for Server");

        if(client.receivePermission()) {
            System.out.println("Successfully Connected to Server");

            ScreenController.activate("capture");
            System.out.println("sending images");
            
            while (true) {
                client.sendScreen();
            }

        } 
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void test() {
        System.out.println("Exiting");
        System.exit(0);
    }
}
