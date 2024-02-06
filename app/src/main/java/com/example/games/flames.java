package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.UUID;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class flames extends AppCompatActivity {
    private ImageView imageView, fNotLit, lNotLit, aNotLit, mNotLit, eNotLit, sNotLit, fLit, lLit, aLit, mLit, eLit, sLit, resetButton, imageView19;
    EditText name1, name2;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flames);
        name1=findViewById(R.id.name1);
        name2=findViewById(R.id.name2);
        imageView=findViewById(R.id.imageView);
        imageView.setImageDrawable(null);
        fLit=findViewById(R.id.fLit);
        lLit=findViewById(R.id.lLit);
        aLit=findViewById(R.id.aLit);
        mLit=findViewById(R.id.mLit);
        eLit=findViewById(R.id.eLit);
        sLit=findViewById(R.id.sLit);
        resetButton=findViewById(R.id.resetButton);
        imageView19=findViewById(R.id.imageView19);
        fNotLit=findViewById(R.id.fNotLit);
        lNotLit=findViewById(R.id.lNotLit);
        aNotLit=findViewById(R.id.aNotLit);
        mNotLit=findViewById(R.id.mNotLit);
        eNotLit=findViewById(R.id.eNotLit);
        sNotLit=findViewById(R.id.sNotLit);
        fLit.setImageDrawable(null);
        lLit.setImageDrawable(null);
        aLit.setImageDrawable(null);
        mLit.setImageDrawable(null);
        eLit.setImageDrawable(null);
        sLit.setImageDrawable(null);





        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                fLit.setImageResource(R.drawable.fnew);
                fNotLit.setImageDrawable(null);
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                lLit.setImageResource(R.drawable.lnew);
                lNotLit.setImageDrawable(null);
            }
        }, 1300);

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                aLit.setImageResource(R.drawable.anew);
                aNotLit.setImageDrawable(null);
            }
        }, 1600);

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                mLit.setImageResource(R.drawable.mnew);
                mNotLit.setImageDrawable(null);
            }
        }, 1900);

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                eLit.setImageResource(R.drawable.enew);
                eNotLit.setImageDrawable(null);
            }
        }, 2200);

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                sLit.setImageResource(R.drawable.snew);
                sNotLit.setImageDrawable(null);
            }
        }, 2500);




        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1.setText("");
                name2.setText("");
                imageView.setImageDrawable(null);
                fNotLit.setImageDrawable(null);
                lNotLit.setImageDrawable(null);
                aNotLit.setImageDrawable(null);
                mNotLit.setImageDrawable(null);
                eNotLit.setImageDrawable(null);
                sNotLit.setImageDrawable(null);
                fLit.setImageResource(R.drawable.fnew);
                lLit.setImageResource(R.drawable.lnew);
                aLit.setImageResource(R.drawable.anew);
                mLit.setImageResource(R.drawable.mnew);
                eLit.setImageResource(R.drawable.enew);
                sLit.setImageResource(R.drawable.snew);
            }
        });

        imageView19.setOnClickListener(new View.OnClickListener()
        {

            public void setNull()
            {
                fLit.setImageDrawable(null);
                lLit.setImageDrawable(null);
                aLit.setImageDrawable(null);
                mLit.setImageDrawable(null);
                eLit.setImageDrawable(null);
                sLit.setImageDrawable(null);
            }

            public void setNotNUll()
            {
                fNotLit.setImageResource(R.drawable.f);
                lNotLit.setImageResource(R.drawable.l);
                aNotLit.setImageResource(R.drawable.a);
                mNotLit.setImageResource(R.drawable.m);
                eNotLit.setImageResource(R.drawable.e);
                sNotLit.setImageResource(R.drawable.s);
            }

            @Override
            public void onClick(View view)
            {
                String a = name1.getText().toString();
                String b = name2.getText().toString();
                hideKeyboard();
                String result="";
                if(a.isEmpty()||b.isEmpty())
                    Toast.makeText(getApplicationContext(), "Field Empty", Toast.LENGTH_SHORT).show();
                else {
                    int n1 = a.length();
                    a = a.toUpperCase();
                    b = b.toUpperCase();
                    a = a.trim();
                    b = b.trim();
                    int n2 = b.length();
                    int f = 0;
                    String s = "FLAMES";
                    String w = "";
                    char e[] = a.toCharArray();
                    char c[] = b.toCharArray();
                    for (int i = 0; i < n1; i++) {
                        for (int j = 0; j < n2; j++) {
                            if (e[i] == c[j]) {
                                e[i] = '1';
                                c[j] = '0';
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < n1; i++) {
                        if (e[i] != '1')
                            f++;
                    }
                    for (int i = 0; i < n2; i++) {
                        if (c[i] != '0')
                            f++;
                    }
                    int l = 6;
                    int k = 0;
                    int p = 0;
                    while (p != 6) {
                        w = "";
                        for (int i = f % (6 - k); i < l; i++) {
                            w += s.charAt(i);
                        }
                        for (int i = 0; i < (f % (6 - k) - 1); i++) {
                            w += s.charAt(i);
                        }
                        s = w;
                        k++;
                        l--;
                        p++;
                    }
                    if (w.equals("F")) {
                        setNull();
                        setNotNUll();
                        fLit.setImageResource(R.drawable.fnew);
                        fNotLit.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.friends);
                        result="Friends";
                    } else if (w.equals("L")) {
                        setNull();
                        setNotNUll();
                        lLit.setImageResource(R.drawable.lnew);
                        lNotLit.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.lover);
                        result="Lover";
                    } else if (w.equals("A")) {
                        setNull();
                        setNotNUll();
                        aLit.setImageResource(R.drawable.anew);
                        aNotLit.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.affection);
                        result="Affection";
                    } else if (w.equals("M")) {
                        setNull();
                        setNotNUll();
                        mLit.setImageResource(R.drawable.mnew);
                        mNotLit.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.marriage);
                        result="Marriage";
                    } else if (w.equals("E")) {
                        setNull();
                        setNotNUll();
                        eLit.setImageResource(R.drawable.enew);
                        eNotLit.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.enemy);
                        result="Enemy";
                    } else {
                        setNull();
                        setNotNUll();
                        sLit.setImageResource(R.drawable.snew);
                        sNotLit.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.sister);
                        result="Sister";
                    }
                }
                FlamesDB flames = new FlamesDB(a,b, result);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("FlamesDB");
                String uniqueId=a+"___"+UUID.randomUUID().toString();
                reference.child(uniqueId).setValue(flames);
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