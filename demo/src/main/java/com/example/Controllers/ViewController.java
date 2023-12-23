package com.example.Controllers;


import com.example.MouseKey;
import com.example.Server;
import java.awt.event.InputEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class ViewController {
    
    @FXML 
    private ImageView screen;

    public void initialize() {
        Scene scene = ScreenController.getScene();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        
            @Override
            public void handle(KeyEvent event) {
                Server.sendEvent(event.getCode().getCode());
                System.out.println("Key Sent: " + event.getText());
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                String button = event.getButton().name();
                if (button.equals("PRIMARY")) {
                    Server.sendEvent(MouseKey.PrimaryMousePress);
                } else if (button.equals("SECONDARY")) {
                    Server.sendEvent(MouseKey.SecondaryMousePress);
                } else {
                    Server.sendEvent(MouseKey.MiddleMousePress);
                }
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    Thread.sleep(200);
                    Server.sendEvent((int) event.getScreenX() + MouseKey.ScreenOffset);
                    Server.sendEvent((int) event.getScreenY() + MouseKey.ScreenOffset);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                String button = event.getButton().name();
                if (button.equals("PRIMARY")) {
                    Server.sendEvent(MouseKey.PrimaryMouseRelease);
                } else if (button.equals("SECONDARY")) {
                    Server.sendEvent(MouseKey.SecondaryMouseRelease);
                } else {
                    Server.sendEvent(MouseKey.MiddleMouseRelease);
                }
            }
            
        });
    }


    public void setScreen(Image image) {
        screen.setImage(image);
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

}
