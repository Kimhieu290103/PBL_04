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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.midterm.pbl4.History;
import com.midterm.pbl4.MainActivity2;
import com.midterm.pbl4.R;
import com.midterm.pbl4.SignUp;
import com.midterm.pbl4.Workout;

import java.time.LocalTime;
import java.util.Calendar;

public class HomeFragment extends Fragment{
    Button Start;

    Long countkey;
    Spinner exercise;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Start = view.findViewById(R.id.btnStart);
        exercise = view.findViewById(R.id.spinner_exercise);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.exercise, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exercise.setAdapter(arrayAdapter);

        String path = "History/";
        Calendar calendar = Calendar.getInstance();
        path += calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);


        DatabaseReference myerf = firebaseDatabase.getReference(path);
        myerf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countkey = snapshot.getChildrenCount();
                if(countkey == null){
                    countkey = new Long(1);
                }else {
                    countkey += 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "History/";
                Calendar calendar = Calendar.getInstance();
                path += calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

                LocalTime localTime = LocalTime.now();
                int hour = localTime.getHour();
                int minute = localTime.getMinute();
                String timestart =hour + ":" + minute;


                History history = new History(0,timestart,"","0");
                String paa = path + "/" + countkey;
                DatabaseReference databaseReference = firebaseDatabase.getReference(paa);
                databaseReference.setValue(history);

                DatabaseReference Status = firebaseDatabase.getReference("/Mode/status");
                Status.setValue("On");


                Intent intent = new Intent(getActivity(), Workout.class);
                Bundle bundle = new Bundle();
                bundle.putString("path", paa);
                bundle.putString("time", timestart);
                intent.putExtra("localpath",bundle);
                startActivity(intent);
            }
        });

        exercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),"Chosen: " + text, Toast.LENGTH_SHORT).show();

                DatabaseReference databaseReference = firebaseDatabase.getReference("/Mode/exercise");
                databaseReference.setValue(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                DatabaseReference databaseReference = firebaseDatabase.getReference("/Mode/exercise");
                databaseReference.setValue("");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}