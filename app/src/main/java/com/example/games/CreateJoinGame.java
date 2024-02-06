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

public class CreateJoinGame extends AppCompatActivity {

    private static final int REQUEST_CODE_SECOND_ACTIVITY = 1;

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

        code=findViewById(R.id.code);
        join=findViewById(R.id.join);
        create=findViewById(R.id.create);

        cardView=findViewById(R.id.cardView);
        cardView2=findViewById(R.id.cardView2);
        cardView2.setVisibility(View.INVISIBLE);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Code = "null";
                codeFound=false;
                checkTemp=true;
                keyValue="null";
                Code=code.getText().toString();
                if(!Code.equals("null")&&!Code.isEmpty())
                {
                    isCodeMaker=true;
                    FirebaseDatabase.getInstance().getReference().child("codes").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean check = isValueAvailable(snapshot, Code);
                            if(!check)
                            {
                                FirebaseDatabase.getInstance().getReference().child("codes").push().setValue(Code);
                                isValueAvailable(snapshot, Code);
                                checkTemp=false;
                                accepted();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(CreateJoinGame.this, "Please enter a valid code!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Code = "null";
                codeFound=false;
                checkTemp=true;
                keyValue="null";
                Code = code.getText().toString();
                if(!Code.equals("null")&&!Code.isEmpty())
                {
                    FirebaseDatabase.getInstance().getReference().child("codes").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean data = isValueAvailable(snapshot, Code);
                            if(data)
                            {
                                codeFound=true;
                                        cardView.setVisibility(View.INVISIBLE);
                                        cardView2.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Toast.makeText(CreateJoinGame.this, "Invalid Code!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(CreateJoinGame.this, "Please enter a valid code!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void accepted()
    {
        Intent intent =new Intent(CreateJoinGame.this, HangmanOnline.class);
        startActivity(intent);
    }

    public boolean isValueAvailable(DataSnapshot snapshot, String code) {
        Iterable<DataSnapshot> data = snapshot.getChildren();

        for (DataSnapshot dataSnapshot : data) {
            String value = dataSnapshot.getValue().toString();
            if (value.equals(Code)) {
                keyValue = dataSnapshot.getKey().toString();
                return true;
            }
        }
        return false;
    }
}