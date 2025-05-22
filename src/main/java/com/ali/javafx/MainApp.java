package com.ali.javafx;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage  primaryStage) {
        LoginScreen.showLogin(primaryStage, username -> {
            AddressBook addressBook = new AddressBook(username);
            try {
                addressBook.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
