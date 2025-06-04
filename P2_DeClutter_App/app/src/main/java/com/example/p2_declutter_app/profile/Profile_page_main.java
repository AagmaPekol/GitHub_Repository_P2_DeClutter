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

import com.example.p2_declutter_app.achievement.AchievementPage;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.database.AppDatabase;
import com.example.p2_declutter_app.database.Clothing;
import com.example.p2_declutter_app.database.ClothingDao;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Profile_page_main extends AppCompatActivity {

    private ExecutorService executorService;
    private AppDatabase db;
    private ClothingDao dbDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        executorService = Executors.newSingleThreadExecutor();
        db = AppDatabase.getDatabase(this);
        dbDao = db.ClothingDao();

        //      The four buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Profile_page_main.this, MainMenuPage.class);
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

                executorService.execute(new Runnable() {
                    public void run() {
                        List<Clothing> clothingList = dbDao.getAllItems();

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(clothingList);

                        Log.d("JSON", json);

                        File file = new File(Profile_page_main.this.getFilesDir(), "clothing_data.json");
                        try (FileWriter writer = new FileWriter(file)) {
                            writer.write(json);
                            Log.d("ExportJSON", "Saved JSON to: " + file.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
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