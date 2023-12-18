package com.example;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Client {
    private BufferedInputStream input;
    private BufferedOutputStream output;

    private Socket socket;

    public void connectToServer(String ip) { 
        try {
            socket = new Socket(ip, 8080);
            input = new BufferedInputStream(socket.getInputStream());
            output = new BufferedOutputStream(socket.getOutputStream());

            System.out.println("Processing connection" + " " + ip);

        } catch (IOException e) {
            System.out.println("Failed to connect");
            System.exit(1);
        } 
    }

    public boolean receivePermission() {
        try {
            System.out.println("hi");
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
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));

                ImageIO.write(screen, "png", byteArrayOutputStream);
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

                output.write(size);
                output.write(byteArrayOutputStream.toByteArray());
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
