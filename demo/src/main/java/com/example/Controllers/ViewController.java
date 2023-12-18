package com.example.Controllers;

import java.awt.AWTException;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;

public class ViewController {
    
    @FXML 
    private ImageView screen;


    public void setScreen(Image image) {
        screen.setImage(image);
    }

}
