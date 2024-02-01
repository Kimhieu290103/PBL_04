package com.midterm.pbl4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Showresult extends AppCompatActivity {
    TextView count, total, start, end;
    Button image, back, video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showresult);

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("localpath");
        String path = myBundle.getString("path");

        count = findViewById(R.id.showcount);
        total = findViewById(R.id.showtotal);
        start = findViewById(R.id.showstart);
        end = findViewById(R.id.showend);
        image = findViewById(R.id.showimage);
        back = findViewById(R.id.buttonback);
        video = findViewById(R.id.showvideo);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Showresult.this, Detail2.class);
                startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Showresult.this, Video.class);
                Bundle myBundle = new Bundle();
                myBundle.putString("path",path);
                intent.putExtra("local",myBundle);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myerf = firebaseDatabase.getReference(path);
        myerf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                History history = snapshot.getValue(History.class);
                count.setText(history.getCount().toString());
                total.setText(history.getTotalTime());
                end.setText(history.getTimeEnd());
                start.setText(history.getTimeStart());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}