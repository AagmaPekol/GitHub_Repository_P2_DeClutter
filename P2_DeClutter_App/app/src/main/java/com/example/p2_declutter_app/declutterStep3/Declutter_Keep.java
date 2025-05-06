package com.example.p2_declutter_app.declutterStep3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.p2_declutter_app.R;

public class Declutter_Keep extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_keep);

        Button nextButton = findViewById(R.id.next_button_keep);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Declutter_Keep.this, declutterKeep2.class);
            startActivity(intent);
        });
    }
}
