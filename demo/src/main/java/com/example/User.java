package com.example;

import java.io.IOException;
import java.io.InputStream;

import com.example.Controllers.ScreenController;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class User extends Application{

    @Override
    public void start(Stage primaryStage)  {
        
        Parent root;

        try {
            
            root = FXMLLoader.load(getClass().getResource("/start.fxml"));
            Scene scene = new Scene(root);
            ScreenController screenController = new ScreenController(scene);
            screenController.addScreen("start", root);

            root = FXMLLoader.load(getClass().getResource("/view.fxml"));
            screenController.addScreen("view", root);

            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public static void main( String[] args )
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.start();
            }
        };
        new Thread(r).start();

        launch();
    }

}
