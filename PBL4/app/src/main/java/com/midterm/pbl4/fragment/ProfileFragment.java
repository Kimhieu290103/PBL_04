package com.midterm.pbl4.fragment;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.midterm.pbl4.LoginActivity;
import com.midterm.pbl4.MainActivity2;
import com.midterm.pbl4.R;
import com.midterm.pbl4.userInfor;

import java.io.IOException;

public class ProfileFragment extends Fragment  {
    Button logout, save;
    EditText edName, edEmail, edAge,edWeight, edheight ;
    ImageView avatar;
    Uri uri;



    private ContentResolver getContentResolver() {
        return getContentResolver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logout = view.findViewById(R.id.buttonLogout);
        edName = view.findViewById(R.id.mName);
        edEmail = view.findViewById(R.id.s_email);
        edAge = view.findViewById(R.id.mAge);
        edWeight = view.findViewById(R.id.weight);
        edheight = view.findViewById(R.id.myHeight);
        avatar = view.findViewById(R.id.myAvatar);
        save = view.findViewById(R.id.Save);
        showUserInformation();
        showInfor();
        initListener();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAction();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void onLogout() {
        // Đăng xuất người dùng
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
    private void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null){
            edName.setVisibility(View.GONE);
        }else {
            edName.setVisibility(View.VISIBLE);
            edName.setText(name);
        }
        edEmail.setText(email);
        Glide.with((this)).load(photoUrl).error(R.drawable.ic_avatar).into(avatar);
    }
    private void showInfor(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try{
                    userInfor infor = documentSnapshot.toObject(userInfor.class);
                    edAge.setText(infor.getAge().toString());
                    edWeight.setText(infor.getWeight().toString());
                    edheight.setText(infor.getHeight().toString());

                }catch (Exception e){

                }
            }
        });


        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_avatar).into(avatar);
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    private void saveAction() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        String name = edName.getText().toString();
        String Age = edAge.getText().toString();
        String Weight = edWeight.getText().toString();
        String Height = edheight.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String path = "User";
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
//                            UserInfor user1 = new UserInfor(Address,mclass);
//                            user1.put("Address", Address);
//                            user1.put("Class", mclass);
                            userInfor infor = new userInfor(Age,Weight,Height);
                            db.collection("User").document(user.getUid())
                                    .set(infor)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), "Writing Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    }
                });
    }
    private void initListener(){
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }
    public void onClickRequestPermission(){
        MainActivity2 mainActivity2 = (MainActivity2) getActivity();

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            mainActivity2.openGallery();
            return;
        }

        if(getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            mainActivity2.openGallery();
        }else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission, 1);
        }
    }

    public void setBitmapImage(Bitmap bitmapImage){
        avatar.setImageBitmap(bitmapImage);

    }
}