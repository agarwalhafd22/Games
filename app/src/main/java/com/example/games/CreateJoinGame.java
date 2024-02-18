package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateJoinGame extends AppCompatActivity {

    private EditText code;
    private Button create, join;
    private CardView cardView, cardView2;
    String Code=null;

    int flag1=0;
    int flag2=0;

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
                hideKeyboard();
                Code = code.getText().toString().trim();
                if (!Code.isEmpty()) {
                    FirebaseDatabase.getInstance().getReference().child("HangmanDB").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                if (snapshot.getKey().equals(Code)) {
                                    Toast.makeText(CreateJoinGame.this, "Code already exists!", Toast.LENGTH_SHORT).show();
                                    flag1=1;
                                    break;
                                }
                            }
                            if(flag1!=1) {
                                HangmanDB hangmanDB = new HangmanDB("null", "waiting", 1, "0", "0", "null");
                                FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).setValue(hangmanDB);
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
                hideKeyboard();
                Code = code.getText().toString();
                if (!Code.isEmpty()) {
                    FirebaseDatabase.getInstance().getReference().child("HangmanDB").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            flag2=0;
                            for(DataSnapshot snapshot: dataSnapshot.getChildren())
                            {
                                if(snapshot.getKey().equals(Code))
                                {
                                    flag2=1;
                                    if(snapshot.child("playerCount").getValue().toString().equals("2"))
                                    {
                                        Toast.makeText(CreateJoinGame.this, "Max players reached!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else {
                                        FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("playerCount").setValue(2);
                                        if (snapshot.child("status").getValue().toString().equals("waiting")) {
                                            cardView.setVisibility(View.INVISIBLE);
                                            cardView2.setVisibility(View.VISIBLE);
                                        } else {
                                            Intent intent = new Intent(CreateJoinGame.this, HangmanGameOnline.class);
                                            startActivity(intent);
                                        }
                                    }
                                    break;
                                }
                            }
                            if(flag2!=1)
                            {
                                Toast.makeText(CreateJoinGame.this, "Enter a valid Code!", Toast.LENGTH_SHORT).show();
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

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}