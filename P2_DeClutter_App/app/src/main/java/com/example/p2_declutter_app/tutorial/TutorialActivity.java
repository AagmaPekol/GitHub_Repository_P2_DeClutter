package com.example.p2_declutter_app.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private View skipButton;
    private View nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        if (!prefs.getBoolean("first_time", true)) {
            startMainApp();
            return;
        }

        setContentView(R.layout.activity_tut);
        viewPager = findViewById(R.id.viewPager);
        skipButton = findViewById(R.id.skip_button);
        nextButton = findViewById(R.id.next_button);

        TutorialAdapter adapter = new TutorialAdapter(this);
        viewPager.setAdapter(adapter);

        skipButton.setOnClickListener(v -> startMainApp());

        nextButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                startMainApp();
            }
        });
    }

    private void startMainApp() {
        SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.putBoolean("first_time", false);
        editor.apply();

        Intent intent = new Intent(this, mainMenuPage.class); //Her skal den hedder hvor tutorualen skal være - J
        startActivity(intent);
        finish();
    }
}
