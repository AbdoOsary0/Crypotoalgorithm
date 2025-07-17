package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class app extends Application {
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

    public String PlaneTextAttack(String PlaneText, String EncMessage, String EncMessage1) {
        int key = (EncMessage.charAt(0) - PlaneText.charAt(0) + 26) % 26;
        return Decryption(key, EncMessage1);
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // Create UI components for encryption/decryption
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
        Button bruteForceAttackButton = new Button("Brute-Force Attack");

        // Group encryption/decryption components
        GridPane encryptionPane = new GridPane();
        encryptionPane.setHgap(10);
        encryptionPane.setVgap(10);
        encryptionPane.setAlignment(Pos.CENTER);
        encryptionPane.add(label1, 0, 0);
        encryptionPane.add(textField1, 1, 0);
        encryptionPane.add(label2, 0, 1);
        encryptionPane.add(textField2, 1, 1);
        encryptionPane.add(textArea, 0, 2, 2, 1); // Span 2 columns

        HBox buttonBox1 = new HBox(10, encryptButton, decryptButton, bruteForceAttackButton);
        buttonBox1.setAlignment(Pos.CENTER);

        // Create UI components for plain text attack
        Label label3 = new Label("Enter Known Plaintext:");
        TextField textField3 = new TextField();
        textField3.setPromptText("Enter known plaintext...");

        Label label4 = new Label("Enter Corresponding Ciphertext:");
        TextField textField4 = new TextField();
        textField4.setPromptText("Enter corresponding ciphertext...");

        Label label5 = new Label("Enter Unknown Ciphertext:");
        TextField textField5 = new TextField();
        textField5.setPromptText("Enter ciphertext to decrypt...");

        // Add a new TextArea for the plain text attack section
        TextArea textArea1 = new TextArea();
        textArea1.setEditable(false);
        textArea1.setPromptText("PlaneText-Attack result will appear here...");
        textArea1.setPrefHeight(100);

        Button planeTextAttackButton = new Button("Get Message");

        // Group plain text attack components
        GridPane attackPane = new GridPane();
        attackPane.setHgap(10);
        attackPane.setVgap(10);
        attackPane.setAlignment(Pos.CENTER);
        attackPane.add(label3, 0, 0);
        attackPane.add(textField3, 1, 0);
        attackPane.add(label4, 0, 1);
        attackPane.add(textField4, 1, 1);
        attackPane.add(label5, 0, 2);
        attackPane.add(textField5, 1, 2);
        attackPane.add(textArea1, 0, 3, 2, 1); // Span 2 columns for the TextArea

        HBox buttonBox2 = new HBox(10, planeTextAttackButton);
        buttonBox2.setAlignment(Pos.CENTER);

        // Add components to the root layout
        root.getChildren().addAll(
                encryptionPane,
                buttonBox1,
                new Separator(), // Separator for better organization
                attackPane,
                buttonBox2
        );
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
        bruteForceAttackButton.setOnAction(e -> Attack(textArea.getText()));
        planeTextAttackButton.setOnAction((e) -> {
            if (!textField3.getText().isEmpty() && !textField4.getText().isEmpty() && !textField5.getText().isEmpty()) {
                textArea1.setText(PlaneTextAttack(textField3.getText(), textField4.getText(), textField5.getText()));
            } else {
                textArea1.setText("Please Fill The 3 Input To Attack");
            }
        });
        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 500, 500);
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
