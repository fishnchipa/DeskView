package com.example;

import java.awt.Graphics2D;
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
    private int resizedWidth = 1280;
    private int resizedHeight = 720;

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
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            while (true) {
                WritableImage screen = r.getScreenCapture(null, screenBounds, false);
                BufferedImage image = SwingFXUtils.fromFXImage(screen, null);
    
                BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2d = resizedImage.createGraphics();
                graphics2d.drawImage(image, 0, 0, resizedWidth, resizedHeight, null);
                graphics2d.dispose();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "gif", byteArrayOutputStream);
    
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
        byte[] keyArray = new byte[4];
        input.read(keyArray);
        int key = ByteBuffer.wrap(keyArray).asIntBuffer().get();
        return key;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }
}
