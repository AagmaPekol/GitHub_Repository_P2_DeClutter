package com.example.p2_declutter_app.wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.database.*;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;
import com.example.p2_declutter_app.xxxTestingFeatures.ApiCallTest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WardrobeDisplayClothing extends AppCompatActivity {

    private String selectedClothingType;
    private String selectedDecision;

    private AppDatabase db;
    private ClothingDao dbDao;
    private ExecutorService executorService;

    private ClothingItemAdapter adapter;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wardrobe_display_clothing);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        db = AppDatabase.getDatabase(this);
        dbDao = db.ClothingDao();
        executorService = Executors.newSingleThreadExecutor();

        selectedClothingType = getIntent().getStringExtra("clothingType");
        selectedDecision = getIntent().getStringExtra("decision");

        TextView textView = findViewById(R.id.wardrobeHeader);
        textView.setText("Category: "+ selectedClothingType);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Clothing> items = dbDao.getItemsByTypeAndDecision(selectedClothingType, selectedDecision);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ClothingItemAdapter(items, dbDao, executorService);
                        recyclerView.setAdapter(adapter);
                        TextView textView = findViewById(R.id.clothingTypeAndCount);
                        textView.setText(selectedClothingType + " +" + items.size());
                    }
                });
            }
        });

        //========================= The four buttons for the top/bottom nav =====================================
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WardrobeDisplayClothing.this, mainMenuPage.class);
                startActivity(intent);

            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WardrobeDisplayClothing.this, WardrobeDecision.class);
                startActivity(intent);

            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WardrobeDisplayClothing.this, ApiCallTest.class);
                startActivity(intent);

            }
        });
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}