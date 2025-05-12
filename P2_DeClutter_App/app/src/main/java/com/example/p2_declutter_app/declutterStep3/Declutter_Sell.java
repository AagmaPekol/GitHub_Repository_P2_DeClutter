package com.example.p2_declutter_app.declutterStep3;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.achievement.AchievementManager;
import com.example.p2_declutter_app.database.AppDatabase;
import com.example.p2_declutter_app.database.Clothing;
import com.example.p2_declutter_app.database.ClothingDao;
import com.example.p2_declutter_app.declutterStep1.ClothingTypeSelection;
import com.example.p2_declutter_app.declutterStep2.dc_step2;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.ClothingItemAdapter;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Declutter_Sell extends AppCompatActivity {

    private String selectedDecision;
    private String selectedClothingType;
    private AppDatabase db;
    private ClothingDao dbDao;
    private ExecutorService executorService;

    private ClothingItemAdapter adapter;
    private int count;
    private static final String ACHIEVEMENT_ID = "sell_item";
    private AchievementManager achievementManager;

    String choice = ClothingTypeSelection.getInstance().getUClothingTypeSelection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_sell);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        db = AppDatabase.getDatabase(this);
        dbDao = db.ClothingDao();
        executorService = Executors.newSingleThreadExecutor();
        selectedClothingType = choice;
        selectedDecision = "sell";

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Clothing> items = dbDao.getItemsByTypeAndDecision(selectedClothingType, selectedDecision);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ClothingItemAdapter(items);
                        recyclerView.setAdapter(adapter);
                        TextView textView = findViewById(R.id.clothingTypeAndCount);
                        textView.setText(selectedClothingType + " " + "(" + items.size() + ")");
                    }
                });
            }
        });
        Button openVinted = findViewById(R.id.openVinted);
        openVinted.setOnClickListener(v -> {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("fr.vinted");
            if (launchIntent != null) {
                startActivity(launchIntent); // Launch the other app
            } else {
                Intent playStoreIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=fr.vinted"));
                startActivity(playStoreIntent);
            }
        });
        ImageButton nextButton = findViewById(R.id.finish_button_sell);
        achievementManager = new AchievementManager(this);achievementManager = new AchievementManager(this);
        nextButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit().putBoolean("declutterSell_finished", true).apply();

            Intent intent = new Intent(Declutter_Sell.this, dc_step3.class);
            // Unlock an achievement
            achievementManager.unlockAchievement(ACHIEVEMENT_ID);
            startActivity(intent);
        });
        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_Sell.this, mainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_Sell.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_Sell.this, Profile_page_main.class);
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
