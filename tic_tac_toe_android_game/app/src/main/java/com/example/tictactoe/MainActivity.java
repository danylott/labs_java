package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button [] buttons = new Button[16];

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;
    boolean isBotActive;

    // p1 => 0
    // p2 => 1
    // empty => 2
    int [] gameState = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}, // rows
            {0, 4, 8, 12}, {1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, // columns
            {0, 5, 10, 15}, {3, 6, 9, 12}, // diagonals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        Button resetGame = (Button) findViewById(R.id.resetGame);
        Button activateBot = (Button) findViewById(R.id.activateBot);

        for(int i = 0; i < buttons.length; i++) {
            String buttonId = "btn_" + i;
            int resourceId =getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
        isBotActive = false;
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();

            }
        });

        activateBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                isBotActive = !isBotActive;
                if(isBotActive) {
                    activateBot.setText("Deactivate Bot");
                } else {
                    activateBot.setText("Activate Bot");
                }
                updatePlayerScore();

            }
        });
    }

    @Override
    public void onClick(View v) {
        // check not empty
        if(!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonId = v.getResources().getResourceEntryName(v.getId());
        // get last character of button name
        int gameStatePointer;
        try {
            gameStatePointer = Integer.parseInt(
                    buttonId.substring(buttonId.length() - 2, buttonId.length()));
        } catch (Exception e) {
            gameStatePointer = Integer.parseInt(
                    buttonId.substring(buttonId.length() - 1, buttonId.length()));
        }

        // if first player
        if(activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else if (!isBotActive) {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        nominateWinner(true);

        if(isBotActive) {
            roundCount++;
            int botPosition = getBotTurnPosition();
            buttons[botPosition].setText("O");
            buttons[botPosition].setTextColor(Color.parseColor("#70FFEA"));
            gameState[botPosition] = 1;
            nominateWinner(false);
        }

        if(playerOneScoreCount > playerTwoScoreCount) {
            playerStatus.setText("Player One is Winning");
        } else if(playerTwoScoreCount > playerOneScoreCount) {
            playerStatus.setText("Player Two is Winning");
        } else {
            playerStatus.setText("");
        }
    }

    public int getRandomEmptyPosition() {
        int randomPosition = Math.abs(new Random().nextInt()) % buttons.length;
        while (gameState[randomPosition] != 2) {
            randomPosition = Math.abs(new Random().nextInt()) % buttons.length;
        }
        return randomPosition;
    }

    public int getBotTurnPosition() {
        if(roundCount < 3) {
            return getRandomEmptyPosition();
        }
        ArrayList<Integer> currentPositions = new ArrayList<>();
        for(int i = 0; i < buttons.length; i++) {
            if(gameState[i] == 1) {
                currentPositions.add(i);
            }
        }
        int bestPosition = -1;
        int maxSimilarPositions = 0;
        for(int i = 0; i < winningPositions.length; i++) {
            int currentSimilarPositions = 0;
            for(int position : winningPositions[i]) {
                if(currentPositions.contains(position)) {
                    currentSimilarPositions++;
                }
            }
            if(currentSimilarPositions > maxSimilarPositions) {
                maxSimilarPositions = currentSimilarPositions;
                bestPosition = i;
            }
        }
        if(bestPosition > 0) {
            for(int possiblePosition : winningPositions[bestPosition]) {
                if(gameState[possiblePosition] == 2) {
                    return possiblePosition;
                }
            }
        }
        return getRandomEmptyPosition();
    }

    public void nominateWinner(boolean isPlayer) {
        if(checkWinner()) {
            if(activePlayer && isPlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player two Won!", Toast.LENGTH_SHORT).show();
            }
            playAgain();

        } else if (roundCount == buttons.length) {
            playAgain();
            Toast.makeText(this, "No winner: Tie!", Toast.LENGTH_SHORT).show();
        } else if(!isBotActive) {
            activePlayer = !activePlayer;
        }
    }

    public boolean checkWinner() {
        for(int [] winningPosition : winningPositions) {
            for(int position = 0; position < winningPosition.length - 1; position++) {
                if (gameState[winningPosition[position]] != gameState[winningPosition[position + 1]])
                    break;
                if (position == winningPosition.length - 2 &&
                        gameState[winningPosition[position]] != 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain() {
        roundCount = 0;
        activePlayer = true;

        for(int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}