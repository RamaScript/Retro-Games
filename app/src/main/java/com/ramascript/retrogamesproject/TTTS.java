package com.ramascript.retrogamesproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TTTS extends AppCompatActivity {

    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 9 zero
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    private TextView playerOneName, playerTwoName;
    private LinearLayout playerOneLayout, playerTwoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttts);

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});

        playerOneName = findViewById(R.id.playerOneName);
        playerTwoName = findViewById(R.id.playerTwoName);
        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        playerOneName.setText(getPlayerOneName);
        playerTwoName.setText(getPlayerTwoName);

        image1.setOnClickListener(view -> {
            if (isBoxSelectable(0)) {
                performAction((ImageView) view, 0);
            }
        });

        image2.setOnClickListener(view -> {
            if (isBoxSelectable(1)) {
                performAction((ImageView) view, 1);
            }
        });

        image3.setOnClickListener(view -> {
            if (isBoxSelectable(2)) {
                performAction((ImageView) view, 2);
            }
        });

        image4.setOnClickListener(view -> {
            if (isBoxSelectable(3)) {
                performAction((ImageView) view, 3);
            }
        });

        image5.setOnClickListener(view -> {
            if (isBoxSelectable(4)) {
                performAction((ImageView) view, 4);
            }
        });

        image6.setOnClickListener(view -> {
            if (isBoxSelectable(5)) {
                performAction((ImageView) view, 5);
            }
        });

        image7.setOnClickListener(view -> {
            if (isBoxSelectable(6)) {
                performAction((ImageView) view, 6);
            }
        });

        image8.setOnClickListener(view -> {
            if (isBoxSelectable(7)) {
                performAction((ImageView) view, 7);
            }
        });

        image9.setOnClickListener(view -> {
            if (isBoxSelectable(8)) {
                performAction((ImageView) view, 8);
            }
        });

    }

    private void performAction(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.ttt_x);
            if (checkResults()) {
                TTTSResultDialog resultDialog = new TTTSResultDialog(TTTS.this, playerOneName.getText().toString()
                        + " is a Winner!", TTTS.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else if (totalSelectedBoxes == 9) {
                TTTSResultDialog resultDialog = new TTTSResultDialog(TTTS.this, "Match Draw", TTTS.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;
            }
        } else {
            imageView.setImageResource(R.drawable.ttt_o);
            if (checkResults()) {
                TTTSResultDialog resultDialog = new TTTSResultDialog(TTTS.this, playerTwoName.getText().toString()
                        + " is a Winner!", TTTS.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else if (totalSelectedBoxes == 9) {
                TTTSResultDialog resultDialog = new TTTSResultDialog(TTTS.this, "Match Draw", TTTS.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            playerOneLayout.setBackgroundResource(R.drawable.ttt_border);
            playerTwoLayout.setBackgroundResource(R.drawable.ttts_white_box);
        } else {
            playerTwoLayout.setBackgroundResource(R.drawable.ttt_border);
            playerOneLayout.setBackgroundResource(R.drawable.ttts_white_box);
        }
    }

    private boolean checkResults() {
        boolean response = false;
        for (int[] combination : combinationList) {
            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                response = true;
            }
        }
        return response;
    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}; // 9 zero
        playerTurn = 1;
        totalSelectedBoxes = 1;

        image1.setImageResource(R.drawable.ttts_white_box);
        image2.setImageResource(R.drawable.ttts_white_box);
        image3.setImageResource(R.drawable.ttts_white_box);
        image4.setImageResource(R.drawable.ttts_white_box);
        image5.setImageResource(R.drawable.ttts_white_box);
        image6.setImageResource(R.drawable.ttts_white_box);
        image7.setImageResource(R.drawable.ttts_white_box);
        image8.setImageResource(R.drawable.ttts_white_box);
        image9.setImageResource(R.drawable.ttts_white_box);
    }
}
