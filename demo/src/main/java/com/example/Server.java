package com.example;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.example.Controllers.ScreenController;
import com.example.Controllers.ViewController;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;




public class Server implements Runnable {
    private static ServerSocket socket;
    private static Socket connection;
    public Object lock;

    private static BufferedInputStream input;
    private static BufferedOutputStream output;

    public Server(Object lock) {
        this.lock = lock;
    }

    public void run() {
        try {
            socket = new ServerSocket(8080);
            
            // Continously listens to connetion event after disconnection
            //while (true) {
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
            //}
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



    private BufferedImage getScreenFrom() {
        BufferedImage image = null;
        byte[] sizeAr = new byte[4];
        
        try {
            input.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
            byte[] imageAr = new byte[size];
            input.read(imageAr);

            image = ImageIO.read(new ByteArrayInputStream(imageAr));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }



    public void sendKeyEvent(Socket socket, int key) {
        try {
            OutputStreamWriter input = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(input);

            bufferedWriter.write(key);
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void close() {
        if (socket.isClosed()) {
            return;
        } else {
            try {
                socket.close();
                System.out.println("Closing Server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
