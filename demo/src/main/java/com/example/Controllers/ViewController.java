package com.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewController {
    
    @FXML 
    private ImageView screen;


    public void setScreen(Image image) {
        screen.setImage(image);
    }

}
