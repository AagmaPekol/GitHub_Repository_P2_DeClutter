package com.example.p2_declutter_app.declutterStep3;

import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.p2_declutter_app.R;

public class declutter3Piles extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_3_piles);

        updateButtonStates(); // checks flags and updates UI

        ImageView bunke1 = findViewById(R.id.bunke1);
        ImageView bunke2 = findViewById(R.id.bunke2);
        ImageView bunke3 = findViewById(R.id.bunke3);

        bunke1.setOnClickListener(v -> {
            Intent intent = new Intent(declutter3Piles.this, Declutter_Keep.class);
            startActivity(intent);
        });

        bunke2.setOnClickListener(v -> {
            Intent intent = new Intent(declutter3Piles.this, Declutter_Sell.class);
            startActivity(intent);
        });

        bunke3.setOnClickListener(v -> {
            Intent intent = new Intent(declutter3Piles.this, declutterDonateDiscard.class);
            startActivity(intent);
        });
    }
    private void updateButtonStates() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // editor.remove("declutterKeep_finished");
        // editor.remove("declutterSell_finished");
        // editor.remove("declutterDonateDiscard_finished");
        // Add more as needed
        // editor.apply();

        boolean isDeclutterKeepFinished = prefs.getBoolean("declutterKeep_finished", false);
        boolean isDeclutterSellFinished = prefs.getBoolean("declutterSell_finished", false);
        boolean isDeclutterDonateDiscardFinished = prefs.getBoolean("declutterDonateDiscard_finished", false);

        ImageView bunke1 = findViewById(R.id.bunke1); // the image button
        ImageView bunke2 = findViewById(R.id.bunke2); // the image button
        ImageView bunke3 = findViewById(R.id.bunke3); // the image button

        if (isDeclutterKeepFinished) {
            bunke1.setImageResource(R.drawable.ic_checkmark); // or dim, overlay, etc.
        }
        if (isDeclutterSellFinished) {
            bunke2.setImageResource(R.drawable.ic_checkmark); // or dim, overlay, etc.
        }
        if (isDeclutterDonateDiscardFinished) {
            bunke3.setImageResource(R.drawable.ic_checkmark); // or dim, overlay, etc.
        }
    }
}
