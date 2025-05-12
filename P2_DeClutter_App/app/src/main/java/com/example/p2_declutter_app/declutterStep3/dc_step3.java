package com.example.p2_declutter_app.declutterStep3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

public class dc_step3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dc_step3);

        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(dc_step3.this, mainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(dc_step3.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(dc_step3.this, Profile_page_main.class);
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

        updateButtonStates(); // checks flags and updates UI

        ImageView bunke1 = findViewById(R.id.bunkeKeep);
        ImageView bunke2 = findViewById(R.id.bunkeDonate);
        ImageView bunke3 = findViewById(R.id.bunkeSell);

        bunke1.setOnClickListener(v -> {
            Intent intent = new Intent(dc_step3.this, Declutter_Keep.class);
            startActivity(intent);
        });

        bunke2.setOnClickListener(v -> {
            Intent intent = new Intent(dc_step3.this, declutterDonateDiscard.class);
            startActivity(intent);
        });

        bunke3.setOnClickListener(v -> {
            Intent intent = new Intent(dc_step3.this, Declutter_Sell.class);
            startActivity(intent);
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
            marker1.setBackgroundResource(R.drawable.pile_done_24); // changes the image above button 1
        }
        if (isDeclutterSellFinished) {
            marker3.setBackgroundResource(R.drawable.pile_done_24); // changes the image above button 2
        }
        if (isDeclutterDonateDiscardFinished) {
            marker2.setBackgroundResource(R.drawable.pile_done_24); // changes the image above button 3
        }
        if (isDeclutterKeepFinished && isDeclutterSellFinished && isDeclutterDonateDiscardFinished) {
            Intent intent = new Intent(this, declutterFinished.class);
            startActivity(intent);
        }
    }
}