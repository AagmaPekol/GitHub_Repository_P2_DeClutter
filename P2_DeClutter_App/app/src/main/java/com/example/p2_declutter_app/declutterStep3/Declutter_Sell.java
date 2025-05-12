package com.example.p2_declutter_app.declutterStep3;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
    private static final String ACHIEVEMENT_ID = "Sell Item";
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
                        adapter = new ClothingItemAdapter(items, dbDao, executorService);
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_Sell.this, mainMenuPage.class);
                warningDialog(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_Sell.this, WardrobeDecision.class);
                warningDialog(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_Sell.this, Profile_page_main.class);
                warningDialog(intent);
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

    private void warningDialog(Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_warning_declutter, null);
        AlertDialog dialog = builder.setView(dialogView).create();

        TextView dialogHeaderWarningText = dialogView.findViewById(R.id.dialogHeaderWarningText);
        TextView dialogWarningText = dialogView.findViewById(R.id.dialogWarningText);
        Button dialogWarningBtn = dialogView.findViewById(R.id.dialogWarningBtn);

        dialogHeaderWarningText.setText("Warning!");
        dialogWarningText.setText("You are about to leave your decluttering session. Are you sure you want to continue?" +
                " \nYou will lose all your progress in this session if you do.");
        dialogWarningText.setGravity(android.view.Gravity.CENTER);
        dialogWarningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int backCount = prefs.getInt("back_press_count", 0);
        prefs.edit().putInt("back_press_count", backCount + 1).apply();

        super.onBackPressed();
    }
}
