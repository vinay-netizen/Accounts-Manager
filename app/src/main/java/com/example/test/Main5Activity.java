package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main5Activity extends AppCompatActivity {
    int val;
    TextView header, finalres, balancs;
    int temp = 0;
    EditText amount,tag;
    Button save,done;
    int flag;
    String con_name;
    DatabaseReference balanceget, trans;
    setbalance balanz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        header = (TextView)findViewById(R.id.textView6);
        finalres = (TextView)findViewById(R.id.textView12);
        balancs = (TextView)findViewById(R.id.textView3);

        amount = (EditText)findViewById(R.id.editText6);
        tag = (EditText)findViewById(R.id.editText2);

        save = (Button)findViewById(R.id.button6);
        done = (Button)findViewById(R.id.button7);

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag",100);
        con_name = intent.getStringExtra("name");

        balanz = new setbalance();

        if(flag==1)
        {
            header.setText("YOU GAVE:");
        }
        else if(flag==0)
        {
            header.setText("YOU GOT:");
        }

        balanceget = FirebaseDatabase.getInstance().getReference();
        balanceget = balanceget.child(con_name).child("BALANCE AMOUNT");

        trans = FirebaseDatabase.getInstance().getReference();
        trans = trans.child(con_name).child("TRANSACTION HISTORY");

        balanceget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String temporary = dataSnapshot.getValue(String.class);

               if(temporary == null)
               {
                   val = 0;
               }
               else
               {
                   val = Integer.parseInt(temporary);
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x =Integer.parseInt(amount.getText().toString());


                String buf = tag.getText().toString();
                balanz.setTags(buf);
                if(flag==1)
                {
                    temp  = temp - x;
                    int y = -x;
                    balanz.setAmount(y);
                    Toast.makeText(Main5Activity.this,buf + " : " + y,Toast.LENGTH_SHORT).show();
                }

                else if(flag==0)
                {
                    temp = temp + x;
                    balanz.setAmount(x);
                    Toast.makeText(Main5Activity.this,buf + " : " + x,Toast.LENGTH_SHORT).show();
                }

                trans.push().setValue(balanz);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val = val + temp;
                balanceget.setValue(Integer.toString(val));
                balancs.setText(Integer.toString(val));
                temp = 0;
                if(val>0)
                {
                    finalres.setText("YOU HAVE TO GIVE " + con_name + " Rs " + java.lang.Math.abs(val));
                }
                else
                {
                   finalres.setText(con_name + " HAS TO GIVE YOU Rs " + java.lang.Math.abs(val));
                }
            }
        });
    }
}
