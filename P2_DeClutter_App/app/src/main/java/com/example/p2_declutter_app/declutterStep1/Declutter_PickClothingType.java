package com.example.p2_declutter_app.declutterStep1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.declutterStep2.Declutter_ClothingPicture;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;

public class Declutter_PickClothingType extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_pick_clothing_type);

        bundle = new Bundle();

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        Button tShirtBtn = findViewById(R.id.tShirtBtn);
        Button pantsBtn = findViewById(R.id.pantsBtn);
        Button dressBtn = findViewById(R.id.dressBtn);
        Button hoodieBtn = findViewById(R.id.hoodieBtn);

        tShirtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("T-Shirt", isFirstRun);
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
                showDialog("Dress", isFirstRun);
            }

        });

        hoodieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Hoodie", isFirstRun);
            }

        });

        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_PickClothingType.this, mainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_PickClothingType.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_PickClothingType.this, Profile_page_main.class);
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

    private void showDialog(String clothingType, boolean isFirstRun){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout_clothing, null);

        Button dialogContinueButton = dialogView.findViewById(R.id.dialogBtn);

        setImageView(clothingType);

        AlertDialog alertDialog = builder.setView(dialogView).create();

        dialogContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("clothingType", clothingType);

                if (isFirstRun) {
                    Intent intent = new Intent(Declutter_PickClothingType.this, dc_step1.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Declutter_PickClothingType.this, Declutter_ClothingPicture.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void setImageView(String clothingType){
        ImageView imageView = findViewById(R.id.dialogImage);

        switch (clothingType) {
            case "T-Shirt":
                //imageView.setImageResource(R.drawable.tshirt);
                break;
            case "Pants":
                //imageView.setImageResource(R.drawable.pants);
                break;
            case "Dress":
                //imageView.setImageResource(R.drawable.dress);
                break;
            case "Hoodie":
                //imageView.setImageResource(R.drawable.hoodie);
                break;
        }
    }

}
