package com.example;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;


public class DummySocket extends Socket {
    private File image;
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
        };
    }

    public int output() {
        return keyPressed;
    }

}
