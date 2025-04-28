package com.example.p2_declutter_app;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.p2_declutter_app.fragments.Homepage;
import com.example.p2_declutter_app.fragments.Profilepage;
import com.example.p2_declutter_app.fragments.Wardrobepage1;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Julias2Template extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_julias2_template);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nagivation);
        bottomNav.setSelectedItemId(R.id.bottom_nav_home);
        bottomNav.setOnItemSelectedListener(navListener);

        Fragment selectedFragment = new Homepage();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        Fragment selectedFragment = null;

        if(itemId == R.id.bottom_nav_home){
            selectedFragment = new Homepage();
        }else if (itemId == R.id.bottom_nav_profile){
            selectedFragment = new Profilepage();
        }else if (itemId == R.id.bottom_nav_wardrobe){
            selectedFragment = new Wardrobepage1();}

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

        return true;
    };


}
