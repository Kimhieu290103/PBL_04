package com.midterm.pbl4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class Video extends AppCompatActivity {
    private VideoView videoView;
    private String videoUrl;
    MediaController mediaController;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoView);
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("local");
        String path = myBundle.getString("path");

        // Lấy URL của video từ Firebase Storage
//        videoUrl = "gs://pbl4-aipt.appspot.com/2023-11-05_00-30-29/video/SQUATS.mp4";
        mediaController = new MediaController(this);
        storageReference = FirebaseStorage.getInstance().getReference().child(path + "/video/output.mp4");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                videoView.setVideoURI(uri);
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.start();
            }
        });
    }
}