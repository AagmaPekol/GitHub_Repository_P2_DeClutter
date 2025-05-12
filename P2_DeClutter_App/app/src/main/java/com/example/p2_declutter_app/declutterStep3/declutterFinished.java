package com.example.p2_declutter_app.declutterStep3;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.achievement.AchievementManager;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

public class declutterFinished extends AppCompatActivity {
    private static final String ACHIEVEMENT_ID = "declutter_finished";
    private AchievementManager achievementManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_declutter_finished);

        ImageButton finishButton = findViewById(R.id.nextBtn);
        achievementManager = new AchievementManager(this);achievementManager = new AchievementManager(this);

        finishButton.setOnClickListener(v -> {
            Intent intent = new Intent(declutterFinished.this, mainMenuPage.class);
            // Unlock an achievement
            achievementManager.unlockAchievement(ACHIEVEMENT_ID);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });
        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterFinished.this, mainMenuPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterFinished.this, WardrobeDecision.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterFinished.this, Profile_page_main.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
