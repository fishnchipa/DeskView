package com.example.Controllers;

import java.io.IOException;
import java.util.HashMap;

import com.example.Server;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ScreenController {
    private static HashMap<String, FXMLLoader> screenMap = new HashMap<>();
    private static Scene main;
    private static Stage primaryStage;

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

    public static void activate(String name, Server server) {
        FXMLLoader loader = screenMap.get(name);
        PermissionController permissionController = new PermissionController(server);
        try {
            loader.setController(permissionController);
            Parent root = loader.load();
            
            main.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getController(String name) {
        FXMLLoader loader = screenMap.get(name);
        return loader.getController();
    }

    public static Scene getScene() {
        return main;
    }

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void setFullScreen() {
        primaryStage.setFullScreen(true);
    }

} 
