package com.example.p2_declutter_app.declutterStep3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

public class dc_step3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dc_step3);

        //      The four buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(dc_step3.this, MainMenuPage.class);
                warningDialog(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(dc_step3.this, WardrobeDecision.class);
                warningDialog(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(dc_step3.this, Profile_page_main.class);
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

        updateButtonStates(); // checks flags and updates UI

        ImageView bunke1 = findViewById(R.id.bunkeKeep);
        ImageView bunke2 = findViewById(R.id.bunkeDonate);
        ImageView bunke3 = findViewById(R.id.bunkeSell);

        bunke1.setOnClickListener(v -> {
            Intent intent = new Intent(dc_step3.this, Declutter_Keep.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        bunke2.setOnClickListener(v -> {
            Intent intent = new Intent(dc_step3.this, declutterDonateDiscard.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        bunke3.setOnClickListener(v -> {
            Intent intent = new Intent(dc_step3.this, Declutter_Sell.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
    private void updateButtonStates() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        boolean isDeclutterKeepFinished = prefs.getBoolean("declutterKeep_finished", false);
        boolean isDeclutterSellFinished = prefs.getBoolean("declutterSell_finished", false);
        boolean isDeclutterDonateDiscardFinished = prefs.getBoolean("declutterDonateDiscard_finished", false);

        ImageView marker1 = findViewById(R.id.marker1); // the image above button 1
        ImageView marker2 = findViewById(R.id.marker2); // the image above button 2
        ImageView marker3 = findViewById(R.id.marker3); // the image above button 3

        if (isDeclutterKeepFinished) {
            marker1.setBackgroundResource(R.drawable.pile_done_24);
        }
        if (isDeclutterSellFinished) {
            marker3.setBackgroundResource(R.drawable.pile_done_24);
        }
        if (isDeclutterDonateDiscardFinished) {
            marker2.setBackgroundResource(R.drawable.pile_done_24);
        }
        if (isDeclutterKeepFinished && isDeclutterSellFinished && isDeclutterDonateDiscardFinished) {
            Intent intent = new Intent(this, declutterFinished.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
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