package com.example.Controllers;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.example.Client;
import com.example.Server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

    @FXML 
    private Text connectionId;

    private Client client = new Client();

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

    public void exit(ActionEvent event) {
        System.exit(0);
    }
}
