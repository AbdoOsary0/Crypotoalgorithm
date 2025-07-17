package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VigenereCipher extends Application {

    public static String encrypt(String plaintext, String key) {
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            if (Character.isLetter(plainChar)) {
                char keyChar = key.charAt(i % key.length());
                char base = Character.isUpperCase(plainChar) ? 'A' : 'a';
                int shift = Character.isUpperCase(keyChar) ? keyChar - 'A' : keyChar - 'a';
                cipherText.append((char) ((plainChar - base + shift) % 26 + base));
            } else {
                cipherText.append(plainChar);
            }
        }
        return cipherText.toString();
    }

    public static String decrypt(String cipherText, String key) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char cipherChar = cipherText.charAt(i);
            if (Character.isLetter(cipherChar)) {
                char keyChar = key.charAt(i % key.length());
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
        Label label2 = new Label("Enter Key Value:");
        TextField textField2 = new TextField();
        textField2.setPromptText("Enter Key Value");
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPromptText("Result will appear here...");
        textArea.setPrefHeight(80);
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");
        root.getChildren().addAll(label1, textField1, label2, textField2, textArea, encryptButton, decryptButton);
        // Encrypt button action
        encryptButton.setOnAction((e) -> {
            if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
                textArea.setText(encrypt(textField1.getText(), textField2.getText()));
            } else {
                textArea.setText("Enter Plain Text and Key to Encrypt !!!");
            }
        });
        // Decrypt button action
        decryptButton.setOnAction((e) -> {
            if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
                textArea.setText(decrypt(textField1.getText(), textField2.getText()));
            } else {
                textArea.setText("Enter Cipher Text and Key to Decrypt !!!");
            }
        });
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vigenere-Cipher-Algorithm");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
