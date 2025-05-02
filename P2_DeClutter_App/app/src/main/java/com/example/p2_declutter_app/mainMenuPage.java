package com.example.p2_declutter_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.p2_declutter_app.declutterStep1.Declutter_PickClothingType;
import com.example.p2_declutter_app.templates.Julias2Template;
import com.example.p2_declutter_app.tutorial.TutorialActivity;
import com.example.p2_declutter_app.wardrobe.WardrobePage;
import com.example.p2_declutter_app.xxxTestingFeatures.ApiCallTest;

public class mainMenuPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button deClutterBtn = findViewById(R.id.deClutterBtn);
        deClutterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainMenuPage.this, Declutter_PickClothingType.class);
                startActivity(intent);
            }
        });

        Button wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mainMenuPage.this, WardrobePage.class);
                startActivity(intent);
            }
        });

        Button rewardsBtn = findViewById(R.id.rewardsBtn);
        rewardsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mainMenuPage.this, ApiCallTest.class);
                startActivity(intent);

            }
        });

        ImageButton juliaBtn = findViewById(R.id.juliaBtn);
        juliaBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mainMenuPage.this, Julias2Template.class);
                startActivity(intent);
            }
        });

        ImageButton loginBtn = findViewById(R.id.profileBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainMenuPage.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

//        Button camera = findViewById(R.id.CameraBTN);
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mainMenuPage.this,Camera.class);
//                startActivity(intent);
//            }
//        });

    }



    public void notImplemented(){
        Toast.makeText(this, "This is not implemented yet", Toast.LENGTH_SHORT).show();
    }

}
