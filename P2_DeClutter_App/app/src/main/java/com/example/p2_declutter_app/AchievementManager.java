package com.example.p2_declutter_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AchievementManager {
    private Context context;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "achievements";

    public AchievementManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isUnlocked(String achievementId) {
        return prefs.getBoolean(achievementId, false);
    }

    public void unlockAchievement(String achievementId) {
        if (!isUnlocked(achievementId)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(achievementId, true);
            editor.apply();
            showAchievementUnlockedDialog(achievementId);
        }
    }

    private void showAchievementUnlockedDialog(String achievementId) {
        // Replace this with actual UI logic
        Toast.makeText(context, "Achievement Unlocked: " + achievementId, Toast.LENGTH_LONG).show();
    }
}
