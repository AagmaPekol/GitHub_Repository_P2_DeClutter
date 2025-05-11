package com.example.p2_declutter_app.declutterStep2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.database.Clothing;

import java.util.List;

public class RecapAdapter extends RecyclerView.Adapter<RecapAdapter.ViewHolder> {

    private List<Clothing> clothingList;
    private Context context;

    public RecapAdapter(Context context, List<Clothing> clothingList) {
        this.context = context;
        this.clothingList = clothingList;
    }

    @NonNull
    @Override
    public RecapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clothing_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecapAdapter.ViewHolder holder, int position) {
        Clothing item = clothingList.get(position);
        Glide.with(context)
                .load(item.getImageUri())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return clothingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.clothingImageView);
        }
    }
}
