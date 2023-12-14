package com.example;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
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
    private ScreenController screenController = new ScreenController();
    private FXMLLoader loader;

    public void run() {
        try {
            socket = new ServerSocket(8080);
            
            // Continously listens to connetion event after disconnection
            //while (true) {
            try {

                connection = socket.accept();
                System.out.println("Waiting Acception");
                // Permission for connection
                screenController.activate("permission");
                synchronized(this) {
                    wait();
                } 
                
                screenController.activate("view");
                loader = screenController.getLoader();
                ViewController view = (ViewController) loader.getController();

                // Begin recieving Images
                while (true) {
                    BufferedImage screen = getScreenFrom(connection);
                    WritableImage image = SwingFXUtils.toFXImage(screen, null);

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

    public static Socket getSocket() {
        return connection;
    }

    public InetAddress getAddress() {
        return socket.getInetAddress();
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
