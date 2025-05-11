package com.example.p2_declutter_app.declutterStep3;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;

public class declutterFinished extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_finished);

        ImageButton finishButton = findViewById(R.id.nextBtn);

        finishButton.setOnClickListener(v -> {
            Intent intent = new Intent(declutterFinished.this, mainMenuPage.class);
            startActivity(intent);
            finish(); // optional: closes this page
        });

    }
}
