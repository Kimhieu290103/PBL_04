package com.midterm.pbl4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Detail2 extends AppCompatActivity {
    String[] Urls = new String[]{"https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-01.jpg?alt=media&token=8888228a-6f98-48ab-a3a0-ca84f03481be",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-02.jpg?alt=media&token=bca65c50-5b0e-434c-8b0f-e4ec740cd664",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-03.jpg?alt=media&token=d94ffa1c-e23e-45d3-9027-3dc78284dd19",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-04.jpg?alt=media&token=27ecb511-8f60-434e-ad41-ccde4529ace6",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-05.jpg?alt=media&token=b2ae04b8-a260-48d9-8959-1d5b8e1b480d",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-06.jpg?alt=media&token=736dff39-d8e7-4884-b4ad-864ba9e5610d",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-07.jpg?alt=media&token=d050d04a-097f-4667-8d0d-aa06d6634893",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-08.jpg?alt=media&token=2c351490-a8b1-40e9-a71b-19480fc78ac0",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-09.jpg?alt=media&token=01e267a7-e483-4d14-8405-d8c71aedb4b5",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FFoot_Tight_At_00-00-10.jpg?alt=media&token=bc6a0934-5674-4dc0-9383-7d09c202fbb2",
            "https://firebasestorage.googleapis.com/v0/b/pbl4-aipt.appspot.com/o/2023-11-05_00-30-29%2Fimage%2FKnee_Wide_At_00-00-00.jpg?alt=media&token=be61e9e6-ae5b-4d91-b0dd-c8718bccf6bc"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        List<String> imageUrls = new ArrayList<>();
        List<String> itemTexts = new ArrayList<>();
        for(int i = 1; i<11; i++){
            imageUrls.add(Urls[i]);

        }
        itemTexts.add("Image 1");
        itemTexts.add("Image 2");
        itemTexts.add("Image 3");
        itemTexts.add("Image 4");
        itemTexts.add("Image 5");
        itemTexts.add("Image 6");
        itemTexts.add("Image 3");
        itemTexts.add("Image 8");
        itemTexts.add("Image 9");
        itemTexts.add("Image 10");
        itemTexts.add("Image 11");



        // Thêm các văn bản khác nếu cần

        // Tìm ListView từ layout
        ListView listView = findViewById(R.id.listView);

        // Tạo adapter và đặt adapter cho ListView
        CustomListAdapter adapter = new CustomListAdapter(this, imageUrls, itemTexts);
        listView.setAdapter(adapter);
    }
}