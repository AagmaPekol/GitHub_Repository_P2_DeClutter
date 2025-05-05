package com.example.p2_declutter_app;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.view.View;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

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