package com.example.p2_declutter_app.wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.database.*;

import com.example.p2_declutter_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WardrobeClothingType extends AppCompatActivity {

    private AppDatabase db;
    private ClothingDao dbDao;
    private ExecutorService executorService;
    private String selectedDecision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wardrobe_clothing_type);

        db = AppDatabase.getDatabase(this);
        dbDao = db.ClothingDao();
        executorService = Executors.newSingleThreadExecutor();

        selectedDecision = getIntent().getStringExtra("decision");
        TextView textView = findViewById(R.id.wardrobeHeader);
        textView.setText("Wardrobe " + selectedDecision);

        executorService.execute(new Runnable() {
            public void run() {
                List<String> clothingTypeList = dbDao.getClothingTypeByDecision(selectedDecision);
                List<String> buttonText = new ArrayList<>();

                for (String clothingType : clothingTypeList) {
                    int count = dbDao.getCountForClothingTypeAndDecision(selectedDecision, clothingType);
                    buttonText.add(clothingType + " +" + count);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!clothingTypeList.isEmpty()){
                            displayClothingTypeButtons(clothingTypeList, buttonText);
                        } else {
                            TextView pageIsEmpty = findViewById(R.id.pageIsEmpty);
                            pageIsEmpty.setVisibility(View.VISIBLE);
                            pageIsEmpty.setGravity(Gravity.CENTER);
                            pageIsEmpty.setText("No items in this Wardrobe" +
                                    "\nAdd items to \"wardrobe " + selectedDecision + "\" to see them here");
                        }
                    }
                });
            }
        });
    }

    private void displayClothingTypeButtons(List<String> clothingTypeList, List<String> buttonText) {
        LinearLayout categoryContainer = findViewById(R.id.categoryContainer);

            for (int i = 0; i < clothingTypeList.size(); i++) {
            String clothingType = clothingTypeList.get(i);
            String btnText = buttonText.get(i);

            Button button = new Button(this);
            button.setText(btnText);

            button.setOnClickListener(v -> {
                Intent intent = new Intent(WardrobeClothingType.this, WardrobeDisplayClothing.class);
                intent.putExtra("clothingType", clothingType);
                intent.putExtra("decision",selectedDecision);
                startActivity(intent);
            });
            categoryContainer.addView(button);
        }
    }
}
