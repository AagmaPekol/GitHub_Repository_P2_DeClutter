package com.example.p2_declutter_app;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {
    private List<Achievement> achievementList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, status;
        ImageView icon;
        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.achievement_name);
            description = view.findViewById(R.id.achievement_description);
            status = view.findViewById(R.id.achievement_status);
            icon = itemView.findViewById(R.id.achievement_icon);
        }
    }

    public AchievementAdapter(List<Achievement> achievements) {
        this.achievementList = achievements;
    }

    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Achievement a = achievementList.get(position);
        holder.name.setText(a.getName());
        holder.description.setText(a.getDescription());
        holder.status.setText(a.isUnlocked() ? "Unlocked" : "Locked");
        holder.itemView.setAlpha(0f);
        holder.itemView.animate().alpha(1f).setDuration(400).start();
        if (a.isUnlocked()) {
            holder.status.setText("Unlocked");
            holder.status.setTextColor(Color.parseColor("#4CAF50")); // green
            holder.icon.setImageResource(R.drawable.baseline_star_rate_24);
        } else {
            holder.status.setText("Locked");
            holder.status.setTextColor(Color.parseColor("#F44336")); // red
            holder.icon.setImageResource(R.drawable.baseline_block_24);
        }
    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }
}