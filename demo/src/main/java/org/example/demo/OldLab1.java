package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OldLab1 extends Application {
    public String Encryption(int key, String planeText) {
        StringBuilder cypherText = new StringBuilder();
        for (char c : planeText.toCharArray()) {
            if (Character.isLetter(c)) {
                char chr = Character.isLowerCase(c) ? 'a' : 'A';
                cypherText.append((char) ((c + key - chr) % 26 + chr));
            } else {
                cypherText.append(c);
            }
        }
        return cypherText.toString();
    }

    public String Decryption(int key, String cypherText) {
        StringBuilder message = new StringBuilder();
        for (char c : cypherText.toCharArray()) {
            if (Character.isLetter(c)) {
                char chr = Character.isLowerCase(c) ? 'a' : 'A';
                message.append((char) (((c - key - chr) + 26) % 26 + chr));
            } else {
                message.append(c);
            }
        }
        return message.toString();
    }

    public void Attack(String EncMessage) {
        int key = 1;
        ArrayList<String> list = new ArrayList<>();

        while (key <= 26) {
            StringBuilder Ans = new StringBuilder();
            for (char c : EncMessage.toCharArray()) {
                if (Character.isLetter(c)) {
                    char chr = Character.isLowerCase(c) ? 'a' : 'A';
                    Ans.append((char) ((c - key - chr + 26) % 26 + chr));
                } else {
                    Ans.append(c);
                }
            }
            list.add("Key " + key + ": " + Ans);
            key++;
        }

        // Convert the ArrayList to a String array and write to file
        writeToFile(list.toArray(new String[0]), "AttackFile.txt");
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));


        // Create UI components
        Label label1 = new Label("Enter Word To Encrypt:");
        TextField textField1 = new TextField();
        textField1.setPromptText("Enter text here...");

        Label label2 = new Label("Enter Key Value:");
        TextField textField2 = new TextField();
        textField2.setPromptText("Enter key here...");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPromptText("Result will appear here...");
        textArea.setPrefHeight(100);

        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");
        Button attackButton = new Button("Attack");

        // Add components to the root layout
        root.getChildren().addAll(label1, textField1, label2, textField2, textArea, encryptButton, decryptButton, attackButton);

        // Set actions for buttons
        encryptButton.setOnAction(e -> {
            if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
                String encryptedText = Encryption(Integer.parseInt(textField2.getText()), textField1.getText());
                textArea.setText(encryptedText);
            } else {
                textArea.setText("Please Enter Word and Enter Key Value");
            }
        });

        decryptButton.setOnAction(e -> {
            if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
                String decryptedText = Decryption(Integer.parseInt(textField2.getText()), textField1.getText());
                textArea.setText(decryptedText);
            } else {
                textArea.setText("Please Enter Word To Decrypt and Enter Key Value");
            }
        });
        attackButton.setOnAction(e -> Attack(textArea.getText()));

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Caesar Cipher Algorithm");
        primaryStage.show();
    }

    public void writeToFile(String[] text, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : text) {
                writer.write(line); // Write the line
                writer.newLine();   // Add a newline after each line
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}









