package com.example.p2_declutter_app.declutterStep2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.database.AppDatabase;
import com.example.p2_declutter_app.database.Clothing;
import com.example.p2_declutter_app.database.ClothingDao;
import com.example.p2_declutter_app.declutterStep3.dc_step3;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

import java.util.List;

public class declutterRecap extends AppCompatActivity {

    private AppDatabase db;
    private ClothingDao dbDao;

    private RecyclerView keepRecyclerView, sellRecyclerView, donateRecyclerView;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_recap);

        db = AppDatabase.getDatabase(this);
        dbDao = db.ClothingDao();
        updateButtonStates();

        keepRecyclerView = findViewById(R.id.keepRecyclerView);
        sellRecyclerView = findViewById(R.id.sellRecyclerView);
        donateRecyclerView = findViewById(R.id.donateRecyclerView);

        keepRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sellRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        donateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sessionId = bundle.getString("sessionId");
        }

        new Thread(() -> {
            List<Clothing> keepItems = dbDao.getClothingByDecisionAndSession("keep", sessionId);
            List<Clothing> sellItems = dbDao.getClothingByDecisionAndSession("sell", sessionId);
            List<Clothing> donateItems = dbDao.getClothingByDecisionAndSession("donate", sessionId);

            runOnUiThread(() -> {
                keepRecyclerView.setAdapter(new RecapAdapter(declutterRecap.this, keepItems));
                sellRecyclerView.setAdapter(new RecapAdapter(declutterRecap.this, sellItems));
                donateRecyclerView.setAdapter(new RecapAdapter(declutterRecap.this, donateItems));
            });
        }).start();

        ImageButton continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(com.example.p2_declutter_app.declutterStep2.declutterRecap.this, dc_step3.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        //      The four buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterRecap.this, MainMenuPage.class);
                warningDialog(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterRecap.this, WardrobeDecision.class);
                warningDialog(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterRecap.this, Profile_page_main.class);
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
    private void updateButtonStates() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("declutterKeep_finished");
        editor.remove("declutterSell_finished");
        editor.remove("declutterDonateDiscard_finished");
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int backCount = prefs.getInt("back_press_count", 0);
        prefs.edit().putInt("back_press_count", backCount + 1).apply();

        super.onBackPressed();
    }
}

