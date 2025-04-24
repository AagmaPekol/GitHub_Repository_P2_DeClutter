package com.example.p2_declutter_app;

import com.example.p2_declutter_app.database.AppDatabase;
import com.example.p2_declutter_app.database.Clothing;
import com.example.p2_declutter_app.database.ClothingDao;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WardrobePage extends AppCompatActivity {

    private AppDatabase db;
    private ClothingDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wardrobe_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getDatabase(this);
        dao = db.ClothingDao();

        EditText clothingTypeText = findViewById(R.id.clothingTypeText);
        EditText descriptionText = findViewById(R.id.descText);
        Button dbBtn = findViewById(R.id.dbBtn);

        dbBtn.setOnClickListener(v -> {
            String type = clothingTypeText.getText().toString();
            String desc = descriptionText.getText().toString();

            if (!type.isEmpty() && !desc.isEmpty()) {
                new Thread(() -> {
                    dao.addItem(new Clothing(type, desc));
                }).start();

                clothingTypeText.setText("");
                descriptionText.setText("");
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter something in both text fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}