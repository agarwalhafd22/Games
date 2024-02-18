package com.example.games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;


import com.google.firebase.database.FirebaseDatabase;


public class HangmanOnline extends AppCompatActivity {

    private ImageView imageView2, submit3;
    private TextView howToPlayTextView, closeTextView, gameInstructions;


    private CheckBox giveHintCheckBox;

    private CardView cardView;
    private EditText word, time1, time2, hintEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_online);

        submit3=findViewById(R.id.submit3);
        word=findViewById(R.id.word);
        time1=findViewById(R.id.time1);
        time2=findViewById(R.id.time2);
        howToPlayTextView=findViewById(R.id.howToPlayTextView);
        gameInstructions=findViewById(R.id.gameInstructions);
        giveHintCheckBox=findViewById(R.id.giveHintCheckBox);
        hintEditText=findViewById(R.id.hintEditText);
        closeTextView=findViewById(R.id.closeTextView);
        closeTextView.setVisibility(View.INVISIBLE);
        gameInstructions.setVisibility(View.INVISIBLE);

        Intent inTent = getIntent();
        String Code = inTent.getStringExtra("Code");


        howToPlayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.setEnabled(false);
                time1.setEnabled(false);
                time2.setEnabled(false);
                giveHintCheckBox.setEnabled(false);
                hintEditText.setEnabled(false);
                submit3.setEnabled(false);
                gameInstructions.setVisibility(View.VISIBLE);
                closeTextView.setVisibility(View.VISIBLE);
            }
        });

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.setEnabled(true);
                time1.setEnabled(true);
                time2.setEnabled(true);
                giveHintCheckBox.setEnabled(true);
                hintEditText.setEnabled(true);
                submit3.setEnabled(true);
                gameInstructions.setVisibility(View.INVISIBLE);
                closeTextView.setVisibility(View.INVISIBLE);
            }
        });

        giveHintCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    hintEditText.setVisibility(View.VISIBLE);
                }
                else
                {
                    hintEditText.setVisibility(View.INVISIBLE);
                }
            }
        });

        submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String wordHangman=word.getText().toString().toLowerCase();
                wordHangman=wordHangman.trim();
                String timeOne=time1.getText().toString();
                String timeTwo=time2.getText().toString();
                String hint="null";

                if(wordHangman.isEmpty()||timeOne.isEmpty()||timeTwo.isEmpty()||hintEditText.getVisibility()==View.VISIBLE&&hintEditText.getText().toString().isEmpty())
                    Toast.makeText(HangmanOnline.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                else if(Integer.parseInt(timeTwo)>59)
                    Toast.makeText(HangmanOnline.this, "Enter correct duration", Toast.LENGTH_SHORT).show();
                else {
                    if(hintEditText.getVisibility()==View.VISIBLE) {
                        hint=hintEditText.getText().toString();
                    }
                    HangmanDB hangmanDB = new HangmanDB(wordHangman,"started", 1, timeOne, timeTwo, hint, "none", null);
                    FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).setValue(hangmanDB);
                    word.setText(null);
                    time1.setText(null);
                    time2.setText(null);
                    hintEditText.setText(null);
                    Intent intent = new Intent(HangmanOnline.this, HangmanGameOnline.class);
                    intent.putExtra("Code", Code);
                    startActivity(intent);
                }
            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}