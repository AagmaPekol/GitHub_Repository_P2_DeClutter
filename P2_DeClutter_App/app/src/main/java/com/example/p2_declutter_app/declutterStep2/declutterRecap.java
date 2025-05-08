package com.example.p2_declutter_app.declutterStep2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.declutterStep3.dc_step3;
import com.example.p2_declutter_app.declutterStep3.declutterKeep2;

public class declutterRecap extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_recap);

        ImageButton nextButton = findViewById(R.id.next_button_recap);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(com.example.p2_declutter_app.declutterStep2.declutterRecap.this, dc_step3.class);
            startActivity(intent);
        });
    }
}

