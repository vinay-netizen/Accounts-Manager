package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {
   private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    int val;
    TextView header, finalres, balancs;
    int temp = 0;
    EditText amount,tag;
    Button save,done;
    int flag;
    String con_name;
    DatabaseReference balanceget, trans, getphone;
    setbalance balanz;

    setdeets activity6;

    List<setdeets> arrays;

   String phoneno = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        header = (TextView)findViewById(R.id.textView6);
        finalres = (TextView)findViewById(R.id.textView12);

        amount = (EditText)findViewById(R.id.editText6);
        tag = (EditText)findViewById(R.id.editText2);

        save = (Button)findViewById(R.id.button6);
        done = (Button)findViewById(R.id.button7);

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag",100);
        con_name = intent.getStringExtra("name");

        balanz = new setbalance();

        activity6 = new setdeets();

        if(flag==1)
        {
            header.setText("YOU GAVE:");
        }
        else if(flag==0)
        {
            header.setText("YOU GOT:");
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS))
            {
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

       getphone = FirebaseDatabase.getInstance().getReference();
       getphone = getphone.child("CONTACT DETAILS");

        balanceget = FirebaseDatabase.getInstance().getReference();
        balanceget = balanceget.child(con_name).child("BALANCE AMOUNT");

        trans = FirebaseDatabase.getInstance().getReference();
        trans = trans.child(con_name).child("TRANSACTION HISTORY");

        arrays = new ArrayList<>();

        getphone = FirebaseDatabase.getInstance().getReference("CONTACT DETAILS");
        getphone.addListenerForSingleValueEvent(valueEventListener);


        Query query = FirebaseDatabase.getInstance().getReference("CONTACT DETAILS").orderByChild("person_name").equalTo(con_name);
        query.addListenerForSingleValueEvent(valueEventListener);



       /*getphone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        activity6 = snapshot.getValue(setdeets.class);
                        String tempname = activity6.getPerson_name();
                        if(con_name == tempname)
                        {
                            phoneno = activity6.getPerson_phone();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


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
                SmsManager sms = SmsManager.getDefault();
                val = val + temp;
                balanceget.setValue(Integer.toString(val));
                temp = 0;
                if(val>0)
                {
                    String temp_1,temp1;
                    temp_1 = "YOU HAVE TO GIVE " + con_name + " Rs " + java.lang.Math.abs(val);
                    temp1 = "VINAY HAS TO GIVE YOU Rs " + java.lang.Math.abs(val);
                    finalres.setText(temp_1);
                    sms.sendTextMessage(phoneno, null, temp1, null, null);
                }
                else
                {
                    String temp_2, temp2;
                    temp_2 = con_name + " HAS TO GIVE YOU Rs " + java.lang.Math.abs(val);
                    temp2 = "YOU HAVE TO GIVE VINAY Rs " + java.lang.Math.abs(val);
                   finalres.setText(temp_2);
                    sms.sendTextMessage(phoneno, null, temp2, null, null);
                }
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
            {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    activity6 = snapshot.getValue(setdeets.class);
                    phoneno = activity6.getPerson_phone();
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

   @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "PERMISSION GRANTED",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"NOT PERMITTED", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }
}
