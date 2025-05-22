package com.ali.javafx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.*;

import java.util.function.Consumer;

public class LoginScreen {

    public static void showLogin(Stage stage, Consumer<String> onLoginSuccess) {
        Label titleLabel = new Label("Address Book User Screen");
        titleLabel.setFont(Font.font("Arial", 64));
        titleLabel.getStyleClass().add("title-label");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");

        Label messageLabel = new Label();

        HBox buttonBox = new HBox(10, loginBtn, registerBtn);
        buttonBox.setStyle("-fx-alignment: center;");
        VBox root = new VBox(10,titleLabel, usernameField, passwordField, buttonBox, messageLabel);
        root.setPadding(new Insets(20));

        loginBtn.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please fill username and password!");
                messageLabel.setTextFill(Color.RED);
                return;
            }
            if (authenticate(user, pass)) {
                onLoginSuccess.accept(user);
            } else {
                messageLabel.setText("Invalid username or password!");
                messageLabel.setTextFill(Color.RED);
            }
        });

        registerBtn.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please fill username and password!");
                messageLabel.setTextFill(Color.RED);
                return;
            }
            if (registerUser(user, pass)) {
                messageLabel.setText("Registration successful! You can login now.");
                messageLabel.setTextFill(Color.GREEN);
            } else {
                messageLabel.setText("User already exists!.");
                messageLabel.setTextFill(Color.RED);
            }
        });


        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add(LoginScreen.class.getResource("/login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login");
        Image icon = new Image(LoginScreen.class.getResourceAsStream("/address.png"));
        stage.getIcons().add(icon);
        stage.show();
    }

    private static boolean authenticate(String username, String password) {
        File file = new File("users.txt");
        if (!file.exists()) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("--");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean registerUser(String username, String password) {
        File file = new File("users.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("--");
                    if (parts.length == 2 && parts[0].equals(username)) {
                        return false;
                    }
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(username + "--" + password);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
