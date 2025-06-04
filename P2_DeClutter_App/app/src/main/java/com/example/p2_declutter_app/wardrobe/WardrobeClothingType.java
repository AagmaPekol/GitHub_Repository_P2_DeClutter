package com.example.p2_declutter_app.wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.p2_declutter_app.database.*;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;

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

        //      The four buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(WardrobeClothingType.this, MainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(WardrobeClothingType.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(WardrobeClothingType.this, Profile_page_main.class);
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
                                    "\nAdd more items to the " + selectedDecision + " pile, to see them here");
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
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_blue_v1));
            button.setTextColor(ContextCompat.getColor(this, R.color.Text_dark_green));

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
