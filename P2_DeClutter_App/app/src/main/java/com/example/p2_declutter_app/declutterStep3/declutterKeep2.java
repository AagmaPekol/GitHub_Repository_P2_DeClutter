package com.example.p2_declutter_app.declutterStep3;

import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.p2_declutter_app.R;

public class declutterKeep2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_keep2);

        Button finishButton = findViewById(R.id.finish_button_keep);

        finishButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit().putBoolean("declutterKeep_finished", true).apply();

            Intent intent = new Intent(declutterKeep2.this, dc_step3.class);
            startActivity(intent);
            finish(); // optional: closes this page
        });
    }
}

