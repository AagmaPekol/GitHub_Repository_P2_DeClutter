package com.example.p2_declutter_app.declutterStep3;

import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.p2_declutter_app.R;

public class declutterSell2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_sell2);

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

        ImageButton finishButton = findViewById(R.id.finish_button_sell);

        finishButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit().putBoolean("declutterSell_finished", true).apply();

            Intent intent = new Intent(declutterSell2.this, dc_step3.class);
            startActivity(intent);
            finish(); // optional: closes this page
        });
    }
}
