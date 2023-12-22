package com.example.Controllers;


import com.example.Server;

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
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Server.sendEvent(event.getButton().ordinal());
                Server.sendEvent((int) event.getScreenX());
                Server.sendEvent((int) event.getScreenY());
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
