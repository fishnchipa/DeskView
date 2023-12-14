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
        FXMLLoader loader = new FXMLLoader();
        try {
            root = FXMLLoader.load(getClass().getResource("/start.fxml"));
            Scene main = new Scene(root);
            ScreenController screenController = new ScreenController(loader, main);

            screenController.addScreen("start", "/start.fxml");
            screenController.addScreen("view", "/view.fxml");
            screenController.addScreen("permission", "/permission.fxml");
            screenController.addScreen("capture", "/capture.fxml");

            primaryStage.setScene(main);
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
