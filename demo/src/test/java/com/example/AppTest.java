package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


import javax.imageio.ImageIO;


import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */

    
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }

    @Test 
    public void establishedSuccessfulServerConnectionToClient() throws IOException {
        Server server = new Server();

        try (Socket ableToConnect = new Socket("localhost", 8080)) {
            assertTrue("Connection Successfully Established", ableToConnect.isConnected());
            ableToConnect.close();
        } 
        server.close();
    }

    @Test 
    public void establishedSuccessfulServerConnectionByIp() throws IOException {
        Server server = new Server();
        String ip = server.getAddress().getHostAddress();
        
        try (Socket ableToConnect = new Socket(ip, 8080)) {
            assertTrue("Connection Successfully Established", ableToConnect.isConnected());
            ableToConnect.close();
        } 
        server.close();
    }

    @Test
    public void establishedFailedServerConnectionToCLient() {
        Server server = new Server();
        server.close();

        try (Socket ableToConnect = new Socket("localhost", 8080)) {
            fail("Connection cannot be established");
        } catch (Exception e) {
            assertEquals("Connection refused", e.getMessage());
        }
    }

    public boolean compareImage(BufferedImage x, BufferedImage y) {
        if (x.getWidth() == y.getWidth() && x.getHeight() == y.getHeight()) {
            for (int i = 0; i < x.getWidth(); i++) {
                for (int j = 0; j < x.getHeight(); j++) {
                    if (x.getRGB(i, j) != y.getRGB(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Test 
    public void receiveImageOutputFromClient() throws IOException {
        Server server = new Server();

        File imagePath = new File("src/test/resources/image1.png");
        BufferedImage clientImage = ImageIO.read(imagePath);
        
        DummySocket socket = new DummySocket(imagePath);
        BufferedImage serverImage = server.getScreenFrom(socket);
        server.close();

        assertTrue("Image successfully sent", compareImage(clientImage, serverImage));
    }

    @Ignore 
    public void sendKeyEventToClient() {
        Server server = new Server();
        server.close();


    }

}
