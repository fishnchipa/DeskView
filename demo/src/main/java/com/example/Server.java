package com.example;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.example.Controllers.ScreenController;
import com.example.Controllers.ViewController;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.WritableImage;




public class Server implements Runnable {
    private static ServerSocket socket;
    private static Socket connection;
    public Object lock;

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
    

                // Permission for connection
                System.out.println("Waiting Acception");
                ScreenController.activate("permission", this);
                System.out.println(connection.isClosed());
                synchronized(this) {
                    this.wait();
                }
                System.out.println(connection.isClosed());

                ViewController view = ScreenController.getController("view");
                while (true) {
             
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    // WritableImage image = SwingFXUtils.toFXImage(screen, null);
                    // view.setScreen(image);
                    System.out.println(reader.readLine());
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

    public static Socket getSocket() {
        return connection;
    }



    public BufferedImage getScreenFrom(Socket socket) {
        BufferedImage image = null;
        
        try {
            BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
            image = ImageIO.read(input);
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
