package org.example.demo;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.Random;

public class onetimepad extends Application {


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("one time pad");

        Label inputLabel = new Label("enter text");
        TextField inputField = new TextField();

        Label keyLabel = new Label("generated key");
        TextField keyField = new TextField();
        keyField.setEditable(false);

        Button generateKeyButton = new Button("generate key");
        Button encryptButton = new Button("encrypt");
        Button decryptButton = new Button("decrypt");

        Label outputLabel = new Label("result");
        TextField outputField = new TextField();
        outputField.setEditable(false);

        generateKeyButton.setOnAction(e -> {
            String text = inputField.getText();
            String key = generate_key(text, 7, 11, 23);
            keyField.setText(key);
        });

        encryptButton.setOnAction(e -> {
            String text = inputField.getText();
            String key = keyField.getText();
            if (!key.isEmpty()) {
                outputField.setText(encrypt(text, key));
            } else {
                outputField.setText("generate the key first");
            }
        });

        decryptButton.setOnAction(e -> {
            String text = outputField.getText();
            String key = keyField.getText();
            if (!key.isEmpty()) {
                outputField.setText(decrypt(text, key));
            } else {
                outputField.setText("generate the key first");
            }
        });


        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        HBox buttonLayout = new HBox(15, generateKeyButton, encryptButton, decryptButton);
        buttonLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                inputLabel, inputField,
                buttonLayout,
                outputLabel, outputField
        );

        Scene scene = new Scene(layout, 350, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private String generate_key(String text, int seed, int a, int b) {
        StringBuilder key = new StringBuilder();
        int x = seed;
        for (int i = 0; i < text.length(); i++) {

            char letter = text.charAt(i);
            if (Character.isLetter(letter)) {
                char generatedChar;
                if (Character.isUpperCase(letter)) {
                    generatedChar = (char) ('A' + x);
                } else {
                    generatedChar = (char) ('a' + x);
                }
                key.append(generatedChar);
            } else {
                key.append(letter);
            }
            x = (a * x + b) % 26;
        }
        System.out.println(key);
        return key.toString();
    }


    private String encrypt(String plaintext, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char letter = plaintext.charAt(i);
            char keyChar = key.charAt(i);

            if (Character.isLetter(letter)) {
                char base = Character.isUpperCase(letter) ? 'A' : 'a';
                char keyBase = Character.isUpperCase(keyChar) ? 'A' : 'a';
                char encryptedChar = (char) ((letter - base + (keyChar - keyBase)) % 26 + base);
                result.append(encryptedChar);
            } else {
                result.append(letter);
            }
        }
        return result.toString();
    }

    private String decrypt(String ciphertext, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char letter = ciphertext.charAt(i);
            char keyChar = key.charAt(i);

            if (Character.isLetter(letter)) {
                char base = Character.isUpperCase(letter) ? 'A' : 'a';
                char keyBase = Character.isUpperCase(keyChar) ? 'A' : 'a';
                char decryptedChar = (char) ((letter - base - (keyChar - keyBase) + 26) % 26 + base);
                result.append(decryptedChar);
            } else {
                result.append(letter);
            }
        }
        return result.toString();
    }


    public static void main(String[] args) {
        launch(args);

    }
}