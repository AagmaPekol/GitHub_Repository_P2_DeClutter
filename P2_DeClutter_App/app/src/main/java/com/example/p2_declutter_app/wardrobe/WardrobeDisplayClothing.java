package com.example.p2_declutter_app.wardrobe;

import android.os.Bundle;
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
        textView.setText("Wardrobe "+ selectedDecision);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Clothing> items = dbDao.getItemsByTypeAndDecision(selectedClothingType, selectedDecision);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ClothingItemAdapter(items);
                        recyclerView.setAdapter(adapter);
                        TextView textView = findViewById(R.id.clothingTypeAndCount);
                        textView.setText(selectedClothingType + " +" + items.size());
                    }
                });
            }
        });

    }
}