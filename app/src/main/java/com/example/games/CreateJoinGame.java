package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.net.Inet4Address;

public class CreateJoinGame extends AppCompatActivity {


    private EditText code;
    private Button create, join;

    private CardView cardView, cardView2;

    boolean isCodeMaker=true;
    boolean codeFound=false;
    boolean checkTemp=true;
    String Code="null";
    String keyValue="null";
    String location="null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join_game);

        code = findViewById(R.id.code);
        join = findViewById(R.id.join);
        create = findViewById(R.id.create);

        cardView = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardView2);
        cardView2.setVisibility(View.INVISIBLE);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Code = "null";
                Code = code.getText().toString();
                if (!Code.equals("null") && !Code.isEmpty()) {
                    FirebaseDatabase.getInstance().getReference("HangmanDB").child(Code).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()&&dataSnapshot.getValue(String.class).equals(Code))
                            {
                                    Toast.makeText(CreateJoinGame.this, "Code already exists!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                    HangmanDB hangmanDB = new HangmanDB("null","waiting");
                                    FirebaseDatabase.getInstance().getReference("HangmanDB").child(Code).setValue(hangmanDB);
                                    accepted(Code);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    Toast.makeText(CreateJoinGame.this, "Enter a valid Code!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Code = "null";
                Code = code.getText().toString();
                if (!Code.equals("null") && !Code.isEmpty()) {
                    FirebaseDatabase.getInstance().getReference("HangmanDB").child(Code).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()&&dataSnapshot.getValue(String.class).equals(Code)) {
                                String value = dataSnapshot.child("status").getValue(String.class);
                                if (value.equals("waiting"))
                                {
                                    cardView.setVisibility(View.INVISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                } else if(value.equals("started")){
                                   Intent intent=new Intent(CreateJoinGame.this, HangmanGameOnline.class);
                                   startActivity(intent);
                                }
                            }
                            else
                            {
                                Toast.makeText(CreateJoinGame.this, "Enter a Valid Code!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    Toast.makeText(CreateJoinGame.this, "Enter a valid Code!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void accepted(String Code)
    {
        Intent intent =new Intent(CreateJoinGame.this, HangmanOnline.class);
        intent.putExtra("Code", Code);
        startActivity(intent);
    }
}