package com.midterm.pbl4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Chronometer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Workout extends AppCompatActivity {
    WebView webView;
    TextView textView;
    Button Btnend;
    ImageButton BtnReload;
    String Link;
    TextToSpeech MySpech;
    private long startTime;
    private boolean isRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout);

        startTime = 0;
        isRunning = false;
        startTime = SystemClock.elapsedRealtime();
        isRunning = true;


//        Intent myIntent = getIntent();
//        Bundle myBundle = myIntent.getBundleExtra("localpath");
//        String path = myBundle.getString("path");
//        String timeStart = myBundle.getString("time");

        Btnend = findViewById(R.id.buttonend);
        BtnReload = findViewById(R.id.btnReload);
        textView = findViewById(R.id.Notify);
        webView = findViewById(R.id.webviewStream);
        readFirebase();
        BtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(Link);
            }
        });
        Btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

//                Intent intent = new Intent(Workout.this, MainActivity2.class);
//                startActivity(intent);
            }
        });
        MySpech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result =  MySpech.setLanguage(Locale.forLanguageTag("Việt Nam"));
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Langue not support");
                    }else{

                    }
                }else {
                    Log.e("TTS", "Intialization failed");
                }
            }
        });

    }
    public void speak(){
        String text = textView.getText().toString().trim();
        MySpech.setSpeechRate(1);
        MySpech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    public void onDestroy(){
        if(MySpech != null){
            MySpech.stop();
            MySpech.shutdown();
        }
        super.onDestroy();
    }
    public void WriteData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
    public void readFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/PBL4-AIPT/Notify");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                textView.setText(value);
                speak();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference = database.getReference("LinkStream");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Link = value;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("localpath");
        String path = myBundle.getString("path");
        String timeStart = myBundle.getString("time");
        String totalTime;
        Long endTime = SystemClock.elapsedRealtime();
        Long elapsedTime = endTime - startTime;
        Long h = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        Long m = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsedTime));
        Long s = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime));
        totalTime = h+"h"+m+"min"+s+"s";

        // Đặt biến isRunning thành false
        isRunning = false;
        int count = (int) (Math.random() * 81) + 20;
        LocalTime localTime = LocalTime.now();
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        String timeEnd =hour + ":" + minute;
        History history = new History(count,timeStart,timeEnd,totalTime);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);
        databaseReference.setValue(history);
        DatabaseReference Status = firebaseDatabase.getReference();
        Status.child("/Mode/status").setValue("Off");
        Status.child("/Mode/exercise").setValue("");
        Intent intent = new Intent(Workout.this, MainActivity2.class);
        startActivity(intent);

//        super.onBackPressed();
    }
}