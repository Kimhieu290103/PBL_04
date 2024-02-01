package com.midterm.pbl4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    ListView lsview;
    TextView textList;
    ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("localpath");
        String path = myBundle.getString("path");

        textList = findViewById(R.id.tvList);
        textList.setText(path);
        lsview = findViewById(R.id.lsdetail);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        lsview.setAdapter(adapter);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myerf = firebaseDatabase.getReference(path);
        myerf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String list =dataSnapshot.getKey();
                    arrayList.add(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Detail.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        lsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String paths = path +"/"+ arrayList.get(position).toString();
                Intent intent = new Intent(Detail.this, Showresult.class);
                Bundle myBundle = new Bundle();
                myBundle.putString("path", paths);
                intent.putExtra("localpath",myBundle);
                startActivity(intent);
            }
        });
    }

}