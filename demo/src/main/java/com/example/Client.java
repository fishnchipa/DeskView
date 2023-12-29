package com.example;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
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

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Screen;

public class Client {
    private InputStream input;
    private OutputStream output;
    private Socket socket;
    private int resizedHeight = 1080;
    private int resizedWidth = 1920;


    public void connectToServer(String ip) { 
        try {
            socket = new Socket(ip, 8080);
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



    public void createScreenCaptureThread() {
        String quality = DataHandler.getData("quality");

        if (quality.equals("high")) {
            resizedHeight = 1080;
            resizedWidth = 1920;
        } else if (quality.equals("medium")) {
            resizedHeight = 1080;
            resizedWidth = 1440;
        } else if (quality.equals("low")) {
            resizedHeight = 720;
            resizedWidth = 1280;
        }
        
        try { 
    
            Robot robot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenBounds = new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());

            while (socket.isConnected()) {
                BufferedImage image = robot.createScreenCapture(screenBounds);
    
                BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2d = resizedImage.createGraphics();
                graphics2d.drawImage(image, 0, 0, resizedWidth, resizedHeight, null);
                graphics2d.dispose();
    
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "gif", byteArrayOutputStream);
    
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                byte[] byteImage = byteArrayOutputStream.toByteArray();
    
                output.write(size);
    
                for (int i = 0; i < byteArrayOutputStream.size(); i++) {
                    output.write(byteImage[i] + 127);
                    output.flush();
                }
                System.out.println("Image sent");
            }
 
        } catch (AWTException | IOException e) {
            e.printStackTrace();
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
