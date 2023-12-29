package com.example.Controllers;


import com.example.MouseKey;
import com.example.Server;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class ViewController {

    @FXML 
    private AnchorPane Header;

    @FXML 
    private AnchorPane HeaderTransition;

    @FXML 
    private AnchorPane Body;

    @FXML 
    private ImageView ScreenView;

    private static Boolean isfullScreen = false;

    public void initialize() {
        ScreenView.fitWidthProperty().bind(Body.widthProperty());
        ScreenView.fitHeightProperty().bind(Body.heightProperty().subtract(50));

        Scene scene = ScreenController.getScene();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        
            @Override
            public void handle(KeyEvent event) {
                Server.sendEvent(event.getCode().getCode());
                System.out.println("Key Sent: " + event.getText());
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                int x = (int) event.getScreenX() + MouseKey.ScreenOffset;
                int y = (int) event.getScreenY() + MouseKey.ScreenOffset;
                Server.sendEvent(x);
                Server.sendEvent(y);
                System.out.println("Mouse moved to x: " + x + " y: " + y);

                String button = event.getButton().name();
                if (button.equals("PRIMARY")) {
                    Server.sendEvent(MouseKey.PrimaryMousePress);
                } else if (button.equals("SECONDARY")) {
                    Server.sendEvent(MouseKey.SecondaryMousePress);
                } else {
                    Server.sendEvent(MouseKey.MiddleMousePress);
                }
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                try {
                    int x = (int) event.getScreenX() + MouseKey.ScreenOffset;
                    int y = (int) event.getScreenY() + MouseKey.ScreenOffset;
                    Thread.sleep(200);
                    Server.sendEvent(x);
                    Server.sendEvent(y);
                    System.out.println("Mouse moved to x: " + x + " y: " + y);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                String button = event.getButton().name();
                if (button.equals("PRIMARY")) {
                    Server.sendEvent(MouseKey.PrimaryMouseRelease);
                } else if (button.equals("SECONDARY")) {
                    Server.sendEvent(MouseKey.SecondaryMouseRelease);
                } else {
                    Server.sendEvent(MouseKey.MiddleMouseRelease);
                }
            }
            
        });
    }

    public void setScreen(Image image) {
        ScreenView.setImage(image);
    }


    public void fullScreen(ActionEvent event) {
        Stage stage = (Stage) Body.getScene().getWindow();
        if (isfullScreen) {

            stage.setFullScreen(false);
            isfullScreen = false;
            
            ScreenView.fitWidthProperty().bind(Body.widthProperty());
            ScreenView.fitHeightProperty().bind(Body.heightProperty().subtract(50));
            ScreenView.setLayoutY(50);

            HeaderTransition.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {}
            });

            HeaderTransition.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {}
            });

        } else {

            stage.setFullScreen(true);
            isfullScreen = true;

            AnchorPane.clearConstraints(ScreenView);

            Rectangle2D screenBounds = Screen.getPrimary().getBounds();


            ScreenView.fitWidthProperty().unbind();
            ScreenView.fitHeightProperty().unbind();

            ScreenView.setLayoutY(0);
            ScreenView.setFitWidth(screenBounds.getWidth());
            ScreenView.setFitHeight(screenBounds.getHeight());
            Header.setLayoutY(-50);

            HeaderTransition.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Header.setLayoutY(50);
                }
                
            });

            HeaderTransition.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Header.setLayoutY(-50);
                }
            });
        }
    }



    public void exit(ActionEvent event) {
        System.exit(0);
    }

}
