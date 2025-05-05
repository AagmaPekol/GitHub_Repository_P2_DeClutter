package com.example.p2_declutter_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2_declutter_app.achievement.Achievement;
import com.example.p2_declutter_app.achievement.AchievementAdapter;
import com.example.p2_declutter_app.achievement.AchievementManager;

import java.util.Arrays;
import java.util.List;

public class AchievementPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AchievementAdapter adapter;
    private AchievementManager achievementManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_page);

        achievementManager = new AchievementManager(this);

        // You can eventually pull these from a database or constants
        List<Achievement> achievements = Arrays.asList(
                new Achievement("first_item", "Add an item", "Add your first piece of clothing to your wardrobe"),
                new Achievement("daily_clean", "Daily Declutter", "Open declutter 2 days in a row"),
                new Achievement("sell_item", "Sell an item", "List your first item on Vinted"),
                new Achievement("wardrobe_opened", "Open the wardrobe", "Open the wardrobe")
        );

        // Update unlocked status
        for (Achievement achievement : achievements) {
            if (achievementManager.isUnlocked(achievement.getId())) {
                achievement.unlock();
            }
        }

        recyclerView = findViewById(R.id.achievement_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AchievementAdapter(achievements);
        recyclerView.setAdapter(adapter);
    }
}
