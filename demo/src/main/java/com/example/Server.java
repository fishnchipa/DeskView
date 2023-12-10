package com.example;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class Server {
    private ServerSocket socket;
    public Server() {
        try {
            socket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
      
    }

    public void close() {
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
