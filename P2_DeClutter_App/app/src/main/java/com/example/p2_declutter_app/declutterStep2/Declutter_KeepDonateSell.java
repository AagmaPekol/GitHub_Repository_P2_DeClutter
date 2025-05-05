package com.example.p2_declutter_app.declutterStep2;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class Declutter_KeepDonateSell extends AppCompatActivity {

    private Bundle bundle;
    private TextView descriptionText;
    private ImageView photoView;
    private  FrameLayout frameLayout;

    private String decision = "";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_keep_donate_sell);

        bundle = getIntent().getExtras();

        descriptionText = findViewById(R.id.aiDescriptionText);
        photoView = findViewById(R.id.photoView2);
        frameLayout = findViewById(R.id.frameLayout);

        descriptionText.setText(bundle.getString("text_AI"));
        setImage();

        // Got code from this tutorial:
        // https://www.youtube.com/watch?v=Rd89cVKrQBg
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            float startX = 0f;
            float startY = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Store the initial touch position
                        startX = event.getX();
                        startY = event.getY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        // Puts the frameLayout where the touch is
                        float moveX = event.getRawX() - frameLayout.getWidth() / 2;
                        float moveY = event.getRawY() - frameLayout.getHeight() / 2;
                        frameLayout.setX(moveX);
                        frameLayout.setY(moveY);
                        return true;

                    case MotionEvent.ACTION_UP:
                        // When the frameLayout is no longer touched.
                        float endX = event.getX();
                        float endY = event.getY();

                        float deltaX = endX - startX;
                        float deltaY = endY - startY;

                        final int SWIPE_THRESHOLD = 100;

                        if (Math.abs(deltaX) > Math.abs(deltaY)) {
                            if (deltaX > SWIPE_THRESHOLD) {
                                decision = "keep"; // Swiped right
                                Toast.makeText(Declutter_KeepDonateSell.this, "swiped right", Toast.LENGTH_SHORT).show();
                            } else if (deltaX < -SWIPE_THRESHOLD) {
                                decision = "sell"; // Swiped left
                                Toast.makeText(Declutter_KeepDonateSell.this, "swiped left", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (deltaY < -SWIPE_THRESHOLD) {
                                decision = "donate"; // Swiped up
                                Toast.makeText(Declutter_KeepDonateSell.this, "swiped up", Toast.LENGTH_SHORT).show();
                            }
                            else if (deltaY > SWIPE_THRESHOLD) {
                                decision = "delete"; // Swiped down
                                Toast.makeText(Declutter_KeepDonateSell.this, "swiped down", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Reset frameLayout position to center
                        frameLayout.animate()
                                .x((v.getRootView().getWidth() - frameLayout.getWidth()) / 2f)
                                .y((v.getRootView().getHeight() - frameLayout.getHeight()) / 2f)
                                .setDuration(150)
                                .start();

                        return true;
                }
                return false;
            }
        });

    }

    private void setImage() {
        if (bundle != null) {
            String currentPhotoPath = bundle.getString("currentPhotoPath");
            File imgFile = new File(currentPhotoPath);

            if (imgFile.exists()) {
                Glide.with(this)
                        .load(imgFile)
                        .apply(new RequestOptions().override(600, 600))
                        .into(photoView);
            } else {
                Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}