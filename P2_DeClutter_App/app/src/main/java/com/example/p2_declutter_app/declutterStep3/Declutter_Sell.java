package com.example.p2_declutter_app.declutterStep3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.p2_declutter_app.R;

public class Declutter_Sell extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_sell);

        Button nextButton = findViewById(R.id.next_button_sell);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Declutter_Sell.this, declutterSell2.class);
            startActivity(intent);
        });
    }
}
