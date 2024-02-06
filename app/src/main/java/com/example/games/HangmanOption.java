package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HangmanOption extends AppCompatActivity {

    private Button onlineMode, offlineMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_option);

        onlineMode=findViewById(R.id.onlineMode);
        offlineMode=findViewById(R.id.offlineMode);

        offlineMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HangmanOption.this, hangman.class);
                startActivity(intent);
            }
        });

        onlineMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HangmanOption.this, CreateJoinGame.class);
                startActivity(intent);
            }
        });
    }
}