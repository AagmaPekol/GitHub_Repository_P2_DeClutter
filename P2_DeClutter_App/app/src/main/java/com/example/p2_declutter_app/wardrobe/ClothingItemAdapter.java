package com.example.p2_declutter_app.wardrobe;

import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.database.*;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.List;

public class ClothingItemAdapter extends RecyclerView.Adapter<ClothingItemAdapter.ViewHolder> {

    private List<Clothing> itemList;
    private ClothingDao dao;
    private ExecutorService executor;

    public ClothingItemAdapter(List<Clothing> items, ClothingDao dao, ExecutorService executor) {
        this.itemList = items;
        this.dao = dao;
        this.executor = executor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView clothingImage;
        public TextView clothingDescription;
        public ImageButton deleteButton;
        public Button editButton;

        public ViewHolder(View view) {
            super(view);
            clothingImage = view.findViewById(R.id.clothingImage);
            clothingDescription = view.findViewById(R.id.clothingDescription);
            deleteButton = view.findViewById(R.id.deleteButton);
            editButton = view.findViewById(R.id.editButton);
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

        holder.editButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Clothing itemToEdit = itemList.get(pos);

                // Create an EditText input
                EditText input = new EditText(holder.itemView.getContext());
                input.setText(itemToEdit.aiText);
                input.setSelection(input.getText().length());

                // Create the dialog
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Edit Description")
                        .setView(input)
                        .setPositiveButton("Save", (dialog, which) -> {
                            String newText = input.getText().toString();
                            itemToEdit.aiText = newText;

                            executor.execute(() -> {
                                dao.updateItem(itemToEdit);
                                // UI update on main thread
                                ((android.app.Activity) holder.itemView.getContext()).runOnUiThread(() -> {
                                    notifyItemChanged(pos);
                                });
                            });
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        if (position != RecyclerView.NO_POSITION) {
                            Clothing itemToDelete = itemList.get(position);

                            executor.execute(() -> {
                                dao.deleteItem(itemToDelete);
                                itemList.remove(position);

                                // Update RecyclerView on the main thread
                                ((android.app.Activity) holder.itemView.getContext()).runOnUiThread(() -> {
                                    notifyItemRemoved(position);
                                });
                            });
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

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
