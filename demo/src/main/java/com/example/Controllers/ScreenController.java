package com.example.Controllers;

import java.util.HashMap;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ScreenController {
    private static HashMap<String, Parent> screenMap = new HashMap<>();
    private static Scene main;

    public ScreenController() {}

    public ScreenController(Scene scene) {
        ScreenController.main = scene;

    }

    public void addScreen(String name, Parent parent) {
        screenMap.put(name, parent);
    }

    public static void activate(String name) {
        main.setRoot(screenMap.get(name));
    }

    public void permission() {
        
    }
}
