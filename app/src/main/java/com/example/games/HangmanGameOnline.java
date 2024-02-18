package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Handler;
import android.os.Looper;



public class HangmanGameOnline extends AppCompatActivity {


    int activeUser=1;


    private ImageView start, reset, enterLetter, youwon, youlost, rope, lefthand, leftleg, righthand, rightleg, head, body, standPole, standHead, standBase;

    private ProgressBar progressBar;

    private TextView timer, wordShow, hintTextView, letters, lettersEntered, hintHint;
    private EditText guessLetter;

    String result="";

    String word="harsh", time1="00", time2="30", hint="null";
    CountDownTimer countDownTimer;

    int f=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_game_online);

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility((View.VISIBLE));

        timer=findViewById(R.id.timer);
        start=findViewById(R.id.start);
        standPole=findViewById(R.id.standPole);
        standHead=findViewById(R.id.standHead);
        standBase=findViewById(R.id.standBase);
        hintHint=findViewById(R.id.hintHint);

        timer.setVisibility(View.INVISIBLE);
        start.setVisibility(View.INVISIBLE);
        standPole.setVisibility(View.INVISIBLE);
        standHead.setVisibility(View.INVISIBLE);
        standBase.setVisibility(View.INVISIBLE);
        hintHint.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        String Code = intent.getStringExtra("Code");

        FirebaseDatabase.getInstance().getReference().child("HangmanDB").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(Code)) {
                        word = snapshot.child("word").getValue().toString();
                        time1 = snapshot.child("time1").getValue().toString();
                        time2 = snapshot.child("time2").getValue().toString();
                        hint = snapshot.child("hint").getValue().toString();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar=findViewById(R.id.progressBar);
                start=findViewById(R.id.start);
                timer=findViewById(R.id.timer);
                standPole=findViewById(R.id.standPole);
                standHead=findViewById(R.id.standHead);
                standBase=findViewById(R.id.standBase);
                wordShow=findViewById(R.id.wordShow);
                youwon=findViewById(R.id.youwon);
                youlost=findViewById(R.id.youlost);
                enterLetter=findViewById(R.id.enterLetter);
                reset=findViewById(R.id.reset2);
                hintHint=findViewById(R.id.hintHint);

                progressBar.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                standPole.setVisibility(View.VISIBLE);
                standHead.setVisibility(View.VISIBLE);
                standBase.setVisibility(View.VISIBLE);
                hintHint.setVisibility(View.VISIBLE);

                if(hint.equals("null"))
                    hintHint.setText("HINT NOT PROVIDED");
                else
                    hintHint.setText("HINT PROVIDED");


                rope=findViewById(R.id.rope);
                head=findViewById(R.id.head);
                lefthand=findViewById(R.id.lefthand);
                leftleg=findViewById(R.id.leftleg);
                righthand=findViewById(R.id.righthand);
                rightleg=findViewById(R.id.rightleg);
                body=findViewById(R.id.body);

                wordShow.setVisibility(View.INVISIBLE);
                youwon.setVisibility(View.INVISIBLE);
                youlost.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.INVISIBLE);
                rope.setVisibility(View.INVISIBLE);
                head.setVisibility(View.INVISIBLE);
                righthand.setVisibility(View.INVISIBLE);
                rightleg.setVisibility(View.INVISIBLE);
                lefthand.setVisibility(View.INVISIBLE);
                leftleg.setVisibility(View.INVISIBLE);
                body.setVisibility(View.INVISIBLE);

                enterLetter.setEnabled(false);
                reset.setEnabled(false);


                char[] wordArray = word.toCharArray();
                for(int i=0;i<wordArray.length;i++)
                {
                    if(wordArray[i]=='a'||wordArray[i]=='e'||wordArray[i]=='i'||wordArray[i]=='o'||wordArray[i]=='u')
                        result=result+wordArray[i]+" ";
                    else if(wordArray[i]==' ')
                    {
                        result=result+"/"+" ";
                    }
                    else
                        result=result+"_ ";
                }
                wordShow.setText(result);

                String timeFinal=time1+":"+time2;
                timer.setText(timeFinal);

                start.setOnClickListener(new View.OnClickListener() {               //starting the game
                    @Override
                    public void onClick(View v) {
                        startTimer(time1, time2, word, hint);
                    }
                });

            }
        }, 2000);



    }

    private void startTimer(String time1, String time2, String word, String hint)
    {

        wordShow=findViewById(R.id.wordShow);
        hintHint=findViewById(R.id.hintHint);

        hintHint.setVisibility(View.INVISIBLE);
        wordShow.setVisibility(View.VISIBLE);

        if (countDownTimer != null) {
            countDownTimer.cancel();                                // Stop the previous timer if it exists
        }


        int minutes = Integer.parseInt(time1);
        int seconds = Integer.parseInt(time2);

        long timeInMillis = (minutes * 60 + seconds) * 1000;
        CountDownTimer countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timer.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
            }

            @Override
            public void onFinish() {                                    // timer has run down
                timer.setText("00:00");
                reset=findViewById(R.id.reset2);
                reset.setVisibility(View.VISIBLE);
                reset.setEnabled(true);
                enterLetter=findViewById(R.id.enterLetter);
                enterLetter.setEnabled(false);
                enterLetter.setVisibility(View.INVISIBLE);
                guessLetter=findViewById(R.id.guessLetter);
                guessLetter.setVisibility(View.INVISIBLE);
                lettersEntered=findViewById(R.id.lettersEntered);
                lettersEntered.setVisibility(View.INVISIBLE);
                letters=findViewById(R.id.letters);
                letters.setVisibility(View.INVISIBLE);
                Toast.makeText(HangmanGameOnline.this, "Time Up!", Toast.LENGTH_SHORT).show();
                hideKeyboard();
                hintTextView=findViewById(R.id.hintTextView);
                if(hintTextView.getVisibility()==View.VISIBLE)
                    hintTextView.setVisibility(View.INVISIBLE);
                youlost = findViewById(R.id.youlost);
                youlost.setVisibility(View.VISIBLE);
                char resultArray[]=result.toCharArray();
                int changeColour[]=new int[word.length()];
                int s=0;
                int color = Color.RED;
                for(int i=0;i<word.length();i++)
                {
                    if(resultArray[2*i]!=word.charAt(i))
                    {
                        resultArray[2*i]=word.charAt(i);
                        changeColour[s] = 2 * i;
                        s++;
                    }
                }
                SpannableString spannableString = new SpannableString(new String(resultArray));
                for(int i=0;i<s;i++)
                {
                    spannableString.setSpan(new ForegroundColorSpan(color), changeColour[i], changeColour[i] + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                wordShow.setText(spannableString);
                reset.setOnClickListener(new View.OnClickListener() {               //reset button which takes the user to previous activity after timer has run down
                    @Override
                    public void onClick(View view) {
                        Intent intent1=new Intent(HangmanGameOnline.this, hangman.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        };

        countDownTimer.start();
        start=findViewById(R.id.start);
        start.setVisibility(View.INVISIBLE);
        start.setEnabled(false);
        letters=findViewById(R.id.letters);
        letters.setVisibility(View.VISIBLE);
        guessLetter=findViewById(R.id.guessLetter);
        guessLetter.setVisibility(View.VISIBLE);
        char resultArray[]=result.toCharArray();
        enterLetter=findViewById(R.id.enterLetter);
        enterLetter.setVisibility(View.VISIBLE);
        enterLetter.setEnabled(true);
        int e=0;
        enterLetter.setOnClickListener(new View.OnClickListener()
        {                                                                   // enter button is clicked and the letter guessed is taken as input
            @Override
            public void onClick(View view) {
                lettersEntered=findViewById(R.id.lettersEntered);
                String showLetters=lettersEntered.getText().toString();
                char c[] = guessLetter.getText().toString().toCharArray();    // letter guessed is taken as input and stored as character array c
                if (c.length == 0)
                    Toast.makeText(HangmanGameOnline.this, "Enter a letter", Toast.LENGTH_SHORT).show();
                else
                {
                    guessLetter.setText(null);
                    int e = 0;
                    for (int i = 0; i < word.length(); i++)
                    {
                        if (word.charAt(i) == c[0])                                // looking if the entered letter is present in the given word
                        {
                            e++;
                            resultArray[i * 2] = c[0];                             // if yes, storing the resultArray, which is new text to be displayed, with letters instead of dashes
                        }
                    }
                    if (e == 0)                                                       // if letter is not present
                    {
                        if(showLetters.indexOf(c[0])==-1) {
                            showLetters = showLetters + c[0];
                            lettersEntered.setText(showLetters);
                        }
                        if (f == 0)
                        {
                            rope = findViewById(R.id.rope);
                            rope.setVisibility(View.VISIBLE);
                            f++;
                        }
                        else if (f == 1)
                        {
                            head = findViewById(R.id.head);
                            head.setVisibility(View.VISIBLE);
                            f++;
                        }
                        else if (f == 2)
                        {
                            body = findViewById(R.id.body);
                            body.setVisibility(View.VISIBLE);
                            f++;
                        }
                        else if (f == 3)
                        {
                            righthand = findViewById(R.id.righthand);
                            righthand.setVisibility(View.VISIBLE);
                            f++;
                        }
                        else if (f == 4)
                        {
                            rightleg = findViewById(R.id.rightleg);
                            rightleg.setVisibility(View.VISIBLE);
                            hintTextView=findViewById(R.id.hintTextView);
                            if(!hint.equals("null"))
                            {
                                hideKeyboard();
                                hintTextView.setVisibility(View.VISIBLE);
                                hintTextView.setText("Hint: "+hint);
                            }
                            f++;
                        }
                        else if (f == 5)
                        {
                            lefthand = findViewById(R.id.lefthand);
                            lefthand.setVisibility(View.VISIBLE);
                            f++;
                        }
                        else
                        {                                                          //game over by wrong guesses, showing "you lost" and revealing the reset button
                            hideKeyboard();
                            guessLetter=findViewById(R.id.guessLetter);
                            guessLetter.setVisibility(View.INVISIBLE);
                            enterLetter.setEnabled(false);
                            lettersEntered=findViewById(R.id.lettersEntered);
                            lettersEntered.setVisibility(View.INVISIBLE);
                            letters=findViewById(R.id.letters);
                            letters.setVisibility(View.INVISIBLE);
                            enterLetter.setVisibility(View.INVISIBLE);
                            hintTextView=findViewById(R.id.hintTextView);
                            hintTextView.setVisibility(View.INVISIBLE);
                            leftleg = findViewById(R.id.leftleg);
                            leftleg.setVisibility(View.VISIBLE);
                            youlost = findViewById(R.id.youlost);
                            youlost.setVisibility(View.VISIBLE);
                            reset = findViewById(R.id.reset2);
                            reset.setVisibility(View.VISIBLE);
                            reset.setEnabled(true);
                            int changeColour[]=new int[word.length()];
                            int s=0;
                            int color = Color.RED;
                            for(int i=0;i<word.length();i++)
                            {
                                if(resultArray[2*i]!=word.charAt(i))
                                {
                                    resultArray[2*i]=word.charAt(i);
                                    changeColour[s] = 2 * i;
                                    s++;
                                }
                            }
                            SpannableString spannableString = new SpannableString(new String(resultArray));
                            for(int i=0;i<s;i++)
                            {
                                spannableString.setSpan(new ForegroundColorSpan(color), changeColour[i], changeColour[i] + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                            wordShow.setText(spannableString);
                            reset.setOnClickListener(new View.OnClickListener() {                   // when after losing, the reset button is clicked, it takes back to the previous activity
                                @Override
                                public void onClick(View view) {
                                    Intent intent1 = new Intent(HangmanGameOnline.this, hangman.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            });
                            countDownTimer.cancel();
                        }
                    }
                    else
                    {
                        result = new String(resultArray);
                        wordShow.setText(result);
                        int d = 0;
                        for (int i = 0; i < result.length(); i++)                   //checking for number of dashes
                        {
                            if (result.charAt(i) != '_')
                                d++;
                        }
                        if (d == result.length())                       // if no dashes are present in the result array, you won the game
                        {
                            hideKeyboard();
                            guessLetter=findViewById(R.id.guessLetter);
                            guessLetter.setVisibility(View.INVISIBLE);
                            lettersEntered=findViewById(R.id.lettersEntered);
                            lettersEntered.setVisibility(View.INVISIBLE);
                            letters=findViewById(R.id.letters);
                            letters.setVisibility(View.INVISIBLE);
                            enterLetter.setEnabled(false);
                            enterLetter.setVisibility(View.INVISIBLE);
                            hintTextView = findViewById(R.id.hintTextView);
                            hintTextView.setVisibility(View.INVISIBLE);
                            youwon = findViewById(R.id.youwon);
                            youwon.setVisibility(View.VISIBLE);
                            reset.setVisibility(View.VISIBLE);
                            reset.setEnabled(true);
                            reset.setOnClickListener(new View.OnClickListener() {            //reset button revealed, it now takes to previous activity
                                @Override
                                public void onClick(View view) {
                                    Intent intent1 = new Intent(HangmanGameOnline.this, hangman.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            });
                            countDownTimer.cancel();
                        }
                    }
                }
            }
        });
    }

//    public void removeCode()
//    {
//        if(isCodeMaker)
//        {
//            FirebaseDatabase.getInstance().getReference().child("codes").child(keyValue).removeValue();
//        }
//    }

//    @Override
//    public void onBackPressed()
//    {
//        removeCode();
//        if(isCodeMaker)
//        {
//            FirebaseDatabase.getInstance().getReference().child("codes").child(Code).removeValue();
//        }
//        System.exit(0);
//    }

//    public void updateDatabase()
//    {
//        FirebaseDatabase.getInstance().getReference().child("codes").child(Code).push().setValue()
//    }

    private void hideKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

