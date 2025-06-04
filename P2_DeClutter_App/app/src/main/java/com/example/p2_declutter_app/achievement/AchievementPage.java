package com.example.p2_declutter_app.achievement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;
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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_achievement_page);


        achievementManager = new AchievementManager(this);

        // You can eventually pull these from a database or constants
        List<Achievement> achievements = Arrays.asList(
                new Achievement("Sort Item", "Sort your first item", "Sort your first item in the decluttering process"),
                new Achievement("Donate Item", "Donate your unwanted clothes", "Donate your unwanted clothes to the nearest container or shop"),
                new Achievement("Sell Item", "Sell a piece of clothing", "Give your clothes a second life by selling them on Vinted"),
                new Achievement("Open Wardrobe", "Open the wardrobe", "Open the wardrobe and check out what you have"),
                new Achievement("Finish Decluttering","Finish decluttering","Go through the whole decluttering process" )

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
                Intent intent = new Intent(AchievementPage.this, MainMenuPage.class);
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
