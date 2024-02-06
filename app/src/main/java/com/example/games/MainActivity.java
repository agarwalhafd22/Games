package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView flames_button, tictactoe_button, hangman_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flames_button=findViewById(R.id.flames_button);
        tictactoe_button=findViewById(R.id.tictactoe_button);
        hangman_button=findViewById(R.id.hangman_button);

        flames_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, flames.class);
                startActivity(intent);
            }
        });

        tictactoe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, tictactoe.class);
                startActivity(intent);
            }
        });

        hangman_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HangmanOption.class);
                startActivity(intent);
            }
        });
    }
}