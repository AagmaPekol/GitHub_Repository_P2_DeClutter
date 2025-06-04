package com.example.p2_declutter_app.wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;

public class WardrobeDecision extends AppCompatActivity {

    private String decision = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wardrobe_decision);

        Button donateBtn = findViewById(R.id.donateBtn);
        Button keepBtn = findViewById(R.id.keepBtn);
        Button sellBtn = findViewById(R.id.sellBtn);

        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision = "donate";
                setDecision(decision);
                }
        });
        keepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision = "keep";
                setDecision(decision);
            }
            });
        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision = "sell";
                setDecision(decision);
            }
        });

//========================= The four buttons for the top/bottom nav =====================================
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WardrobeDecision.this, MainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WardrobeDecision.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WardrobeDecision.this, Profile_page_main.class);
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

    private void setDecision(String decision) {
        Intent intent = new Intent(WardrobeDecision.this, WardrobeClothingType.class);
        intent.putExtra("decision", decision);
        startActivity(intent);
    }

}