package com.example;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;

public class Client {
    private InputStream input;
    private OutputStream output;

    private Socket socket;

    public void connectToServer(String ip) { 
        try {
            socket = new Socket(ip, 1234);
            input = socket.getInputStream();
            output = socket.getOutputStream();

            System.out.println("Processing connection" + " " + ip);

        } catch (IOException e) {
            System.out.println("Failed to connect");
            System.exit(1);
        } 
    }

    public boolean receivePermission() {
        try {
            if (input.read() == 1) {
                System.out.println("Successful Connection");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Unsuccessful Connection");
            return false;
        }
        System.out.println("Unsuccessful Connection");
        return false;
    }

    public void sendScreen() {
        try {
            Robot r = new Robot();
            // Rectangle2D screenBounds = new Rectangle2D(0, 0, 800, 600);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            while (true) {
                WritableImage screen = r.getScreenCapture(null, screenBounds, true);
                BufferedImage image = SwingFXUtils.fromFXImage(screen, null);
    
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", byteArrayOutputStream);
    
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                byte[] byteImage = byteArrayOutputStream.toByteArray();
    
                output.write(size);
    
                for (int i = 0; i < byteArrayOutputStream.size(); i++) {
                    output.write(byteImage[i] + 127);
                    output.flush();
                }
                
            }

        } catch (IOException e) {
            System.out.println("Server Closed Connection");
            System.exit(0);
        }
    }

    public int receiveEvent() throws IOException {
        return input.read();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }
}
