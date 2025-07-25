package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AESCipher1 extends Application {
    // S-Box and inverse S-Box
    public static final int[][] S_BOX = {{0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76}, {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0}, {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15}, {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75}, {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84}, {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF}, {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8}, {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2}, {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73}, {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB}, {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79}, {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08}, {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A}, {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E}, {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF}, {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}};

    public static final int[][] INV_S_BOX = {{0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB}, {0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB}, {0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E}, {0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25}, {0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92}, {0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84}, {0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06}, {0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B}, {0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73}, {0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E}, {0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B}, {0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4}, {0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F}, {0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF}, {0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61}, {0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D}};

    public static final int[][] MIX_COLUMNS_MATRIX = {{0x02, 0x03, 0x01, 0x01}, {0x01, 0x02, 0x03, 0x01}, {0x01, 0x01, 0x02, 0x03}, {0x03, 0x01, 0x01, 0x02}};

    public static final int[][] INV_MIX_COLUMNS_MATRIX = {{0x0E, 0x0B, 0x0D, 0x09}, {0x09, 0x0E, 0x0B, 0x0D}, {0x0D, 0x09, 0x0E, 0x0B}, {0x0B, 0x0D, 0x09, 0x0E}};

    private static final int[] RCON = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36};

    public static String generateRandomPlaintext(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) (random.nextInt(26) + 'a'));
        }
        return sb.toString();
    }

    // Generate random key
    public static String generateRandomKey() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append((char) (random.nextInt(26) + 'a'));
        }
        return sb.toString();
    }

    private static int[] subWord(int[] word) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            int val = word[i];
            result[i] = S_BOX[(val >> 4) & 0x0F][val & 0x0F];
        }
        return result;
    }

    private static int[] rotWord(int[] word) {
        return new int[]{word[1], word[2], word[3], word[0]};
    }

    private static int[][][] generateRoundKeys(int[][] key) {
        int[][] w = new int[44][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                w[i][j] = key[j][i];
            }
        }
        for (int i = 4; i < 44; i++) {
            int[] temp = w[i - 1].clone();

            if (i % 4 == 0) {
                temp = rotWord(temp);
                temp = subWord(temp);
                temp[0] ^= RCON[i / 4 - 1];
            }

            for (int j = 0; j < 4; j++) {
                w[i][j] = w[i - 4][j] ^ temp[j];
            }
        }

        int[][][] roundKeys = new int[11][4][4];
        for (int round = 0; round < 11; round++) {
            for (int col = 0; col < 4; col++) {
                for (int row = 0; row < 4; row++) {
                    roundKeys[round][row][col] = w[round * 4 + col][row];
                }
            }
        }
        writeToFile(roundKeys, "RoundKeys.txt");
        return roundKeys;
    }

    public static void writeToFile(int[][][] keys, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Iterate through all 11 rounds
            for (int round = 0; round < 11; round++) {
                writer.write("Round " + round + ":");
                writer.newLine();

                // Iterate through columns (4 columns)
                for (int col = 0; col < 4; col++) {
                    StringBuilder line = new StringBuilder();

                    // Iterate through rows (4 rows)
                    for (int row = 0; row < 4; row++) {
                        // Write each key byte, formatting it as two hex digits
                        line.append(String.format("%02x ", keys[round][row][col] & 0xFF));
                    }

                    // Write the current line of keys for the current round
                    writer.write(line.toString().trim()); // Remove extra space at the end
                    writer.newLine();
                }
                writer.newLine(); // Add a blank line between rounds for clarity
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static int[][] encryptBlock(int[][] state, int[][][] roundKeys) {
        addRoundKey(state, roundKeys[0]);

        for (int round = 1; round < 10; round++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, roundKeys[round]);
        }

        subBytes(state);
        shiftRows(state);
        addRoundKey(state, roundKeys[10]);

        return state;
    }

    private static int[][] decryptBlock(int[][] state, int[][][] roundKeys) {
        addRoundKey(state, roundKeys[10]);
        invShiftRows(state);
        invSubBytes(state);

        for (int round = 9; round >= 1; round--) {
            addRoundKey(state, roundKeys[round]);
            invMixColumns(state);
            invShiftRows(state);
            invSubBytes(state);
        }

        addRoundKey(state, roundKeys[0]);

        return state;
    }

    public static String encryptToHex(String plaintext, String key) {
        String padded = padPlaintext(plaintext);
        int blocks = padded.length() / 16;

        int[][] keyMatrix = stringToMatrix(key.substring(0, 16));
        int[][][] roundKeys = generateRoundKeys(keyMatrix);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < blocks; i++) {
            String block = padded.substring(i * 16, (i + 1) * 16);
            int[][] state = stringToMatrix(block);
            state = encryptBlock(state, roundKeys);
            result.append(matrixToHex(state));
        }

        return result.toString();
    }

    public static String decryptFromHex(String ciphertext, String key) {
        int blocks = ciphertext.length() / 32;
        int[][] keyMatrix = stringToMatrix(key.substring(0, 16));
        int[][][] roundKeys = generateRoundKeys(keyMatrix);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < blocks; i++) {
            String block = ciphertext.substring(i * 32, (i + 1) * 32);
            int[][] state = hexToMatrix(block);
            state = decryptBlock(state, roundKeys);
            result.append(matrixToString(state));
        }

        return unpadPlaintext(result.toString());
    }

    private static String padPlaintext(String input) {
        int blockSize = 16;
        int padding = blockSize - (input.length() % blockSize);
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < padding; i++) {
            sb.append((char) padding);
        }
        return sb.toString();
    }

    private static String unpadPlaintext(String input) {
        int padding = input.charAt(input.length() - 1);
        return input.substring(0, input.length() - padding);
    }

    private static int[][] stringToMatrix(String textBlock) {
        int[][] matrix = new int[4][4];
        for (int i = 0; i < 16; i++) {
            matrix[i % 4][i / 4] = textBlock.charAt(i);
        }
        return matrix;
    }

    private static int[][] hexToMatrix(String hex) {
        int[][] matrix = new int[4][4];
        for (int i = 0; i < 32; i += 2) {
            String byteStr = hex.substring(i, i + 2);
            int val = Integer.parseInt(byteStr, 16);
            matrix[(i / 2) % 4][(i / 2) / 4] = val;
        }
        return matrix;
    }

    private static String matrixToHex(int[][] matrix) {
        StringBuilder hex = new StringBuilder();
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                hex.append(String.format("%02X", matrix[row][col]));
            }
        }
        return hex.toString();
    }

    private static String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                sb.append((char) matrix[row][col]);
            }
        }
        return sb.toString();
    }

    private static void addRoundKey(int[][] state, int[][] roundKey) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                state[row][col] ^= roundKey[row][col];
            }
        }
    }

    private static void subBytes(int[][] state) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int val = state[row][col];
                state[row][col] = S_BOX[(val >> 4) & 0x0F][val & 0x0F];
            }
        }
    }

    private static void invSubBytes(int[][] state) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int val = state[row][col];
                state[row][col] = INV_S_BOX[(val >> 4) & 0x0F][val & 0x0F];
            }
        }
    }

    private static void shiftRows(int[][] state) {
        for (int row = 1; row < 4; row++) {
            int[] temp = new int[4];
            for (int col = 0; col < 4; col++) {
                temp[col] = state[row][(col + row) % 4];
            }
            System.arraycopy(temp, 0, state[row], 0, 4);
        }
    }

    private static void invShiftRows(int[][] state) {
        for (int row = 1; row < 4; row++) {
            int[] temp = new int[4];
            for (int col = 0; col < 4; col++) {
                temp[(col + row) % 4] = state[row][col];
            }
            System.arraycopy(temp, 0, state[row], 0, 4);
        }
    }

    private static int gmul(int a, int b) {
        int p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) p ^= a;
            boolean highBitSet = (a & 0x80) != 0;
            a <<= 1;
            if (highBitSet) a ^= 0x1B;
            b >>= 1;
        }
        return p & 0xFF;
    }

    private static void mixColumns(int[][] state) {
        int[][] temp = new int[4][4];
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                temp[row][col] = gmul(MIX_COLUMNS_MATRIX[row][0], state[0][col]) ^ gmul(MIX_COLUMNS_MATRIX[row][1], state[1][col]) ^ gmul(MIX_COLUMNS_MATRIX[row][2], state[2][col]) ^ gmul(MIX_COLUMNS_MATRIX[row][3], state[3][col]);
            }
        }
        for (int row = 0; row < 4; row++) {
            System.arraycopy(temp[row], 0, state[row], 0, 4);
        }
    }

    private static void invMixColumns(int[][] state) {
        int[][] temp = new int[4][4];
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                temp[row][col] = gmul(INV_MIX_COLUMNS_MATRIX[row][0], state[0][col]) ^ gmul(INV_MIX_COLUMNS_MATRIX[row][1], state[1][col]) ^ gmul(INV_MIX_COLUMNS_MATRIX[row][2], state[2][col]) ^ gmul(INV_MIX_COLUMNS_MATRIX[row][3], state[3][col]);
            }
        }
        for (int row = 0; row < 4; row++) {
            System.arraycopy(temp[row], 0, state[row], 0, 4);
        }
    }

    @Override
    public void start(Stage stage) {
        // Input components
        Label inputLabel = new Label("Enter Text:");
        TextField inputField = new TextField();
        Button generateTextBtn = new Button("Generate Random Text");

        Label keyLabel = new Label("Enter Key:");
        TextField keyField = new TextField();
        Button generateKeyBtn = new Button("Generate Random Key");

        // Action buttons
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        generateTextBtn.setOnAction(e -> {
            inputField.setText(generateRandomPlaintext(16));
        });

        generateKeyBtn.setOnAction(e -> {
            keyField.setText(generateRandomKey());
        });

        encryptButton.setOnAction(e -> {
            String text = inputField.getText();
            String key = keyField.getText();
            if (key.length() < 16) {
                while (key.length() < 16) {
                    key += " ";
                }
            }
            String encrypted = encryptToHex(text, key);
            outputArea.setText(encrypted);
        });

        // Decryption handler
        decryptButton.setOnAction(e -> {
            String ciphertext = inputField.getText();
            String key = keyField.getText();
            if (key.length() < 16) {
                while (key.length() < 16) {
                    key += " ";
                }
            }
            String decrypted = decryptFromHex(ciphertext, key);
            outputArea.setText(decrypted);
        });
        // Layout
        VBox layout = new VBox(10, inputLabel, inputField, generateTextBtn, keyLabel, keyField, generateKeyBtn, new HBox(10, encryptButton, decryptButton), outputArea);
        layout.setPadding(new Insets(15));

        // Scene and stage
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("AES Encryption/Decryption");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}