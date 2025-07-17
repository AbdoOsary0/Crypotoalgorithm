package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.SecureRandom;

public class OneTimePadCipher extends Application {
    public static String key;
    int a = 10, b = 15, s = 5;

    public static int getRandomNumber() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(26); // Generates a number from 0 to 25
    }

    public static void GenerateKey(int length, int a, int b, int s) {
        StringBuilder keyGen = new StringBuilder();
        int x = s;
        char charKey;
        for (int i = 0; i < length; i++) {
            x = (a * (-x) + b) % 26;
            charKey = (char) ('a' + (x < 0 ? x + 26 : x));
            keyGen.append(charKey);
        }
        System.out.println(keyGen.toString());
        key = keyGen.toString();
    }

    public String encrypt(String plaintext) {
        StringBuilder cipherText = new StringBuilder();
        GenerateKey(plaintext.length(), a, b, s);
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i);
            if (Character.isLetter(plainChar)) {
                char base = Character.isUpperCase(plainChar) ? 'A' : 'a';
                int shift = Character.isUpperCase(keyChar) ? key.charAt(i) - 'A' : key.charAt(i) - 'a';
                cipherText.append((char) ((plainChar - base + shift) % 26 + base));
            } else {
                cipherText.append(plainChar);
            }
        }
        return cipherText.toString();
    }

    public String decrypt(String cipherText) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char cipherChar = cipherText.charAt(i);
            char keyChar = key.charAt(i);
            if (Character.isLetter(cipherChar)) {
                char base = Character.isUpperCase(cipherChar) ? 'A' : 'a';
                int shift = Character.isUpperCase(keyChar) ? keyChar - 'A' : keyChar - 'a';
                plaintext.append((char) ((cipherChar - base - shift + 26) % 26 + base));
            } else {
                plaintext.append(cipherChar);
            }
        }
        return plaintext.toString();
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        Label label1 = new Label("Enter Plain Text:");
        TextField textField1 = new TextField();
        textField1.setPromptText("Enter Plain Text");
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPromptText("Result will appear here...");
        textArea.setPrefHeight(80);
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");
        root.getChildren().addAll(label1, textField1, textArea, encryptButton, decryptButton);
        // Encrypt button action
        encryptButton.setOnAction((e) -> {
            if (!textField1.getText().isEmpty()) {
                textArea.setText(encrypt(textField1.getText()));
                a += getRandomNumber();
                b += getRandomNumber();
                s += getRandomNumber();
            } else {
                textArea.setText("Enter Plain Text to Encrypt !!!");
            }
        });
        // Decrypt button action
        decryptButton.setOnAction((e) -> {
            if (!textField1.getText().isEmpty() && !key.isEmpty()) {
                textArea.setText(decrypt(textField1.getText()));
            } else {
                textArea.setText("Enter Cipher Text  to Decrypt !!!");
            }
        });
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("OneTimePad-Cipher-Algorithm");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}