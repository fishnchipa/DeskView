package com.example;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
    private FXMLLoader loader;

    public void run() {
        try {
            socket = new ServerSocket(1234);
            
            // Continously listens to connetion event after disconnection
            while (true) {
                try {

                    connection = socket.accept();

                    // Permission for connection
                    ScreenController.activate("permission");
                    
                    wait();

                    ScreenController.activate("view");
                    ViewController view = (ViewController) loader.getController();

                    // Begin recieving Images
                    //while (true) {
                        BufferedImage screen = getScreenFrom(connection);
                        WritableImage image = SwingFXUtils.toFXImage(screen, null);

                        view.setScreen(image);
                        

                    //}
                } catch (IOException | InterruptedException e) {
                    System.out.println("Invalid Ip");
                }
            }
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
            image = ImageIO.read(socket.getInputStream());
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
