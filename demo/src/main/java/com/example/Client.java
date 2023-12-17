package com.example;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;


import javax.imageio.ImageIO;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Client {
    private BufferedInputStream input;
    private BufferedOutputStream output;

    public Socket connectToServer(String ip) { 
        try {
            Socket socket = new Socket(ip, 8080);
            input = new BufferedInputStream(socket.getInputStream());
            output = new BufferedOutputStream(socket.getOutputStream());
            return socket;
        } catch (IOException e) {
            System.exit(1);
        } 
        return null;
    }

    public boolean receivePermission(Socket socket) {
        try {
            if (input.read() == 1) {
                System.out.println("Successful Connection");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Unsuccessful Connection");
            return false;
        }
        return false;
    }

    public void sendScreen(Socket socket) {
        try {
    
            while (true) {
                Robot r = new Robot();
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));

                ImageIO.write(screen, "png", output);
                output.flush();
                System.out.print("Frame Sent");
            }
        } catch (AWTException | IOException e) {

            e.printStackTrace();
        }
    }

    public void write(int data) {
        try {
            output.write(data);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
