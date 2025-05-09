package com.example.p2_declutter_app.declutterStep2;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.p2_declutter_app.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p2_declutter_app.database.*;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.io.LineReader;

public class Declutter_KeepDonateSell extends AppCompatActivity {

    private ExecutorService executorService;
    private AppDatabase db;
    private ClothingDao dao;
    private Bundle bundle;
    private Bundle bundleStartOver;
    private TextView descriptionText;

    private TextView headerText;
    private ImageView photoView;
    private  FrameLayout frameLayout;
    private FrameLayout backgroundLayout;

    private Button saveToDatabaseBtn;

    private int currentBackgroundColor = Color.WHITE;
    private ValueAnimator colorAnimator;

    private String decision = "";
    private String description;
    private String currentPhotoPath;
    private String aiDescription;
    private String clothingType;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_keep_donate_sell);

        bundle = getIntent().getExtras();

        if (bundle == null) {
            Toast.makeText(this, "Bundle is null!", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService = Executors.newSingleThreadExecutor();
        db = AppDatabase.getDatabase(this);
        dao = db.ClothingDao();

        clothingType = bundle.getString("clothingType");
        description = bundle.getString("description");
        currentPhotoPath = bundle.getString("currentPhotoPath");
        aiDescription = bundle.getString("text_AI");

        bundleStartOver = new Bundle();
        bundleStartOver.putString("clothingType", clothingType);

//        Log.d("BUNDLE_DEBUG", "clothingType: " + bundle.getString("clothingType"));
//        Log.d("BUNDLE_DEBUG", "description: " + bundle.getString("description"));
//        Log.d("BUNDLE_DEBUG", "currentPhotoPath: " + bundle.getString("currentPhotoPath"));
//        Log.d("BUNDLE_DEBUG", "text_AI: " + bundle.getString("text_AI"));

        descriptionText = findViewById(R.id.aiDescriptionText);
        photoView = findViewById(R.id.photoView2);
        frameLayout = findViewById(R.id.frameLayout);
        //backgroundLayout = findViewById(R.id.frameLayout);
        headerText = findViewById(R.id.headerText);
        saveToDatabaseBtn = findViewById(R.id.saveToDatabaseBtn);

        descriptionText.setText(bundle.getString("text_AI"));
        setImage();

        saveToDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(decision.isEmpty()) {
                    Toast.makeText(Declutter_KeepDonateSell.this, "Please swipe the card", Toast.LENGTH_SHORT).show();
                } else {
                    saveToDatabase(clothingType, description, currentPhotoPath, decision, aiDescription);
                }
            }
        });

        // Got code from this tutorial:
        // https://www.youtube.com/watch?v=Rd89cVKrQBg
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            float startRawX = 0f;
            float startRawY = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Store the initial touch position
                        startRawX = event.getRawX();
                        startRawY = event.getRawY();
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
                        float endX = event.getRawX();
                        float endY = event.getRawY();

                        float deltaX = endX - startRawX;
                        float deltaY = endY - startRawY;

                        final int SWIPE_THRESHOLD = 100;

                        if (Math.abs(deltaX) > Math.abs(deltaY)) {
                            if (deltaX > SWIPE_THRESHOLD) {
                                frameLayout.setBackground(ContextCompat.getDrawable(Declutter_KeepDonateSell.this, R.drawable.swipe_card_background_green));
                                headerText.setText("Keep " + clothingType);
                                decision = "keep"; // Swiped right
                                Log.d("Swipe", "swiped right");
                            } else if (deltaX < -SWIPE_THRESHOLD) {
                                frameLayout.setBackground(ContextCompat.getDrawable(Declutter_KeepDonateSell.this, R.drawable.swipe_card_background_yellow));
                                headerText.setText("Sell " + clothingType);
                                decision = "sell"; // Swiped left
                                Log.d("Swipe", "swiped left");

                            }
                        } else {
                            if (deltaY < -SWIPE_THRESHOLD) {
                                frameLayout.setBackground(ContextCompat.getDrawable(Declutter_KeepDonateSell.this, R.drawable.swipe_card_background_red));
                                headerText.setText("Donate " + clothingType);
                                decision = "donate"; // Swiped up
                                Log.d("Swipe", "swiped up");
                            }
                            else if (deltaY > SWIPE_THRESHOLD) {
                                frameLayout.setBackground(ContextCompat.getDrawable(Declutter_KeepDonateSell.this, R.drawable.swipe_card_background));
                                headerText.setText("");
                                decision = "";
                                Log.d("Swipe", "swiped down");
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

    private void saveToDatabase(String clothingType, String description, String currentPhotoPath, String decision, String aiDescription) {

        if (clothingType == null || clothingType.isEmpty() ||
                description == null || description.isEmpty() ||
                decision == null || decision.isEmpty() ||
                currentPhotoPath == null || currentPhotoPath.isEmpty()) {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            executorService.execute(new Runnable() {
                public void run() {
                    Clothing item = new Clothing(clothingType, description, currentPhotoPath, decision, aiDescription);
                    dao.addItem(item);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Declutter_KeepDonateSell.this, Declutter_ClothingPicture.class);
                            intent.putExtras(bundleStartOver);
                            Log.d("DATABASE", "Saved to database");
                            startActivity(intent);
                        }
                    });
                }
            });
        }
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