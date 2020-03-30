package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {
    ListView trans;
    DatabaseReference retrieve;
    List<String> show;
    setbalance ret;
    String names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        trans = (ListView)findViewById(R.id.xxx);

        Intent i = getIntent();
        names = i.getStringExtra("name");

        retrieve = FirebaseDatabase.getInstance().getReference();
        retrieve = retrieve.child(names).child("TRANSACTION HISTORY");

        show = new ArrayList<>();
        final ArrayAdapter<String> setarrayadapter = new ArrayAdapter<String>(Main6Activity.this, android.R.layout.simple_list_item_1, show);

        ret = new setbalance();

        retrieve.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ret = dataSnapshot.getValue(setbalance.class);
                int amnt = ret.getAmount();
                String tags = ret.getTags();
                String x = String.valueOf(amnt);
                String temp = tags + " : " + x;
                show.add(temp);
                trans.setAdapter(setarrayadapter);
                setarrayadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                setarrayadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
