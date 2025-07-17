package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RabinInt extends Application {

    public static String millerRabinTest(int n, int iterations) {
        if (n < 2) return "Composite";
        if (n == 2 || n == 3) return "May Be Prime";
        if (n % 2 == 0) return "Composite";

        int d = n - 1;
        int r = 0;
        while (d % 2 == 0) {
            d /= 2;
            r++;
        }

        for (int i = 0; i < iterations; i++) {
            //(0 -> n-3) +2 -> [2 -> n-2]
            int a = 2 + (int) (Math.random() * (n - 3));
            long x = modPow(a, d, n);
            if (x == 1 || x == n - 1) continue;

            boolean passed = false;
            for (int j = 0; j < r - 1; j++) {
                x = modPow(x, 2, n);
                if (x == n - 1) {
                    passed = true;
                    break;
                }
            }

            if (!passed) return "Composite";
        }
        return "May Be Prime";
    }

    private static long modPow(long base, long exp, int mod) {
        long result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }

    @Override
    public void start(Stage stage) {
        Label inputLabel = new Label("Enter Number:");
        TextField inputField = new TextField();

        Button checkButton = new Button("Check Prime");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        checkButton.setOnAction(e -> {
            String input = inputField.getText().trim();
            try {
                int number = Integer.parseInt(input);
                // for each
                String result = millerRabinTest(number, 15);
                outputArea.setText(result);
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid input");
            }
        });

        VBox layout = new VBox(10, inputLabel, inputField, new HBox(10, checkButton), outputArea);
        layout.setPadding(new Insets(15));
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Rabin-Miller Primality Test");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
