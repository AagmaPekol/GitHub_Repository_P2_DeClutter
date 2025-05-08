package com.example.p2_declutter_app.mainMenuPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.p2_declutter_app.R;

public class WelcomeTutorial1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_tutorial1);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean welcomeIsFirstRun = prefs.getBoolean("welcomeIsFirstRun", true);

        TextView textView = findViewById(R.id.textViewBtn);

        if (!welcomeIsFirstRun) {
            Intent intent = new Intent(WelcomeTutorial1.this, mainMenuPage.class);
            startActivity(intent);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (welcomeIsFirstRun) {
                    Intent intent = new Intent(WelcomeTutorial1.this, WelcomeTutorial2.class);
                    startActivity(intent);
                }
            }
        });
    }
}