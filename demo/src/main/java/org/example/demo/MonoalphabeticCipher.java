package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
//import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;

public class MonoalphabeticCipher extends Application {
    public static char[] normalChar = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static char[] codedChar;

    public static void ReadApha() {
        List<Character> cipherList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Alph.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 1) {
                    cipherList.add(line.charAt(0));
                }
            }
            codedChar = new char[cipherList.size()];
            for (int i = 0; i < cipherList.size(); i++) {
                codedChar[i] = cipherList.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Encryption(String planeText) {
        StringBuilder CypherText = new StringBuilder();
        planeText = planeText.toLowerCase();
        for (int i = 0; i < planeText.length(); i++) {
            for (int j = 0; j <= 25; j++) {
                if (Character.isLetter(planeText.charAt(i))) {
                    if (planeText.charAt(i) == normalChar[j]) {
                        CypherText.append(codedChar[j]);
                        break;
                    }
                } else {
                    CypherText.append(planeText.charAt(i));
                    break;
                }
            }
        }
        return CypherText.toString();
    }

    public String Decryption(String CypherText) {
        StringBuilder PlaneText = new StringBuilder();
        for (int i = 0; i < CypherText.length(); i++) {
            for (int j = 0; j <= 25; j++) {
                if (Character.isLetter(CypherText.charAt(i))) {
                    if (CypherText.charAt(i) == codedChar[j]) {
                        PlaneText.append(normalChar[j]);
                        break;
                    }
                } else {
                    PlaneText.append(CypherText.charAt(i));
                    break;
                }
            }
        }
        return PlaneText.toString();
    }


    @Override
    public void start(Stage primaryStage) {
        ReadApha();
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        Label label1 = new Label("Enter Plane Text:");

        TextField textField1 = new TextField();
        textField1.setPromptText("Enter Plane Text");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPromptText("Result will appear here...");
        textArea.setPrefHeight(80);

        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");


        root.getChildren().addAll(label1, textField1, textArea, encryptButton, decryptButton);
        encryptButton.setOnAction((e) -> {
            if (!textField1.getText().isEmpty()) {
                textArea.setText(Encryption(textField1.getText()));
            } else {
                textArea.setText("Enter Plane Text to Encrypt !!!");
            }
        });
        decryptButton.setOnAction((e) -> {
            if (!textField1.getText().isEmpty()) {
                textArea.setText(Decryption(textField1.getText()));
            } else {
                textArea.setText("Enter  Cypher Text to Decrypt ...");
            }
        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mono-Alph Algorithm");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
//        MonoalphabeticCipher app = new MonoalphabeticCipher();
    }


}
