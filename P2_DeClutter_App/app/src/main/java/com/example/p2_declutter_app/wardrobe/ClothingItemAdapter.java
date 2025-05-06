package com.example.p2_declutter_app.wardrobe;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.database.*;

import java.io.File;
import java.util.List;

public class ClothingItemAdapter extends RecyclerView.Adapter<ClothingItemAdapter.ViewHolder> {

    private List<Clothing> itemList;

    public ClothingItemAdapter(List<Clothing> items) {
        this.itemList = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView clothingImage;
        public TextView clothingDescription;

        public ViewHolder(View view) {
            super(view);
            clothingImage = view.findViewById(R.id.clothingImage);
            clothingDescription = view.findViewById(R.id.clothingDescription);
        }
    }

    @Override
    public ClothingItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clothing_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingItemAdapter.ViewHolder holder, int position) {
        Clothing item = itemList.get(position);
        holder.clothingDescription.setText(item.aiText);

        if (item.imageUri != null && !item.imageUri.isEmpty()) {
            File imgFile = new File(item.imageUri);
            if (imgFile.exists()) {
                Glide.with(holder.itemView.getContext())
                        .load(imgFile)
                        .apply(new RequestOptions().override(600, 600)) // optional resizing
                        .into(holder.clothingImage);
            } else {
                holder.clothingImage.setImageResource(R.drawable.man_bottom_step1);
            }
        } else {
            holder.clothingImage.setImageResource(R.drawable.man_bottom_step1);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
