package com.example;

import java.io.IOException;

import com.example.Controllers.ScreenController;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
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

            root = FXMLLoader.load(getClass().getResource("/permission.fxml"));
            screenController.addScreen("permission", root);
            
            root = FXMLLoader.load(getClass().getResource("/capture.fxml"));
            screenController.addScreen("capture", root);

            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main( String[] args )
    {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();

        launch();
    }

}
