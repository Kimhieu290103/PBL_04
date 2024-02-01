package com.midterm.pbl4;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomListAdapter extends BaseAdapter{
    private Context context;
    private List<String> imageUrls;
    private List<String> itemTexts;

    public CustomListAdapter(Context context, List<String> imageUrls, List<String> itemTexts) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.itemTexts = itemTexts;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView itemText = convertView.findViewById(R.id.itemText);

        // Tải hình ảnh từ đường link bằng Glide
        String imageUrl = imageUrls.get(position);
        Log.d("ImageLoading", "Loading image from: " + imageUrl);
        Glide.with(context).load(imageUrl).into(itemImage);


        // Hiển thị văn bản liên quan
        itemText.setText(itemTexts.get(position));

        return convertView;
    }
}

