package com.example.p2_declutter_app.achievement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;

import com.example.p2_declutter_app.R;

public class AchievementTest extends AppCompatActivity {
        private static final String ACHIEVEMENT_ID = "first_win";
        private AchievementManager achievementManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_achievement_test);

            achievementManager = new AchievementManager(this);

            Button unlockButton = findViewById(R.id.unlock_button);
            TextView statusText = findViewById(R.id.status_text);

            updateStatusText(statusText);

            unlockButton.setOnClickListener(v -> {
                achievementManager.unlockAchievement(ACHIEVEMENT_ID);
                updateStatusText(statusText);
            });
        }

        private void updateStatusText(TextView textView) {
            boolean unlocked = achievementManager.isUnlocked(ACHIEVEMENT_ID);
            if (unlocked) {
                textView.setText("Achievement Unlocked!");
            } else {
                textView.setText("Achievement Locked");
            }
        }
}