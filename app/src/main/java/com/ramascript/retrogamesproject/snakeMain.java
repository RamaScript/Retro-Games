package com.ramascript.retrogamesproject;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class snakeMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_main);


        RelativeLayout board = findViewById(R.id.board);
        RelativeLayout border = findViewById(R.id.relativeLayout);
        LinearLayout lilu = findViewById(R.id.lilu);
        Button upButton = findViewById(R.id.up);
        Button downButton = findViewById(R.id.down);
        Button leftButton = findViewById(R.id.left);
        Button rightButton = findViewById(R.id.right);
        Button pauseButton = findViewById(R.id.pause);
        Button newgame = findViewById(R.id.new_game);
        Button resume = findViewById(R.id.resume);
        Button playagain = findViewById(R.id.playagain);
        Button score = findViewById(R.id.score);
        Button score2 = findViewById(R.id.score2);
        ImageView food = new ImageView(this);
        ImageView snake = new ImageView(this);
        List<ImageView> snakeSegments = new ArrayList<>();
        Handler handler = new Handler();
        AtomicLong delayMillis = new AtomicLong(30L); // Update snake position every 100 milliseconds
        final String[] currentDirection = {"right"}; // Start moving right by default
        AtomicInteger scorex = new AtomicInteger();

        board.setVisibility(View.INVISIBLE);
        playagain.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        score2.setVisibility(View.INVISIBLE);

        newgame.setOnClickListener(v -> {

            board.setVisibility(View.VISIBLE);
            newgame.setVisibility(View.INVISIBLE);
            resume.setVisibility(View.INVISIBLE);
            score2.setVisibility(View.VISIBLE);

            snake.setImageResource(R.drawable.snake);
            snake.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            board.addView(snake);
            snakeSegments.add(snake); // Add the new snake segment to the list

            final float[] snakeX = {snake.getX()};
            final float[] snakeY = {snake.getY()};

            food.setImageResource(R.drawable.snake_food);
            food.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            board.addView(food);

            Random random = new Random(); // create a Random object
            int randomX = random.nextInt(801) - 400; // generate a random x-coordinate between -400 and 400
            int randomY = random.nextInt(801) - 400; // generate a random y-coordinate between -400 and 400

            food.setX(randomX);
            food.setY(randomY);

            Runnable checkFoodCollision = () -> {
                int distanceThreshold = 50;

                double distance = sqrt(pow((snake.getX() - food.getX()), 2) + pow((snake.getY() - food.getY()), 2));

                if (distance < distanceThreshold) { // Check if the distance between the snake head and the food is less than the threshold

                    ImageView newSnake =
                            new ImageView(this); // Create a new ImageView for the additional snake segment
                    newSnake.setImageResource(R.drawable.snake);
                    newSnake.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    board.addView(newSnake);

                    snakeSegments.add(newSnake); // Add the new snake segment to the list

                    int newRandomX = random.nextInt(801) - -100;
                    int newRandomY = random.nextInt(801) - -100;

                    food.setX(newRandomX);
                    food.setY(newRandomY);

                    delayMillis.getAndDecrement();
                    scorex.getAndIncrement();

                    score2.setText("score : " + scorex); // Update delay text view
                }
            };

            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    for (int i = snakeSegments.size() - 1; i >= 1; i--) { // Update the position of each snake segment except for the head
                        snakeSegments.get(i).setX(snakeSegments.get(i - 1).getX());
                        snakeSegments.get(i).setY(snakeSegments.get(i - 1).getY());
                    }

                    switch (currentDirection[0]) {
                        case "up":
                            snakeY[0] -= 10;
                            if (snakeY[0] < -490) { // Check if the ImageView goes off the top of the board
                                snakeY[0] = -490;
                                border.setBackgroundColor(getResources().getColor(R.color.red));
                                playagain.setVisibility(View.VISIBLE);
                                currentDirection[0] = "pause";
                                lilu.setVisibility(View.INVISIBLE);

                                score.setText("your score is  " + scorex); // Update delay text view
                                score.setVisibility(View.VISIBLE);
                                score2.setVisibility(View.INVISIBLE);

                            }

                            snake.setTranslationY(snakeY[0]);
                            break;
                        case "down":
                            snakeY[0] += 10;
                            int maxY = board.getHeight() / 2 - snake.getHeight() + 30; // Calculate the maximum y coordinate
                            if (snakeY[0] > maxY) { // Check if the ImageView goes off the bottom of the board
                                snakeY[0] = maxY;
                                border.setBackgroundColor(getResources().getColor(R.color.red));
                                playagain.setVisibility(View.VISIBLE);
                                currentDirection[0] = "pause";
                                lilu.setVisibility(View.INVISIBLE);

                                score.setText("your score is  " + scorex); // Update delay text view
                                score.setVisibility(View.VISIBLE);
                                score2.setVisibility(View.INVISIBLE);

                            }
                            snake.setTranslationY(snakeY[0]);
                            break;
                        case "left":
                            snakeX[0] -= 10;
                            if (snakeX[0] < -490) { // Check if the ImageView goes off the top of the board
                                snakeX[0] = -490;
                                border.setBackgroundColor(getResources().getColor(R.color.red));
                                playagain.setVisibility(View.VISIBLE);
                                currentDirection[0] = "pause";
                                lilu.setVisibility(View.INVISIBLE);
                                score.setText("your score is  " + scorex); // Update delay text view
                                score.setVisibility(View.VISIBLE);
                                score2.setVisibility(View.INVISIBLE);
                            }
                            snake.setTranslationX(snakeX[0]);
                            break;
                        case "right":
                            snakeX[0] += 10;
                            int maxX = board.getHeight() / 2 - snake.getHeight() + 30; // Calculate the maximum y coordinate
                            if (snakeX[0] > maxX) { // Check if the ImageView goes off the bottom of the board
                                snakeX[0] = maxX;
                                border.setBackgroundColor(getResources().getColor(R.color.red));
                                playagain.setVisibility(View.VISIBLE);
                                currentDirection[0] = "pause";
                                lilu.setVisibility(View.INVISIBLE);

                                score.setText("your score is  " + scorex); // Update delay text view
                                score.setVisibility(View.VISIBLE);
                                score2.setVisibility(View.INVISIBLE);
                            }
                            snake.setTranslationX(snakeX[0]);
                            break;
                        case "pause":
                            snakeX[0] += 0;
                            snake.setTranslationX(snakeX[0]);
                            break;
                    }

                    checkFoodCollision.run();
                    handler.postDelayed(this, delayMillis.get());
                }
            };

            handler.postDelayed(runnable, delayMillis.get());

            // Set button onClickListeners to update the currentDirection variable when pressed
            upButton.setOnClickListener(v1 -> currentDirection[0] = "up");
            downButton.setOnClickListener(v12 -> currentDirection[0] = "down");
            leftButton.setOnClickListener(v13 -> currentDirection[0] = "left");
            rightButton.setOnClickListener(v14 -> currentDirection[0] = "right");
            pauseButton.setOnClickListener(v15 -> {
                currentDirection[0] = "pause";
                board.setVisibility(View.INVISIBLE);
                newgame.setVisibility(View.VISIBLE);
                resume.setVisibility(View.VISIBLE);
            });
            resume.setOnClickListener(v16 -> {
                currentDirection[0] = "right";
                board.setVisibility(View.VISIBLE);
                newgame.setVisibility(View.INVISIBLE);
                resume.setVisibility(View.INVISIBLE);
            });
            playagain.setOnClickListener(v17 -> recreate());
        });
    }
}