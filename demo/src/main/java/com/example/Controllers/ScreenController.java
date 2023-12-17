package com.example.Controllers;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ScreenController {
    private static HashMap<String, FXMLLoader> screenMap = new HashMap<>();
    private static Scene main;

    public ScreenController(Scene scene) {
        ScreenController.main = scene;
    }
    
    public void addScreen(String name, FXMLLoader loader) {
        screenMap.put(name, loader);
    }

    public static void activate(String name) {
        FXMLLoader loader = screenMap.get(name);
        
        try {
            Parent root = loader.load();
            main.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void activate(String name, Object lock) {
        FXMLLoader loader = screenMap.get(name);
        PermissionController permissionController = new PermissionController(lock);
        try {
            loader.setController(permissionController);
            Parent root = loader.load();
            
            main.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

} 
