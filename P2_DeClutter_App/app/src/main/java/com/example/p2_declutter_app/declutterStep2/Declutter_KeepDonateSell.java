package com.example.p2_declutter_app.declutterStep2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.R;

import java.io.File;

public class Declutter_KeepDonateSell extends AppCompatActivity {

    private Bundle bundle;
    private TextView descriptionText;
    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_keep_donate_sell);

        bundle = getIntent().getExtras();

        descriptionText = findViewById(R.id.aiDescriptionText);
        photoView = findViewById(R.id.photoView2);

        descriptionText.setText(bundle.getString("text_AI"));
        setImage();

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