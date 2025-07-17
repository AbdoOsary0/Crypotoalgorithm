package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RSAAlgo extends Application {
    // [e,n] ->public   private-> [d,n]
    private BigInteger n, e, d;
    private final SecureRandom random = new SecureRandom();
    private final int BIT_LENGTH = 512;
    private final int BLOCK_SIZE = BIT_LENGTH / 8 - 11; // Leave room for padding

    @Override
    public void start(Stage primaryStage) {
        generateKeys();

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label label1 = new Label("Enter Text:");
        TextField inputField = new TextField();
        inputField.setPromptText("Enter text message");

        Label label2 = new Label("Result:");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPromptText("Result will appear here...");
        resultArea.setPrefHeight(100);

        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        root.getChildren().addAll(label1, inputField, label2, resultArea, encryptButton, decryptButton);

        encryptButton.setOnAction(event -> {
            try {
                String message = inputField.getText();
                if (message.isEmpty()) {
                    resultArea.setText("Please enter a message to encrypt.");
                    return;
                }

                List<BigInteger> encryptedBlocks = encryptMessage(message);
                StringBuilder sb = new StringBuilder();
                for (BigInteger block : encryptedBlocks) {
                    sb.append(block.toString()).append("\n");
                }
                resultArea.setText("Encrypted Blocks:\n" + sb.toString());
            } catch (Exception ex) {
                resultArea.setText("Error during encryption: " + ex.getMessage());
            }
        });

        decryptButton.setOnAction(event -> {
            try {
                String cipherText = inputField.getText();
                if (cipherText.isEmpty()) {
                    resultArea.setText("Please enter encrypted blocks to decrypt.");
                    return;
                }

                String[] blocks = cipherText.split("\n");
                List<BigInteger> encryptedBlocks = new ArrayList<>();
                for (String block : blocks) {
                    if (!block.trim().isEmpty()) {
                        encryptedBlocks.add(new BigInteger(block.trim()));
                    }
                }

                String decryptedMessage = decryptMessage(encryptedBlocks);
                resultArea.setText("Decrypted Message:\n" + decryptedMessage);
            } catch (Exception ex) {
                resultArea.setText("Error during decryption: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(root, 480, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("RSA Cipher");
        primaryStage.show();
    }

    private List<BigInteger> encryptMessage(String message) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        List<BigInteger> encryptedBlocks = new ArrayList<>();

        for (int i = 0; i < messageBytes.length; i += BLOCK_SIZE) {
            int length = Math.min(BLOCK_SIZE, messageBytes.length - i);
            byte[] block = new byte[length];
            System.arraycopy(messageBytes, i, block, 0, length);

            BigInteger messageInt = new BigInteger(1, block); // Using 1 to ensure positive number
            if (messageInt.compareTo(n) >= 0) {
                throw new IllegalArgumentException("Message block too large for current key size");
            }
            BigInteger cipher = messageInt.modPow(e, n);
            encryptedBlocks.add(cipher);
        }
        return encryptedBlocks;
    }

    private String decryptMessage(List<BigInteger> encryptedBlocks) {
        byte[] decryptedBytes = new byte[0];

        for (BigInteger block : encryptedBlocks) {
            BigInteger decrypted = block.modPow(d, n);
            byte[] blockBytes = decrypted.toByteArray();

            // Handle leading zero byte if present
            if (blockBytes[0] == 0) {
                byte[] temp = new byte[blockBytes.length - 1];
                System.arraycopy(blockBytes, 1, temp, 0, temp.length);
                blockBytes = temp;
            }

            // Concatenate the decrypted blocks
            byte[] temp = new byte[decryptedBytes.length + blockBytes.length];
            System.arraycopy(decryptedBytes, 0, temp, 0, decryptedBytes.length);
            System.arraycopy(blockBytes, 0, temp, decryptedBytes.length, blockBytes.length);
            decryptedBytes = temp;
        }
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private void generateKeys() {
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH, random);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH, random);
        while (p.compareTo(q) == 0) {
            q = BigInteger.probablePrime(BIT_LENGTH, random);
            p = BigInteger.probablePrime(BIT_LENGTH, random);
        }
//        System.out.println(p );
//        System.out.println(q );
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(BIT_LENGTH/2, random);; // Common public exponent
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.TWO);
        }
        d = e.modInverse(phi); // Using built-in modInverse for simplicity
    }

    public static void main(String[] args) {
        launch(args);
    }
}