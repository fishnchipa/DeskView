package com.example.Controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;


public class ViewController {
    
    @FXML 
    private ImageView screen;

    public void handleEvent() {
        Scene scene = ScreenController.getScene();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());

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
