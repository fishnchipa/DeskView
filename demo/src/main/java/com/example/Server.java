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

import com.example.Controllers.ServerController;

public class Server {
    private ServerSocket socket;




    public void start() {
        try {
            socket = new ServerSocket(1234);
            
            // Continously listens to connetion event after disconnection
            while (true) {
                Socket connection = socket.accept();

                while (true) {
                    BufferedImage screen = getScreenFrom(connection);
                    
                }


            }

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
        try {
            OutputStreamWriter input = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(input);

            bufferedWriter.write(key);
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
