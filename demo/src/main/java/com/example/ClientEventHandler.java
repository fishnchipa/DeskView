package com.example;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;




public class ClientEventHandler implements Runnable {

    private Client client;
    private Robot robot;

    public ClientEventHandler(Client client) {
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
                if (key < 1024 ) {

                    // Keyboard Event
                    robot.keyPress(key);
                    robot.keyRelease(key);

                    System.out.println((char) key);
                } else {

                    // Mouse Event
                    if (key == MouseKey.PrimaryMousePress) {
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        System.out.println("Primary Mouse Pressed");

                    } else if (key == MouseKey.SecondaryMousePress) {
                        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                        System.out.println("Secondary Mouse Pressed");

                    } else if (key == MouseKey.MiddleMousePress) {
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        System.out.println("Middle Mouse Pressed");

                    } else if (key == MouseKey.PrimaryMouseRelease) {
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        System.out.println("Primary Mouse Released");

                    } else if (key == MouseKey.SecondaryMouseRelease) {
                        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                        System.out.println("Primary Mouse released");

                    } else if (key == MouseKey.MiddleMouseRelease) {
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        System.out.println("Primary Mouse released");

                    } else {
                        int x = key - MouseKey.ScreenOffset;
                        int y = client.receiveEvent() - MouseKey.ScreenOffset;
                        System.out.println("Moused Move to x: " + x + " y: " + y);
                        robot.mouseMove(x, y - 40);
                    }
                }
                System.out.println("Key pressed: " + key);
            } catch (IOException e) {

            }
        }
    }
}
