package com.example.test;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main3Activity extends AppCompatActivity {
  Button button,proceed;
    DatabaseReference x,getnamedata,getcontactdata;

    EditText ed1;
    EditText ed2;
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



        button = (Button)findViewById(R.id.button);
        proceed = (Button)findViewById(R.id.proc);

        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText5);

       t1 = (TextView)findViewById(R.id.textView8);
       t2 = (TextView)findViewById(R.id.textView9);

        x = FirebaseDatabase.getInstance().getReference();

        getnamedata =FirebaseDatabase.getInstance().getReference();
        getnamedata = getnamedata.child("NAME");

        getcontactdata = FirebaseDatabase.getInstance().getReference();
        getcontactdata = getcontactdata.child("CONTACTS");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Main3Activity.this,"CONTACT SAVED!",Toast.LENGTH_SHORT).show();
                String name = ed1.getText().toString();
                String phone = ed2.getText().toString();
                getnamedata.push().setValue(name);
                getcontactdata.push().setValue(phone);
                t1.setText(name);
                t2.setText(phone);
            }
        });


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main3Activity.this,"PROCEEDING",Toast.LENGTH_LONG);
                Intent intent = new Intent(Main3Activity.this,Main2Activity.class);
                Main3Activity.this.startActivity(intent);

            }
        });
    }
}
