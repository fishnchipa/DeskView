package com.example;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Client {

    /*
     * Checks if connection is valid
     */
    public Socket connectToServer(String ip) { 
        try {
            // Server.close();
            Socket socket = new Socket(ip, 8080);
            return socket;
        } catch (IOException e) {
            System.out.println(ip + "Io exception");
            return null;
        } 
    }

    public boolean receivePermission(Socket socket) {
        try (InputStreamReader reader = new InputStreamReader(socket.getInputStream())) {
            if (reader.read() == 1) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public void sendScreen(Socket socket) {
        try {
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());

            Robot r = new Robot();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));

            ImageIO.write(screen, "png", os);
            os.flush();

        } catch (AWTException | IOException e) {

            e.printStackTrace();
        }
    }
}
