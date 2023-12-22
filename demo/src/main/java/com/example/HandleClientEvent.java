package com.example;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import javafx.scene.input.KeyCode;


public class HandleClientEvent implements Runnable {

    private Client client;
    private Robot robot;

    public HandleClientEvent(Client client) {
        this.client = client;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int key = client.receiveEvent();
                if (key > 3 ) {
                    // Keyboard Event

                    robot.keyPress(key);
                    robot.keyRelease(key);
                } else {
                    // Mouse Event
                    int x = client.receiveEvent();
                    int y = client.receiveEvent();

                    robot.mouseMove(x, y);
                    robot.mousePress(key);
                    robot.mouseRelease(key);
                }
                System.out.println("Key pressed: " + key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
