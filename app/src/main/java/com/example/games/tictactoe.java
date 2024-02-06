package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class tictactoe extends AppCompatActivity {

    private ImageView enterplayersname, enterTic;

    private EditText player1, player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        enterplayersname=findViewById(R.id.enterplayersname);
        enterTic=findViewById(R.id.entertTic);
        player1=findViewById(R.id.player1);
        player2=findViewById(R.id.player2);

        enterTic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerOne=player1.getText().toString();
                String playerTwo=player2.getText().toString();
                if(playerOne.isEmpty()||playerTwo.isEmpty())
                    Toast.makeText(tictactoe.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent=new Intent(tictactoe.this, tictactoegame.class);
                    intent.putExtra("NAME1", playerOne);
                    intent.putExtra("NAME2", playerTwo);
                    startActivity(intent);
                }
            }
        });
    }
}