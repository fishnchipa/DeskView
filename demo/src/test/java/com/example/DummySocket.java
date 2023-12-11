package com.example;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class DummySocket extends Socket {
    private File image;
    private List<Byte> byteList = new ArrayList<>();
    private int keyPressed;

    public DummySocket() {}

    public DummySocket(File image) {
        this.image = image;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(Files.readAllBytes(image.toPath()));
    }

    public OutputStream getOutputStream() {
        return new OutputStream() {

            @Override
            public void write(int b) {
                keyPressed = b;
            }

            @Override
            public void write(byte[] b) {
                for (int i = 0; i < b.length; i++) {
                    byteList.add(b[i]);
                }
            }

        };
    }

    public int output() {
        return keyPressed;
    }

    private byte[] toByteArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        int index = 0;
        for (byte b : byteList) {
          byteArray[index++] = b;
        }
        return byteArray;
      }

    public BufferedImage getImage() throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(toByteArray(byteList));
        BufferedImage image = ImageIO.read(is);
        return image;
    }
}
