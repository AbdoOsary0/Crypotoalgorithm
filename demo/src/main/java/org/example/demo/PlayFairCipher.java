package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayFairCipher extends Application {
    public static char[][] keySquare;

    // Generate the 5x5 key square
    public void GenerteKey(String key) {
        keySquare = MatixKeys(key.toUpperCase().replace("J", "I"));
    }

    // Create the 5x5 key square from the key
    private char[][] MatixKeys(String key) {
        char[][] square = new char[5][5];
        boolean[] used = new boolean[26];
        int row = 0, col = 0;

        // Fill the key square with the key
        for (char c : key.toCharArray()) {
            if (c >= 'A' && c <= 'Z' && c != 'J' && !used[c - 'A']) {
                square[row][col] = c;
                used[c - 'A'] = true;
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }

        // Fill the remaining spaces with the rest of the alphabet
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !used[c - 'A']) {
                square[row][col] = c;
                used[c - 'A'] = true;
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }
        return square;
    }

    // Find the position of a character in the key square
    private int[] findPlace(char c) {
        c = Character.toUpperCase(c); // Convert to uppercase
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (keySquare[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return null; // Return null if the character is not found
    }

    // Encrypt a pair of characters
    private String encryptPair(String pair) {
        char a = Character.toUpperCase(pair.charAt(0)); // Convert to uppercase
        char b = Character.toUpperCase(pair.charAt(1)); // Convert to uppercase

        // If either character is not a letter or not in the key square, return the pair as-is
        if (!Character.isLetter(a) || !Character.isLetter(b)) {
            return pair;
        }

        int[] posA = findPlace(a);
        int[] posB = findPlace(b);

        // If either character is not found in the key square, return the pair as-is
        if (posA == null || posB == null) {
            return pair;
        }

        if (posA[0] == posB[0]) { // Same row
            return "" + keySquare[posA[0]][(posA[1] + 1) % 5] + keySquare[posB[0]][(posB[1] + 1) % 5];
        } else if (posA[1] == posB[1]) { // Same column
            return "" + keySquare[(posA[0] + 1) % 5][posA[1]] + keySquare[(posB[0] + 1) % 5][posB[1]];
        } else { // Rectangle
            return "" + keySquare[posA[0]][posB[1]] + keySquare[posB[0]][posA[1]];
        }
    }

    // Decrypt a pair of characters
    private String decryptPair(String pair) {
        char a = Character.toUpperCase(pair.charAt(0)); // Convert to uppercase
        char b = Character.toUpperCase(pair.charAt(1)); // Convert to uppercase

        // If either character is not a letter or not in the key square, return the pair as-is
        if (!Character.isLetter(a) || !Character.isLetter(b)) {
            return pair;
        }

        int[] posA = findPlace(a);
        int[] posB = findPlace(b);

        // If either character is not found in the key square, return the pair as-is
        if (posA == null || posB == null) {
            return pair;
        }

        if (posA[0] == posB[0]) { // Same row
            return "" + keySquare[posA[0]][(posA[1] - 1 + 5) % 5] + keySquare[posB[0]][(posB[1] - 1 + 5) % 5];
        } else if (posA[1] == posB[1]) { // Same column
            return "" + keySquare[(posA[0] - 1 + 5) % 5][posA[1]] + keySquare[(posB[0] - 1 + 5) % 5][posB[1]];
        } else { // Rectangle
            return "" + keySquare[posA[0]][posB[1]] + keySquare[posB[0]][posA[1]];
        }
    }

    // Encrypt the plaintext
    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (Character.isLetter(c)) {
                // Handle alphabetic characters
                String pair = (i + 1 < plaintext.length() && Character.isLetter(plaintext.charAt(i + 1)))
                        ? encryptPair("" + c + plaintext.charAt(i + 1))
                        : encryptPair("" + c + 'X');
                ciphertext.append(pair);
                i++; // Skip the next character since it's part of the pair
            } else {
                // Keep non-alphabetic characters unchanged
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (Character.isLetter(c)) {
                // Handle alphabetic characters
                String pair = (i + 1 < ciphertext.length() && Character.isLetter(ciphertext.charAt(i + 1)))
                        ? decryptPair("" + c + ciphertext.charAt(i + 1))
                        : decryptPair("" + c + 'X');
                plaintext.append(pair);
                i++; // Skip the next character since it's part of the pair
            } else {
                // Keep non-alphabetic characters unchanged
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }

    // JavaFX GUI setup
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
                GenerteKey(textField2.getText());
                textArea.setText(encrypt(textField1.getText()));
            } else {
                textArea.setText("Enter Plain Text and Key to Encrypt !!!");
            }
        });

        // Decrypt button action
        decryptButton.setOnAction((e) -> {
            if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
                GenerteKey(textField2.getText());
                textArea.setText(decrypt(textField1.getText()));
            } else {
                textArea.setText("Enter Cipher Text and Key to Decrypt !!!");
            }
        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PlayFairCipher-Algorithm");
        primaryStage.show();
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}