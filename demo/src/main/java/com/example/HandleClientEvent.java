package com.example;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;




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

                    System.out.println((char) key);
                } else {

                    // Mouse Event
                    int x = client.receiveEvent();
                    int y = client.receiveEvent();

                    robot.mouseMove(x, y);

                    if (key == 1) {
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        System.out.println("Primary Key");
                    } else if (key == 2) {
                        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                        System.out.println("Secondary Key");
                    } else {
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        System.out.println("Middle Key");
                    }
                }
                System.out.println("Key pressed: " + key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
