package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestDes extends Application {

    // S-Boxes
    private static final int[][][] S_BOXES = {
            { // S1
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            { // S2
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            { // S3
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            { // S4
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            { // S5
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            { // S6
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            { // S7
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            { // S8
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };

    // Permutation tables
    private static final int[][] PC1 = {
            {57, 49, 41, 33, 25, 17, 9},
            {1, 58, 50, 42, 34, 26, 18},
            {10, 2, 59, 51, 43, 35, 27},
            {19, 11, 3, 60, 52, 44, 36},
            {63, 55, 47, 39, 31, 23, 15},
            {7, 62, 54, 46, 38, 30, 22},
            {14, 6, 61, 53, 45, 37, 29},
            {21, 13, 5, 28, 20, 12, 4}
    };

    private static final int[][] PC2 = {
            {14, 17, 11, 24, 1, 5},
            {3, 28, 15, 6, 21, 10},
            {23, 19, 12, 4, 26, 8},
            {16, 7, 27, 20, 13, 2},
            {41, 52, 31, 37, 47, 55},
            {30, 40, 51, 45, 33, 48},
            {44, 49, 39, 56, 34, 53},
            {46, 42, 50, 36, 29, 32}
    };

    private static final int[][] IP = {
            {58, 50, 42, 34, 26, 18, 10, 2},
            {60, 52, 44, 36, 28, 20, 12, 4},
            {62, 54, 46, 38, 30, 22, 14, 6},
            {64, 56, 48, 40, 32, 24, 16, 8},
            {57, 49, 41, 33, 25, 17, 9, 1},
            {59, 51, 43, 35, 27, 19, 11, 3},
            {61, 53, 45, 37, 29, 21, 13, 5},
            {63, 55, 47, 39, 31, 23, 15, 7}
    };

    private static final int[][] IP_INV = {
            {40, 8, 48, 16, 56, 24, 64, 32},
            {39, 7, 47, 15, 55, 23, 63, 31},
            {38, 6, 46, 14, 54, 22, 62, 30},
            {37, 5, 45, 13, 53, 21, 61, 29},
            {36, 4, 44, 12, 52, 20, 60, 28},
            {35, 3, 43, 11, 51, 19, 59, 27},
            {34, 2, 42, 10, 50, 18, 58, 26},
            {33, 1, 41, 9, 49, 17, 57, 25}
    };

    private static final int[][] E = {
            {32, 1, 2, 3, 4, 5},
            {4, 5, 6, 7, 8, 9},
            {8, 9, 10, 11, 12, 13},
            {12, 13, 14, 15, 16, 17},
            {16, 17, 18, 19, 20, 21},
            {20, 21, 22, 23, 24, 25},
            {24, 25, 26, 27, 28, 29},
            {28, 29, 30, 31, 32, 1}
    };

    private static final int[][] P = {
            {16, 7, 20, 21, 29, 12, 28, 17},
            {1, 15, 23, 26, 5, 18, 31, 10},
            {2, 8, 24, 14, 32, 27, 3, 9},
            {19, 13, 30, 6, 22, 11, 4, 25}
    };

    private static final int[] LEFT_SHIFT_SCHEDULE = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};


    public static String encrypt(String plaintext, String key) {
        validate8CharInput(plaintext, key);
        int[] plainBits = textToBits(plaintext);
        int[] keyBits = textToBits(key);
        int[][] roundKeys = generateRoundKeys(keyBits);
        int[] cipherBits = processBlock(plainBits, roundKeys, true);
        return bitsToHex(cipherBits);
    }

    public static String decrypt(String ciphertextHex, String key) {
        validateHexInput(ciphertextHex, key);
        int[] cipherBits = hexToBits(ciphertextHex);
        int[] keyBits = textToBits(key);
        int[][] roundKeys = generateRoundKeys(keyBits);
        int[] plainBits = processBlock(cipherBits, roundKeys, false);
        return bitsToText(plainBits);
    }

    /* ======================== */
    /* Helper Methods           */
    /* ======================== */

    private static void validate8CharInput(String text, String key) {
        if (text.length() != 8 || key.length() != 8) {
            throw new IllegalArgumentException("Both plaintext and key must be exactly 8 characters");
        }
    }

    private static void validateHexInput(String hex, String key) {
        if (hex.length() != 16 || key.length() != 8) {
            throw new IllegalArgumentException("Ciphertext must be 16 hex digits and key must be 8 characters");
        }
        if (!hex.matches("[0-9a-fA-F]+")) {
            throw new IllegalArgumentException("Ciphertext must contain only hex digits");
        }
    }

    private static int[] textToBits(String text) {
        int[] bits = new int[64];
        for (int i = 0; i < 8; i++) {
            int ch = text.charAt(i);
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = (ch >> (7 - j)) & 1;
            }
        }
        return bits;
    }

    private static String bitsToText(int[] bits) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 64; i += 8) {
            int ch = 0;
            for (int j = 0; j < 8; j++) {
                ch = (ch << 1) | bits[i + j];
            }
            text.append((char) ch);
        }
        return text.toString();
    }

    private static String bitsToHex(int[] bits) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < 64; i += 4) {
            int nibble = 0;
            for (int j = 0; j < 4; j++) {
                nibble = (nibble << 1) | bits[i + j];
            }
            hex.append(Integer.toHexString(nibble));
        }
        return hex.toString();
    }

    private static int[] hexToBits(String hex) {
        int[] bits = new int[64];
        for (int i = 0; i < 16; i++) {
            int nibble = Integer.parseInt(hex.substring(i, i + 1), 16);
            for (int j = 0; j < 4; j++) {
                bits[i * 4 + j] = (nibble >> (3 - j)) & 1;
            }
        }
        return bits;
    }

    private static int[][] generateRoundKeys(int[] keyBits) {
        int[] permutedKey = permute(keyBits, PC1);
        int[] leftKey = new int[28];
        int[] rightKey = new int[28];
        System.arraycopy(permutedKey, 0, leftKey, 0, 28);
        System.arraycopy(permutedKey, 28, rightKey, 0, 28);

        int[][] roundKeys = new int[16][48];
        for (int round = 0; round < 16; round++) {
            leftKey = leftShift(leftKey, LEFT_SHIFT_SCHEDULE[round]);
            rightKey = leftShift(rightKey, LEFT_SHIFT_SCHEDULE[round]);

            int[] combinedKey = new int[56];
            System.arraycopy(leftKey, 0, combinedKey, 0, 28);
            System.arraycopy(rightKey, 0, combinedKey, 28, 28);
            roundKeys[round] = permute(combinedKey, PC2);
        }
        for (int round = 0; round < 16; round++) {
            System.out.print("Sub key Number" + (round + 1) + ":");
            for (int i = 0; i < 48; i++) {
                System.out.print(roundKeys[round][i]);
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------");
        return roundKeys;
    }

    private static int[] processBlock(int[] input, int[][] roundKeys, boolean encrypt) {
        input = permute(input, IP);
        int[] left = new int[32];
        int[] right = new int[32];
        System.arraycopy(input, 0, left, 0, 32);
        System.arraycopy(input, 32, right, 0, 32);

        for (int round = 0; round < 16; round++) {
            int[] roundKey = encrypt ? roundKeys[round] : roundKeys[15 - round];
            int[] feistelOutput = feistelFunction(right, roundKey);

            int[] newRight = new int[32];
            for (int i = 0; i < 32; i++) {
                newRight[i] = left[i] ^ feistelOutput[i];
            }
            left = right;
            right = newRight;
        }
        int[] output = new int[64];
        System.arraycopy(right, 0, output, 0, 32);
        System.arraycopy(left, 0, output, 32, 32);
        return permute(output, IP_INV);
    }

    private static int[] feistelFunction(int[] right, int[] roundKey) {
        int[] expanded = permute(right, E);
        int[] xorResult = new int[48];
        for (int i = 0; i < 48; i++) {
            xorResult[i] = expanded[i] ^ roundKey[i];
        }
        int[] sBoxOutput = new int[32];
        for (int i = 0; i < 8; i++) {
            int[] sixBits = new int[6];
            System.arraycopy(xorResult, i * 6, sixBits, 0, 6);
            int row = (sixBits[0] << 1) + sixBits[5];
            int col = (sixBits[1] << 3) + (sixBits[2] << 2) + (sixBits[3] << 1) + sixBits[4];
            int value = S_BOXES[i][row][col];
            for (int j = 0; j < 4; j++) {
                sBoxOutput[i * 4 + j] = (value >> (3 - j)) & 1;
            }
        }
        return permute(sBoxOutput, P);
    }

    private static int[] permute(int[] input, int[][] table) {
        int[] output = new int[table.length * table[0].length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                output[i * table[i].length + j] = input[table[i][j] - 1];
            }
        }
        return output;
    }

    private static int[] leftShift(int[] input, int shifts) {
        int[] output = new int[input.length];
        System.arraycopy(input, shifts, output, 0, input.length - shifts);
        System.arraycopy(input, 0, output, input.length - shifts, shifts);
        return output;
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
        primaryStage.setTitle("DesCipher-Algorithm");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

