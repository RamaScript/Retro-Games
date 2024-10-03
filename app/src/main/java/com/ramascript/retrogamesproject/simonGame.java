package com.ramascript.retrogamesproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class simonGame extends AppCompatActivity {

    private ArrayList<String> gameSequence = new ArrayList<>();
    private ArrayList<String> userSequence = new ArrayList<>();
    private String[] colors = {"red", "yellow", "purple", "green"};
    private boolean started = false;
    private int level = 0;

    private Button redButton, yellowButton, purpleButton, greenButton, startButton;
    private TextView levelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_game);

        redButton = findViewById(R.id.red_button);
        yellowButton = findViewById(R.id.yellow_button);
        purpleButton = findViewById(R.id.purple_button);
        greenButton = findViewById(R.id.green_button);
        startButton = findViewById(R.id.start_button);
        levelText = findViewById(R.id.level_text);

        // Start game when the user presses the start button
        startButton.setOnClickListener(v -> startGame());

        // User input on button click
        redButton.setOnClickListener(v -> handleUserInput("red"));
        yellowButton.setOnClickListener(v -> handleUserInput("yellow"));
        purpleButton.setOnClickListener(v -> handleUserInput("purple"));
        greenButton.setOnClickListener(v -> handleUserInput("green"));
    }

    private void startGame() {
        if (!started) {
            started = true;
            level = 0;
            gameSequence.clear();
            levelUp();
            startButton.setVisibility(View.INVISIBLE);  // Hide the start button when the game starts
        }
    }

    private void levelUp() {
        userSequence.clear();  // Clear the user's sequence for the new level
        level++;
        levelText.setText("Level " + level);

        // Generate a random color and add it to the game sequence
        String randomColor = colors[new Random().nextInt(colors.length)];
        gameSequence.add(randomColor);
        flashButton(randomColor);  // Flash the button for visual feedback
    }

    private void flashButton(String color) {
        Button button = getColorButton(color);
        button.setAlpha(0);
        new Handler().postDelayed(() -> button.setAlpha(1.0f), 200);
    }

    private Button getColorButton(String color) {
        switch (color) {
            case "red":
                return redButton;
            case "yellow":
                return yellowButton;
            case "purple":
                return purpleButton;
            case "green":
                return greenButton;
            default:
                return null;
        }
    }

    private void handleUserInput(String color) {
        if (!started) return;

        userSequence.add(color);
        flashButton(color);  // Flash the button when pressed

        checkAnswer(userSequence.size() - 1);  // Check the user's input
    }

    private void checkAnswer(int currentIndex) {
        if (userSequence.get(currentIndex).equals(gameSequence.get(currentIndex))) {
            if (userSequence.size() == gameSequence.size()) {
                // User completed the sequence correctly
                new Handler().postDelayed(this::levelUp, 1000);  // Go to next level after a short delay
            }
        } else {
            // User got the sequence wrong, game over
            levelText.setText("Game Over! Your score was: " + level);
            startButton.setVisibility(View.VISIBLE);  // Show the start button for restart
            startButton.setText("Restart");
            resetGame();
        }
    }

    private void resetGame() {
        started = false;
        gameSequence.clear();
        userSequence.clear();
    }
}
