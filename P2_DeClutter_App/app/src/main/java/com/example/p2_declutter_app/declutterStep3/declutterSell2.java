package com.example.p2_declutter_app.declutterStep3;

import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.p2_declutter_app.R;

public class declutterSell2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_sell2);

        Button finishButton = findViewById(R.id.finish_button_sell);

        finishButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit().putBoolean("declutterSell_finished", true).apply();

            Intent intent = new Intent(declutterSell2.this, dc_step3.class);
            startActivity(intent);
            finish(); // optional: closes this page
        });
    }
}
