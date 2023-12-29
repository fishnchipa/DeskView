package com.example;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;


import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.Controllers.ScreenController;
import com.example.Controllers.UserController;
import com.example.Controllers.ViewController;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Screen;





public class Server implements Runnable {
    private static ServerSocket socket;
    private static Socket connection;
    private static BufferedInputStream input;
    private static BufferedOutputStream output;



    public void run() {
        try {
            int port = Integer.parseInt(DataHandler.getData("port"));
            socket = new ServerSocket(port);
            
            // Continously listens to connetion event after disconnection
            while (!socket.isClosed()) {
                try {
                    connection = socket.accept();
                    
                    input = new BufferedInputStream(connection.getInputStream());
                    output = new BufferedOutputStream(connection.getOutputStream());

                    // Permission for connection
                    System.out.println("Waiting Acception");
                    UserController userController = ScreenController.getController("app-start");
                    userController.showPermission(this);

                    // Recieve Screen Size
                    
                    Dimension clientScreen = getScreenDetails();
                    ViewController view = new ViewController(clientScreen);
                    FXMLLoader viewLoader = ScreenController.getLoader("app-view");
                    viewLoader.setController(view);
                    view.setClientScreenDetails(clientScreen);

                    synchronized(this) {
                        this.wait();
                    }

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
            System.out.println("Port is unavaliable");
            UserController controller = (UserController) ScreenController.getController("app-start");
            controller.setServerStatus(false);
        }
    }


    private Dimension getScreenDetails() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        Dimension dimension = null;
        try {
            String screenString = reader.readLine();
            String newString = screenString.substring(19, screenString.length() - 1);
            String[] sizeAr = newString.split(",");
    
            int height = Integer.parseInt(sizeAr[0].substring(6));
            int width = Integer.parseInt(sizeAr[1].substring(7));

            dimension = new Dimension(width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dimension;
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
