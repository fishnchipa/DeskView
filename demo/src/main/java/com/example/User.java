package com.example;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.Controllers.ScreenController;
import com.example.Controllers.UserController;
import com.example.Controllers.ViewController;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

public class User extends Application{

    public static Server server;


    @Override
    public void start(Stage primaryStage)  {   
        try {

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/app-start.fxml"));
            Parent root = loader1.load();
            Scene scene = new Scene(root);

            ScreenController screenController = new ScreenController(scene);
            screenController.addScreen("app-start", loader1);

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view.fxml"));
            screenController.addScreen("view", loader2);

            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/permission.fxml"));
            screenController.addScreen("permission", loader3);

            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/capture.fxml"));
            screenController.addScreen("capture", loader4);

            // Connection Input unfocused when pressed out side of area
            UserController controller = ScreenController.getController("app-start");
            controller.focusInput(scene);

            server = new Server();
            Thread serverThread = new Thread(server, "Server Thread");
            serverThread.start();

            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        
        launch();
        

    }

}
