package com.example.p2_declutter_app.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.p2_declutter_app.AchievementPage;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.achievement.Achievement;
import com.example.p2_declutter_app.declutterStep1.DC_IntroStep;
import com.example.p2_declutter_app.declutterStep3.Declutter_Sell;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

public class Profile_page_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Profile_page_main.this, mainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Profile_page_main.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Profile_page_main.this, Profile_page_main.class);
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

        Button achievements = findViewById(R.id.achievements);
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Profile_page_main.this, AchievementPage.class);
                startActivity(intent);
            }
        });

        Button toast1 = findViewById(R.id.toast1);
        toast1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                int backCount = prefs.getInt("back_press_count", 0);
                Log.d("BackPress", "Total back presses so far: " + backCount);
                Toast.makeText(Profile_page_main.this,"This feature has not been implemented yet " + backCount, Toast.LENGTH_SHORT).show();
            }
        });

        Button toast2 = findViewById(R.id.toast2);
        toast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile_page_main.this,"This feature has not been implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

        Button toast3 = findViewById(R.id.toast3);
        toast3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile_page_main.this,"This feature has not been implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

        Button toast4 = findViewById(R.id.toast4);
        toast4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile_page_main.this,"This feature has not been implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}