package com.example.p2_declutter_app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.File;

public class Declutter_ClothingDescription extends AppCompatActivity {


    private Bundle bundle;
    private TextView descriptionText;
    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_clothing_description);

        descriptionText = findViewById(R.id.descriptionText);
        photoView = findViewById(R.id.photoView);

        bundle = getIntent().getExtras();
        setImage();

        descriptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDescription();
            }
        });
    }

    private void setDescription(){

        String description = descriptionText.getText().toString().trim();

        if (description.isEmpty()){
            Toast.makeText(Declutter_ClothingDescription.this, "Please write something", Toast.LENGTH_SHORT).show();
        } else {
            bundle.putString("description", description);
        }
    }

    private void setImage() {
        if (bundle != null) {
            String currentPhotoPath = bundle.getString("currentPhotoPath");

            if (currentPhotoPath != null) {
                File imgFile = new File(currentPhotoPath);
                if (imgFile.exists()) {
                    photoView.setImageURI(Uri.fromFile(imgFile));
                } else {
                    Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}