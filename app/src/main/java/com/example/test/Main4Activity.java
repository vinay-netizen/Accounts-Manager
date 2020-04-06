package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {
    String pos;
    List<String> history;

    TextView contactname;

    Button got,gave,showtrans;

    setbalance x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        contactname = (TextView)findViewById(R.id.textView2);

        got = (Button)findViewById(R.id.button2);
        gave = (Button)findViewById(R.id.button3);
        showtrans = (Button)findViewById(R.id.button4);


        final Intent intent =getIntent();
        pos = intent.getStringExtra("CONTACT NAME");
        contactname.setText(pos);
        history = new ArrayList<>();

        x = new setbalance();

       gave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent a = new Intent(Main4Activity.this,Main5Activity.class);
               a.putExtra("flag",1);
               a.putExtra("name",pos);
               Toast.makeText(Main4Activity.this,"YOU GOT!",Toast.LENGTH_SHORT).show();
               Main4Activity.this.startActivity(a);

           }
       });

       got.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent b = new Intent(Main4Activity.this,Main5Activity.class);
               b.putExtra("flag",0);
               b.putExtra("name",pos);
               Toast.makeText(Main4Activity.this,"YOU GAVE!",Toast.LENGTH_SHORT).show();
               Main4Activity.this.startActivity(b);
           }
       });

     showtrans.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             Intent c = new Intent(Main4Activity.this, Main6Activity.class);
             c.putExtra("name",pos);
             startActivity(c);
           }
       });

    }
}
