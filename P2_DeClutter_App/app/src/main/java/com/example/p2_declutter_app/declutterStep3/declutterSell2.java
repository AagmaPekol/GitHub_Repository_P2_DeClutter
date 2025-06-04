package com.example.p2_declutter_app.declutterStep3;

import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.declutterStep1.DC_IntroStep;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

public class declutterSell2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });
        //      The four buttons for the top/bottom nav
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterSell2.this, WardrobeDecision.class);
                warningDialog(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterSell2.this, Profile_page_main.class);
                warningDialog(intent);
            }
        });
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void warningDialog(Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_warning_declutter, null);
        AlertDialog dialog = builder.setView(dialogView).create();

        TextView dialogHeaderWarningText = dialogView.findViewById(R.id.dialogHeaderWarningText);
        TextView dialogWarningText = dialogView.findViewById(R.id.dialogWarningText);
        Button dialogWarningBtn = dialogView.findViewById(R.id.dialogWarningBtn);

        dialogHeaderWarningText.setText("Warning!");
        dialogWarningText.setText("You are about to leave your decluttering session. Are you sure you want to continue?" +
                " \nYou will lose all your progress in this session if you do.");
        dialogWarningText.setGravity(android.view.Gravity.CENTER);
        dialogWarningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int backCount = prefs.getInt("back_press_count", 0);
        prefs.edit().putInt("back_press_count", backCount + 1).apply();

        super.onBackPressed();
    }
}
