package com.example.Controllers;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.function.UnaryOperator;

import javax.imageio.ImageIO;
import javax.swing.text.View;

import com.example.Client;
import com.example.ClientEventHandler;
import com.example.DataHandler;
import com.example.Server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserController {
    

    @FXML
    private TextField IpInput;

    @FXML 
    private Text ConnectionId;

    @FXML 
    private AnchorPane ConnectionInput;

    @FXML 
    private Label ConnectionLabel;

    @FXML
    private Pane ServerSignalOn;

    @FXML
    private Pane ServerSignalOff;

    @FXML
    private HBox RemoteAccess;

    @FXML
    private ScrollPane Documentation;

    @FXML 
    private VBox DocumentationBackground;

    @FXML 
    private Text DocumentationText;

    @FXML 
    private Text GUIText;

    @FXML 
    private Text ProtocolText;

    @FXML 
    private AnchorPane Settings;

    @FXML 
    private AnchorPane SettingsBackground;

    @FXML 
    private AnchorPane SettingsGeneral;

    @FXML 
    private AnchorPane SettingsOutput;

    @FXML 
    private AnchorPane SettingsConnection;

    @FXML 
    private ComboBox<String> ThemeBox;

    @FXML 
    private ComboBox<String> LanguageBox;
    
    @FXML 
    private ComboBox<String> QualityBox;

    @FXML
    private TextField PortBox;

    @FXML 
    private HBox Header;

    @FXML 
    private HBox Body;

    @FXML 
    private HBox Footer;

    @FXML 
    private HBox Permission;

    @FXML 
    private Label ResetSave;

    private Client client;
    private Server server;
    private ObservableList<String> themes = null;
    private ObservableList<String> languages = null;
    private ObservableList<String> qualities = null;


    public void initialize() {
        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"),12345);
            ConnectionId.setText(datagramSocket.getLocalAddress().getHostAddress());
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }



    public void submit(ActionEvent event) {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                client = new Client();

                System.out.println("Attemping to Connect to Server");
                client.connectToServer(IpInput.getText());

                // Waiting for Server accept
                System.out.println("Waiting for Server");

                // Send Screen Size 
                client.sendScreenDetails();

                if(client.receivePermission()) {
                    System.out.println("Successfully Connected to Server");

                    ScreenController.activate("capture");
                    System.out.println("sending images");
                    
                    // Thread for sending events
                    ClientEventHandler clientHandler = new ClientEventHandler(client);
                    Thread clientHandlerThread = new Thread(clientHandler);
                    clientHandlerThread.start();

                    // Sending screen capture
                    client.screenCapture();
                } 
            }
        };

        Thread newThread = new Thread(run);
        newThread.start();
    }




    public void showPermission(Server server) {
        this.server = server;
        disableAll();
        Permission.setVisible(true);
    }




    public void acceptConnection(ActionEvent event) {
        System.out.println("Accepted");
        System.out.println("Loading Screen");
        ScreenController.activate("app-view");
        BufferedOutputStream writer = Server.getOutputStream();
        
        // Sending confirmation to Client
        try {
            writer.write(1);
            writer.flush();
            System.out.println("Confirmation Sent");

        } catch (IOException e) {
            System.out.println("Acception error");
            e.printStackTrace();
        }
        synchronized(server) {
            server.notify();
        }
    }




    public void declineConnection(ActionEvent event) {
        BufferedOutputStream writer = Server.getOutputStream();
        Permission.setVisible(false);
        enableAll();
        try {
            writer.write(0);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setServerStatus(boolean status) {
        if (status) {
            ServerSignalOff.setVisible(false);
            ServerSignalOn.setVisible(true);
        } else {
            ServerSignalOff.setVisible(true);
            ServerSignalOn.setVisible(false);
        }
    }




    public void focusInput(Scene scene) {
        scene.setOnMousePressed(event -> {
            if (!IpInput.equals(event.getSource())) {
                IpInput.getParent().requestFocus();
            }
        });
    }




    public void remoteAccessPage(ActionEvent event) {
        RemoteAccess.setVisible(true);
        Documentation.setVisible(false);
        ConnectionInput.setVisible(true);
        Settings.setVisible(false);
    }




    public void documentationPage(ActionEvent event) {
        RemoteAccess.setVisible(false);
        Documentation.setVisible(true);
        ConnectionInput.setVisible(false);
        Settings.setVisible(false);

        DocumentationBackground.prefHeightProperty().bind(Documentation.heightProperty());
        DocumentationBackground.prefWidthProperty().bind(Documentation.widthProperty());

        DocumentationText.wrappingWidthProperty().bind(Documentation.widthProperty().subtract(20));
        GUIText.wrappingWidthProperty().bind(Documentation.widthProperty().subtract(20));
        ProtocolText.wrappingWidthProperty().bind(Documentation.widthProperty().subtract(20));
    }




    public void settingPage(ActionEvent event) {
        RemoteAccess.setVisible(false);
        Documentation.setVisible(false);
        ConnectionInput.setVisible(false);
        Settings.setVisible(true);

        SettingsBackground.prefHeightProperty().bind(Settings.heightProperty().subtract(20));
        SettingsBackground.prefWidthProperty().bind(Settings.widthProperty().subtract(20));

        UnaryOperator <Change> numberFilter = change -> {
            String portText = change.getText();
            String newText = change.getControlNewText();

            if (portText.isEmpty())  {
                return change;
            }
      
            if ((portText.matches("\\d+")) && newText.length() < 6) {
                return change;
            }
            return null;
        };

        TextFormatter<String> formatter = new TextFormatter<>(numberFilter);
        PortBox.setTextFormatter(formatter);

        if (themes == null) {
            themes = FXCollections.observableArrayList("Dark", "Light");
            ThemeBox.setItems(themes);
            ThemeBox.setPromptText(DataHandler.getData("theme"));
        }

        if (languages == null) {
            languages = FXCollections.observableArrayList("English");
            LanguageBox.setItems(languages);
            LanguageBox.setPromptText(DataHandler.getData("language"));
        }

        if (qualities == null) {
            qualities = FXCollections.observableArrayList("High", "Medium", "Low");
            QualityBox.setItems(qualities);
            QualityBox.setPromptText(DataHandler.getData("quality"));
        }

        if (PortBox.getPromptText().isEmpty()) {
            PortBox.setPromptText(DataHandler.getData("port"));
        }
    }



    public void save(ActionEvent event) {
        String theme = ThemeBox.getValue();
        String language = LanguageBox.getValue();
        String quality = QualityBox.getValue();
        String port = PortBox.getText();

        DataHandler.setData(theme, language, quality, port);
        System.out.println("Data saved");
        ResetSave.setVisible(true);
    }



    private void disableAll() {
        Header.setDisable(true);
        Body.setDisable(true);
        Footer.setDisable(true);

    }

    private void enableAll() {
        Header.setDisable(false);
        Body.setDisable(false);
        Footer.setDisable(false);
    }

    public void exitPage(ActionEvent event) {
        System.exit(0);
    }
}
