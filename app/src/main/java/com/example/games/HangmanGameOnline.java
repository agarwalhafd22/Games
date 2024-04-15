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

public class HangmanGameOnline extends AppCompatActivity {


    int userIntent, userDatabase;


    private ImageView start, reset, enterLetter, youwon, youlost, rope, lefthand, leftleg, righthand, rightleg, head, body, standPole, standHead, standBase;

    private ProgressBar progressBar;

    private TextView timer, wordShow, hintTextView, letters, lettersEntered, hintHint;
    private EditText guessLetter;

    String result="";

    String word=null, time1=null, time2=null, hint=null, Code=null, wordOutput=null;
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
        Code = intent.getStringExtra("Code");
        userIntent = intent.getIntExtra("userIntent", 0);




        //retrieving data from firebase database
        FirebaseDatabase.getInstance().getReference().child("HangmanDB").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(Code)) {
                        word = snapshot.child("word").getValue().toString();
                        time1 = snapshot.child("time1").getValue().toString();
                        time2 = snapshot.child("time2").getValue().toString();
                        if(snapshot.child("hint").exists())
                            hint = snapshot.child("hint").getValue().toString();
                        userDatabase = Integer.parseInt(snapshot.child("activeUser").getValue().toString());
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
                guessLetter=findViewById(R.id.guessLetter);
                lettersEntered=findViewById(R.id.lettersEntered);

                progressBar.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                standPole.setVisibility(View.VISIBLE);
                standHead.setVisibility(View.VISIBLE);
                standBase.setVisibility(View.VISIBLE);
                hintHint.setVisibility(View.VISIBLE);

                if(hint==null)
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
                letters=findViewById(R.id.letters);

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

                FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("result").setValue(result);

                String timeFinal=time1+":"+time2;
                timer.setText(timeFinal);

                //starting the game for the both the users when the start button is pressed
                FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("status").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue().equals("guessing"))
                            {
                                    start.setVisibility(View.INVISIBLE);
                                    letters.setVisibility(View.VISIBLE);
                                    guessLetter.setVisibility(View.VISIBLE);
                                    enterLetter.setVisibility(View.VISIBLE);
                                    enterLetter.setEnabled(true);
                                    hintHint.setVisibility(View.INVISIBLE);

                                        if (userIntent==userDatabase) {
                                            start.setEnabled(false);
                                            guessLetter.setEnabled(false);
                                            enterLetter.setEnabled(false);
                                        }

                                    startTimer();
                            }
                            else if(dataSnapshot.getValue().equals("Time Up"))
                            {
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
                            }
                            else if(dataSnapshot.getValue().equals("Game Over"))
                            {
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
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();                                // Stop the previous timer if it exists
                                }
                            }
                            else if(dataSnapshot.getValue().toString().equals("Won"))
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
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();                                // Stop the previous timer if it exists
                                }
                            }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //changing the wordShow
                FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("result").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        wordShow.setText(dataSnapshot.getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //changing the lettersEntered

                FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("lettersEnteredAlready").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        lettersEntered.setText(dataSnapshot.getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (userIntent==userDatabase) {
                    start.setEnabled(false);
                    guessLetter.setEnabled(false);
                    enterLetter.setEnabled(false);
                }

                start.setOnClickListener(new View.OnClickListener() {               //starting the game
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("status").setValue("guessing");
                    }
                });

                FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("hangmanFig").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().toString().equals("0"))
                        {
                            rope = findViewById(R.id.rope);
                            rope.setVisibility(View.VISIBLE);
                        }
                        else if(dataSnapshot.getValue().toString().equals("1"))
                        {
                            head = findViewById(R.id.head);
                            head.setVisibility(View.VISIBLE);
                        }
                        else if(dataSnapshot.getValue().toString().equals("2"))
                        {
                            body = findViewById(R.id.body);
                            body.setVisibility(View.VISIBLE);
                        }
                        else if(dataSnapshot.getValue().toString().equals("3"))
                        {
                            righthand = findViewById(R.id.righthand);
                            righthand.setVisibility(View.VISIBLE);
                        }
                        else if(dataSnapshot.getValue().toString().equals("4"))
                        {
                            rightleg = findViewById(R.id.rightleg);
                            rightleg.setVisibility(View.VISIBLE);
                        }
                        else if(dataSnapshot.getValue().toString().equals("5"))
                        {
                            lefthand = findViewById(R.id.lefthand);
                            lefthand.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                start.setOnClickListener(new View.OnClickListener() {               //starting the game
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("status").setValue("guessing");
                    }
                });

            }
        }, 2000);
    }

    private void startTimer()
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
                FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("status").setValue("Time Up");
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
        char resultArray[]=result.toCharArray();
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
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("lettersEnteredAlready").setValue(showLetters);
                        }
                        if (f == 0)
                        {
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("hangmanFig").setValue(0);
                            f++;
                        }
                        else if (f == 1)
                        {
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("hangmanFig").setValue(1);
                            f++;
                        }
                        else if (f == 2)
                        {
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("hangmanFig").setValue(2);
                            f++;
                        }
                        else if (f == 3)
                        {
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("hangmanFig").setValue(3);
                            f++;
                        }
                        else if (f == 4)
                        {
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("hangmanFig").setValue(4);
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
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("hangmanFig").setValue(5);
                            f++;
                        }
                        else
                        {                                                          //game over by wrong guesses, showing "you lost" and revealing the reset button
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("status").setValue("Game Over");
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
                        }
                    }
                    else
                    {
                        result = new String(resultArray);
                        FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("result").setValue(result);
                        int d = 0;
                        for (int i = 0; i < result.length(); i++)                   //checking for number of dashes
                        {
                            if (result.charAt(i) != '_')
                                d++;
                        }
                        if (d == result.length())                       // if no dashes are present in the result array, you won the game
                        {
                            FirebaseDatabase.getInstance().getReference().child("HangmanDB").child(Code).child("status").setValue("Won");
                            reset.setOnClickListener(new View.OnClickListener() {            //reset button revealed, it now takes to previous activity
                                @Override
                                public void onClick(View view) {
                                    Intent intent1 = new Intent(HangmanGameOnline.this, hangman.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            });
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

