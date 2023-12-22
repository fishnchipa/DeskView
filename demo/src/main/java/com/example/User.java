package com.example;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;

import com.example.Controllers.ScreenController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class User extends Application{


    @Override
    public void start(Stage primaryStage)  {
        
        try {

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/start.fxml"));
            Parent root = loader1.load();
            Scene scene = new Scene(root);

            ScreenController screenController = new ScreenController(scene);
            screenController.addScreen("start", loader1);

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view.fxml"));
            screenController.addScreen("view", loader2);

            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/permission.fxml"));
            screenController.addScreen("permission", loader3);

            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/capture.fxml"));
            screenController.addScreen("capture", loader4);

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Server server;

    public static void main( String[] args )
    {
        server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();

        launch();
    }

}
