package com.example.p2_declutter_app.mainMenuPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.p2_declutter_app.achievement.AchievementPage;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.achievement.AchievementManager;
import com.example.p2_declutter_app.declutterStep1.Declutter_PickClothingType;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

public class MainMenuPage extends AppCompatActivity {
    private static final String ACHIEVEMENT_ID = "Open Wardrobe";
    private AchievementManager achievementManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button deClutterBtn = findViewById(R.id.deClutterBtn);
        deClutterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuPage.this, Declutter_PickClothingType.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        achievementManager = new AchievementManager(this);
        Button wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Unlock an achievement
                achievementManager.unlockAchievement(ACHIEVEMENT_ID);

                Intent intent = new Intent(MainMenuPage.this, WardrobeDecision.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button rewardsBtn = findViewById(R.id.rewardsBtn);
        rewardsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainMenuPage.this, "Rewards is not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton achievementBtn = findViewById(R.id.achievementBtn);
        achievementBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainMenuPage.this, AchievementPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton loginBtn = findViewById(R.id.profileBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuPage.this, Profile_page_main.class);
                startActivity(intent);
            }
        });
    }
}
