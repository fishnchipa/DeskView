package com.example.Controllers;


import java.awt.Dimension;
import java.awt.Toolkit;

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
    private double yOffsetScale;
    private double xOffsetScale;
    private Dimension clientScreenSize;
    


    public ViewController(Dimension clientScreenSize) {
        this.clientScreenSize = clientScreenSize;
    }

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

        ScreenView.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                yOffsetScale = clientScreenSize.getHeight() / ScreenView.getFitHeight(); 
                xOffsetScale = clientScreenSize.getWidth() / ScreenView.getFitWidth();

                int x = (int) (event.getX()*xOffsetScale + MouseKey.ScreenOffset);
                int y = (int) (event.getY()*yOffsetScale + MouseKey.ScreenOffset);
                Server.sendEvent(x);
                Server.sendEvent(y);
                System.out.println("Mouse moved to x: " + (event.getX()*yOffsetScale)+ " y: " + (event.getX()*yOffsetScale));

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

        ScreenView.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                yOffsetScale = clientScreenSize.getHeight() / ScreenView.getFitHeight(); 
                xOffsetScale = clientScreenSize.getWidth() / ScreenView.getFitWidth();

                int x = (int) (event.getX()*xOffsetScale + MouseKey.ScreenOffset);
                int y = (int) (event.getY()*yOffsetScale + MouseKey.ScreenOffset);

                try {
                    Thread.sleep(200);
                    Server.sendEvent(x);
                    Server.sendEvent(y);
                    System.out.println("Mouse moved to x: " + (event.getX()*yOffsetScale)+ " y: " + (event.getX()*yOffsetScale));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ScreenView.setOnMouseReleased(new EventHandler<MouseEvent>() {

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
