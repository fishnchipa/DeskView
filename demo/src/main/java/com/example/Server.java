package com.example;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;


import javax.imageio.ImageIO;

import com.example.Controllers.ScreenController;
import com.example.Controllers.UserController;
import com.example.Controllers.ViewController;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;





public class Server implements Runnable {
    private static ServerSocket socket;
    private static Socket connection;
    private static BufferedInputStream input;
    private static BufferedOutputStream output;

    public void run() {
        try {
            socket = new ServerSocket(8080);
            
            // Continously listens to connetion event after disconnection
            while (!socket.isClosed()) {
                try {
                    connection = socket.accept();
                    
                    input = new BufferedInputStream(connection.getInputStream());
                    output = new BufferedOutputStream(connection.getOutputStream());

                    // Permission for connection
                    System.out.println("Waiting Acception");
                    ScreenController.activate("permission", this);

                    synchronized(this) {
                        this.wait();
                    }
                
                    ViewController view = ScreenController.getController("view");

                    while (true) {
                        BufferedImage screen = getScreenFrom();

                        Image image = SwingFXUtils.toFXImage(screen, null);
                        view.setScreen(image);
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Unable to accept");
                }
            }
        } catch (IOException e) {
            System.out.println("Server not responding");
            UserController controller = (UserController) ScreenController.getController("app-start");
            controller.setServerStatus(false);
        }
    }

    private BufferedImage getScreenFrom() {
        BufferedImage image = null;
        byte[] sizeAr = new byte[4];
        
        try {
            input.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
            byte[] imageAr = new byte[size];
            
            for (int i = 0; i < size; i++) {
                imageAr[i] = (byte) (input.read() - 127);
            }

            ByteArrayInputStream arrayInput = new ByteArrayInputStream(imageAr);
            image = ImageIO.read(arrayInput);

            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void sendEvent(int key) {
        byte[] keyArray =  ByteBuffer.allocate(4).putInt(key).array();

        try {
            output.write(keyArray);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BufferedInputStream getInputStream() {
        return input;
    }

    public static BufferedOutputStream getOutputStream() {
        return output;
    }

}
