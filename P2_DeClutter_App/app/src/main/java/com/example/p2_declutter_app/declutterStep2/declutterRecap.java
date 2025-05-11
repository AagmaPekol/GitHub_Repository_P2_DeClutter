package com.example.p2_declutter_app.declutterStep2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.database.AppDatabase;
import com.example.p2_declutter_app.database.Clothing;
import com.example.p2_declutter_app.database.ClothingDao;
import com.example.p2_declutter_app.declutterStep3.dc_step3;
import com.example.p2_declutter_app.declutterStep3.declutterKeep2;

import java.util.List;

public class declutterRecap extends AppCompatActivity {

    private AppDatabase db;
    private ClothingDao dbDao;

    private RecyclerView keepRecyclerView, sellRecyclerView, donateRecyclerView;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_recap);

        db = AppDatabase.getDatabase(this);
        dbDao = db.ClothingDao();
        updateButtonStates();

        keepRecyclerView = findViewById(R.id.keepRecyclerView);
        sellRecyclerView = findViewById(R.id.sellRecyclerView);
        donateRecyclerView = findViewById(R.id.donateRecyclerView);

        keepRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sellRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        donateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sessionId = bundle.getString("sessionId");
        }

        new Thread(() -> {
            List<Clothing> keepItems = dbDao.getClothingByDecisionAndSession("keep", sessionId);
            List<Clothing> sellItems = dbDao.getClothingByDecisionAndSession("sell", sessionId);
            List<Clothing> donateItems = dbDao.getClothingByDecisionAndSession("donate", sessionId);

            runOnUiThread(() -> {
                keepRecyclerView.setAdapter(new RecapAdapter(declutterRecap.this, keepItems));
                sellRecyclerView.setAdapter(new RecapAdapter(declutterRecap.this, sellItems));
                donateRecyclerView.setAdapter(new RecapAdapter(declutterRecap.this, donateItems));
            });
        }).start();

        ImageButton continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(com.example.p2_declutter_app.declutterStep2.declutterRecap.this, dc_step3.class);
            startActivity(intent);
        });
    }
    private void updateButtonStates() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("declutterKeep_finished");
        editor.remove("declutterSell_finished");
        editor.remove("declutterDonateDiscard_finished");
        editor.apply();
    }
}

