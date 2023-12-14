package com.example.Controllers;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ScreenController {
    private static HashMap<String, String> screenMap = new HashMap<>();
    private static Scene main;
    private static FXMLLoader loader;

    public ScreenController() {}

    public ScreenController(FXMLLoader loader, Scene main) {
        ScreenController.loader = loader;
        ScreenController.main = main;

    }

    public void addScreen(String name, String root) {
        screenMap.put(name, root);
    }

    public void activate(String name) {
        loader.setLocation(getClass().getResource(screenMap.get(name)));
        try {
            Parent root = loader.load();
            main.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public FXMLLoader getLoader() {
        return loader;
    }    
}
