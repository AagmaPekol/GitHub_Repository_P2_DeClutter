package com.example.p2_declutter_app.declutterStep1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;

import java.util.UUID;

public class Declutter_PickClothingType extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_pick_clothing_type);

        bundle = new Bundle();

        String sessionId = UUID.randomUUID().toString();
        bundle.putString("sessionId", sessionId);

        Log.d("SessionID", "Session ID: " + sessionId);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        Button tShirtBtn = findViewById(R.id.tShirtBtn);
        Button pantsBtn = findViewById(R.id.pantsBtn);
        Button dressBtn = findViewById(R.id.dressBtn);
        Button hoodieBtn = findViewById(R.id.hoodieBtn);

        tShirtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("T-Shirts", isFirstRun);
            }
        });

        pantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Pants", isFirstRun);
            }
        });

        dressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Dresses", isFirstRun);
            }

        });

        hoodieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Hoodies", isFirstRun);
            }

        });

        //      The four buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_PickClothingType.this, MainMenuPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_PickClothingType.this, WardrobeDecision.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_PickClothingType.this, Profile_page_main.class);
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

    private void showDialog(String clothingType, boolean isFirstRun){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout_clothing, null);

        Button dialogContinueButton = dialogView.findViewById(R.id.dialogBtn);
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitleTextView);

        dialogTitle.setText(clothingType);
        setImageView(clothingType, dialogView);

        AlertDialog alertDialog = builder.setView(dialogView).create();

        dialogContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("clothingType", clothingType);
                ClothingTypeSelection.getInstance().setClothingTypeSelection(clothingType);

                if (isFirstRun) {
                    Intent intent = new Intent(Declutter_PickClothingType.this, DC_IntroStep.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Declutter_PickClothingType.this, dc_step1.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void setImageView(String clothingType, View dialogView){
        ImageView imageView = dialogView.findViewById(R.id.dialogImage);

        switch (clothingType) {
            case "T-Shirts":
                imageView.setImageResource(R.drawable.red_tee);
                break;
            case "Pants":
                imageView.setImageResource(R.drawable.blue_pantss);
                break;
            case "Dresses":
                imageView.setImageResource(R.drawable.yello_dresss);
                break;
            case "Hoodies":
                imageView.setImageResource(R.drawable.purp_hoodie);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int backCount = prefs.getInt("back_press_count", 0);
        prefs.edit().putInt("back_press_count", backCount + 1).apply();

        Log.d("BackPress", "Total back presses so far: " + backCount);

        super.onBackPressed();
    }
}
