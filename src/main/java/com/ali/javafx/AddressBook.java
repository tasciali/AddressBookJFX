package com.ali.javafx;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class AddressBook extends Application {

    private String currentUser;
    private String FILE_NAME;

    private ObservableList<String> contactList;
    private ListView<String> listView;

    public AddressBook(String username) {
        this.currentUser = username;
        this.FILE_NAME = "data/" + username + ".txt";

        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }
    @Override
    public void start(Stage primaryStage) {
        contactList = FXCollections.observableArrayList(loadContacts());

        listView = new ListView<>(contactList);

        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);

        TextField nameField = new TextField();
        nameField.setPromptText("Name surname");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone number");

        TextField emailField = new TextField();
        emailField.setPromptText("E-posta");

        Button addButton = new Button("➕ Add");
        Button editButton = new Button("✏️ Edit");
        Button deleteButton = new Button("❌ Clear");
        Button logoutButton = new Button("Logout");

        addButton.setId("add-button");
        deleteButton.setId("delete-button");
        editButton.setId("edit-button");
        logoutButton.setId("logout-button");

        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                return;
            }

            String entry = name + "--" + phone + "--" + email;
            contactList.add(entry);
            saveContacts();
            clearFields(nameField, phoneField, emailField);

            messageLabel.setText("Contact added!");
            messageLabel.setTextFill(Color.RED);
        });

        editButton.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                String entry = nameField.getText() + "--" + phoneField.getText() + "--" + emailField.getText();
                contactList.set(selectedIndex, entry);
                saveContacts();
                clearFields(nameField, phoneField, emailField);
            }
            messageLabel.setText("Successfully Organized!");
            messageLabel.setTextFill(Color.RED);
        });

        deleteButton.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                contactList.remove(selectedIndex);
                saveContacts();
                clearFields(nameField, phoneField, emailField);
            }
            messageLabel.setText("Successfully deleted!");
            messageLabel.setTextFill(Color.RED);
        });

        logoutButton.setOnAction(e -> {
            try {
                MainApp mainApp = new MainApp();
                mainApp.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String[] parts = newVal.split("--");
                if (parts.length == 3) {
                    nameField.setText(parts[0]);
                    phoneField.setText(parts[1]);
                    emailField.setText(parts[2]);
                }
            }
        });

        Label title = new Label(" Address book - " + currentUser);
        title.setFont(Font.font("Arial", 32));
        title.setTextFill(Color.DARKBLUE);

        HBox inputBox = new HBox(10, nameField, phoneField, emailField);
        HBox buttonBox = new HBox(30, addButton, editButton, deleteButton, logoutButton);
        VBox root = new VBox(10, title, listView, inputBox, buttonBox, messageLabel);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 650, 450);
        scene.getStylesheets().add(getClass().getResource("/address.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Address book application");
        primaryStage.show();
    }

    private List<String> loadContacts() {
        List<String> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
            } catch (IOException e) {
                System.out.println("Dosya okunurken hata: " + e.getMessage());
            }
        }
        return list;
    }

    private void clearFields(TextField name, TextField phone, TextField email) {
        name.clear();
        phone.clear();
        email.clear();
    }

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String contact : contactList) {
                writer.write(contact);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Dosya yazılırken hata: " + e.getMessage());
        }
    }
}
