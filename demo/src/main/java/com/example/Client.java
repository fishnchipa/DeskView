package com.example;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


import javax.imageio.ImageIO;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Client {

    /*
     * Checks if connection is valid
     */
    public Socket connectToServer(String ip) { 
        try {
            Socket socket = new Socket(ip, 8080);
            return socket;
        } catch (IOException e) {
            System.exit(1);
        } 
        return null;
    }

    public boolean receivePermission(Socket socket) {
        try (InputStreamReader reader = new InputStreamReader(socket.getInputStream())) {
            if (reader.read() == 1) {
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
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            while (true) {
                Robot r = new Robot();
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));

                ImageIO.write(screen, "png", os);
                os.flush();
                System.out.print("Frame Sent");
            }
        } catch (AWTException | IOException e) {

            e.printStackTrace();
        }
    }
}
