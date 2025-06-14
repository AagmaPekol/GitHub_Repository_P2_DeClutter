package com.example.p2_declutter_app.declutterStep1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;

public class DC_IntroStep extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dc_intro_step);

        bundle = getIntent().getExtras();
        String clothingType = bundle.getString("clothingType");

        TextView textTutorial = findViewById(R.id.textTutorial);
        String formattedText = "<b>Welcome to your decluttering journey!</b><br><br>" +
                "We will now dive deep into a section of your wardrobe, scour through the forgotten and sort each piece, " +
                "with it’s history and memories in mind. Who knows what you might find on the way?<br><br>" +
                "This time, we will be looking through <b><u>" + clothingType + "</u></b><br><br>" +
                "Remember that this is <b>YOUR</b> journey, so follow along in your own pace and make it as fun or cozy as you want. " +
                "A warm drink or some good music is highly recommended.<br><br>" +
                "When you are done with each step, press the <font color='#f29c4a'><b>orange</b></font> button at the bottom right to proceed.";

        textTutorial.setText(Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY));
        textTutorial.setGravity(android.view.Gravity.CENTER);

        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DC_IntroStep.this, MainMenuPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //      The four buttons for the top/bottom nav
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DC_IntroStep.this, WardrobeDecision.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DC_IntroStep.this, Profile_page_main.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DC_IntroStep.this, dc_step1.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int backCount = prefs.getInt("back_press_count", 0);
        prefs.edit().putInt("back_press_count", backCount + 1).apply();

        super.onBackPressed();
    }
}