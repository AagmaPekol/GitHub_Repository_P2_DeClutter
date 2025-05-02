package com.example.p2_declutter_app.declutterStep2;

import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
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

public class Declutter_KeepDonateSell extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private Bundle bundle;
    private TextView descriptionText;
    private ImageView photoView;

    private static final String TAG = "Swipe Position";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_keep_donate_sell);

        bundle = getIntent().getExtras();

        //descriptionText = findViewById(R.id.aiDescriptionText);
        //photoView = findViewById(R.id.photoView2);

        descriptionText.setText(bundle.getString("text_AI"));
        setImage();

        this.gestureDetector = new GestureDetector(Declutter_KeepDonateSell.this, this);
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

    //Credit to this youtube video on how to implement swipe gestures
    //https://www.youtube.com/watch?v=oFl7WwEX2Co
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueX = x2 - x1;
                float valueY = y2 - y1;

                if(Math.abs(valueX) > MIN_DISTANCE){
                    if(x2 > x1){
                        Toast.makeText(this, "Swipe to the right", Toast.LENGTH_SHORT).show();
                }
                    else{
                        Toast.makeText(this, "Swipe to the left", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(Math.abs(valueY) > MIN_DISTANCE) {
                    if (y2 > y1) {
                        Toast.makeText(this, "Swipe down", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Swipe up", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}