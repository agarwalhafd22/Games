package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.Arrays;

public class tictactoegame extends AppCompatActivity {

    private ImageView container, image0, image1, image2, image3, image4, image5, image6, image7, image8;
    private TextView player1name, player2name, turn;

    boolean gameActive = true;

    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};


    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;


    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        if (!gameActive) {
            gameReset(view);
            counter = 0;
        }


        if (gameState[tappedImage] == 2) {
            counter++;


            if (counter == 9) {

                gameActive = false;
            }


            gameState[tappedImage] = activePlayer;

            img.setTranslationY(-1000f);

            if (activePlayer == 0) {

                img.setImageResource(R.drawable.x);
                activePlayer = 1;

                Intent intent = getIntent();

                String enteredName2 = intent.getStringExtra("NAME2");
                BreakIterator textViewResult;
                turn.setText(enteredName2+"'s turn");
            } else {
                // set the image of o
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                Intent intent = getIntent();

                String enteredName1 = intent.getStringExtra("NAME1");
                BreakIterator textViewResult;
                turn.setText(enteredName1+"'s turn");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        if (counter > 4) {
            for (int[] winPosition : winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] &&
                        gameState[winPosition[0]] != 2) {
                    flag = 1;

                    String winnerStr="";

                    gameActive = false;
                    if (gameState[winPosition[0]] == 0) {

                        Intent intent = getIntent();
                        String enteredName1 = intent.getStringExtra("NAME1");
                        BreakIterator textViewResult;

                        winnerStr = player1name.getText().toString()+" has won";
                        turn.setText(winnerStr);


                    } else {
                        Intent intent = getIntent();
                        String enteredName2 = intent.getStringExtra("NAME2");
                        BreakIterator textViewResult;

                        winnerStr = player2name.getText().toString()+" has won";
                        turn.setText(winnerStr);
                    }

                }
            }

            if (counter == 9 && flag == 0) {
                turn.setText("Match Draw");
            }
        }
    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        //set all position to Null
        Arrays.fill(gameState, 2);
        // remove all the images from the boxes inside the grid
        ((ImageView) findViewById(R.id.image0)).setImageResource(0);
        ((ImageView) findViewById(R.id.image1)).setImageResource(0);
        ((ImageView) findViewById(R.id.image2)).setImageResource(0);
        ((ImageView) findViewById(R.id.image3)).setImageResource(0);
        ((ImageView) findViewById(R.id.image4)).setImageResource(0);
        ((ImageView) findViewById(R.id.image5)).setImageResource(0);
        ((ImageView) findViewById(R.id.image6)).setImageResource(0);
        ((ImageView) findViewById(R.id.image7)).setImageResource(0);
        ((ImageView) findViewById(R.id.image8)).setImageResource(0);

        Intent intent = getIntent();

        String enteredName1 = intent.getStringExtra("NAME1");
        BreakIterator textViewResult;
        turn.setText(enteredName1 + "'s turn");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoegame);

        container=findViewById(R.id.container);
        player1name=findViewById(R.id.player1name);
        player2name=findViewById(R.id.player2name);
        turn=findViewById(R.id.turn);
        image0=findViewById(R.id.image0);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);
        image5=findViewById(R.id.image5);
        image6=findViewById(R.id.image6);
        image7=findViewById(R.id.image7);
        image8=findViewById(R.id.image8);

        String enteredName1, enteredName2;


        Intent intent = getIntent();

        enteredName1 = intent.getStringExtra("NAME1");
        BreakIterator textViewResult;
        player1name.setText(enteredName1);
        turn.setText(enteredName1+"'s turn");

        enteredName2 = intent.getStringExtra("NAME2");
        player2name.setText(enteredName2);
    }
}