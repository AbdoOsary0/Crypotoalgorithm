package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Vigenere extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("vigenere");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label lblInput = new Label("write a plaintext or ciphertext");
        TextArea txtInput = new TextArea();

        Label lblKey = new Label("key");
        TextField txtKey = new TextField();

        Label lblResult = new Label("result");
        TextArea txtResult = new TextArea();
        txtResult.setEditable(false);

        Button btnEncrypt = new Button("encrypt");
        Button btnDecrypt = new Button("decrypt");

        btnEncrypt.setOnAction(e -> {
            String input = txtInput.getText();
            String key = txtKey.getText();
            txtResult.setText(encrypt(input, key));
        });

        btnDecrypt.setOnAction(e -> {
            String input = txtInput.getText();
            String key = txtKey.getText();
            txtResult.setText(decrypt(input, key));
        });

        HBox buttonLayout = new HBox(15, btnEncrypt, btnDecrypt);
        buttonLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(lblInput, txtInput, lblKey, txtKey, lblResult, txtResult, buttonLayout);

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String encrypt(String plaintext, String key) {
        StringBuilder result = new StringBuilder();
        key = key.replaceAll("[^a-zA-Z]", "");
        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char letter = plaintext.charAt(i);
            if (Character.isLetter(letter)) {
                char base = Character.isUpperCase(letter) ? 'A' : 'a';
                char keyletter = Character.toUpperCase(key.charAt(j % key.length()));
                result.append((char) ((letter - base + (keyletter - 'A')) % 26 + base));
                j++;
            } else {
                result.append(letter);
            }
        }
        return result.toString();
    }

    private String decrypt(String ciphertext, String key) {
        StringBuilder result = new StringBuilder();
        key = key.replaceAll("[^a-zA-Z]", "");
        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char letter = ciphertext.charAt(i);
            if (Character.isLetter(letter)) {
                char base = Character.isUpperCase(letter) ? 'A' : 'a';
                char keyletter = Character.toUpperCase(key.charAt(j % key.length()));
                result.append((char) ((letter - base - (keyletter - 'A') + 26) % 26 + base));
                j++;
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
