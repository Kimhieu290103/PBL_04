package com.midterm.pbl4.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midterm.pbl4.Detail;
import com.midterm.pbl4.Detail2;
import com.midterm.pbl4.R;
import com.midterm.pbl4.SignUp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoryFragment extends Fragment {
    ListView lsview;
    TextView listDate;
    ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        lsview = view.findViewById(R.id.lsfolder);
        listDate = view.findViewById(R.id.tvListDate);
        listDate.setText("History");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,arrayList);
        lsview.setAdapter(adapter);
        String path = "History";

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myerf = firebaseDatabase.getReference(path);
        myerf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                Long count = snapshot.getChildrenCount();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String list =dataSnapshot.getKey();
                    arrayList.add(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        lsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String paths = path +"/"+ arrayList.get(position).toString();
                Intent intent = new Intent(getActivity(), Detail.class);
                Bundle myBundle = new Bundle();
                myBundle.putString("path", paths);
                intent.putExtra("localpath",myBundle);
                startActivity(intent);
            }
        });
        return view;
    }
}