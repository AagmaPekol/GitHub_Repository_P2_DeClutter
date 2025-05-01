package com.example.p2_declutter_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Julias2Template extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_julias2_template);
//        BottomNavigationView bottomNav = findViewById(R.id.bottom_nagivation);
//        bottomNav.setSelectedItemId(R.id.bottom_nav_home);
//        bottomNav.setOnItemSelectedListener(navListener);
//
//        Fragment selectedFragment = new Homepage();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
//
         }


//    public NavigationBarView.OnItemSelectedListener navListener = item -> {
//        int itemId = item.getItemId();
//        Fragment selectedFragment = null;
//
//
//        if(itemId == R.id.bottom_nav_home){
//            Intent intent = new Intent(Julias2Template.this, mainMenuPage.class);
//            startActivity(intent);
//            return true;
//
//        }else if (itemId == R.id.bottom_nav_profile){
//            selectedFragment = new Profilepage();
//
//        }else if (itemId == R.id.bottom_nav_wardrobe){
//            selectedFragment = new Wardrobepage1();}
//
//        if (selectedFragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, selectedFragment)
//                    .commit();
//        }
//        return true;
//    };


}
