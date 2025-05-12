package com.example.p2_declutter_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2_declutter_app.achievement.Achievement;
import com.example.p2_declutter_app.achievement.AchievementAdapter;
import com.example.p2_declutter_app.achievement.AchievementManager;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

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
                new Achievement("keep_item", "Keep a piece of clothing", "Add a piece of clothing to the keep pile when decluttering"),
                new Achievement("donate_item", "Donate a piece of clothing", "Donate a piece of clothing to your nearest donation spot"),
                new Achievement("sell_item", "Sell a piece of clothing", "List your first piece of clothing on Vinted"),
                new Achievement("wardrobe_opened", "Open the wardrobe", "Open the wardrobe and check out what you have"),
                new Achievement("declutter_finished","Finish decluttering","Go through the whole decluttering process" )

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

        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AchievementPage.this, mainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AchievementPage.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AchievementPage.this, Profile_page_main.class);
                startActivity(intent);
            }
        });
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
