package com.example.p2_declutter_app.declutterStep3;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.achievement.AchievementManager;
import com.example.p2_declutter_app.mainMenuPage.mainMenuPage;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.p2_declutter_app.databinding.ActivityDeclutterDonateDiscardBinding;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class declutterDonateDiscard extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityDeclutterDonateDiscardBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String ACHIEVEMENT_ID = "donate_item";
    private AchievementManager achievementManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityDeclutterDonateDiscardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton finishButton = findViewById(R.id.finish_button_donate);

        achievementManager = new AchievementManager(this);

        finishButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit().putBoolean("declutterDonateDiscard_finished", true).apply();

            Intent intent = new Intent(declutterDonateDiscard.this, dc_step3.class);
            startActivity(intent);
            finish();
        });
        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterDonateDiscard.this, mainMenuPage.class);
                // Unlock an achievement
                achievementManager.unlockAchievement(ACHIEVEMENT_ID);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterDonateDiscard.this, WardrobeDecision.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(declutterDonateDiscard.this, Profile_page_main.class);
                startActivity(intent);
            }
        });
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    ;}
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
        }
    }
    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && mMap != null) {
            mMap.setMyLocationEnabled(true);

            // Move camera to current / last known location
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f));
                        } else {
                            Toast.makeText(this, "Unable to retrieve location.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in for the nearest donation locations
        LatLng rodeKorsContainer = new LatLng(57.0415, 9.9389);
        mMap.addMarker(new MarkerOptions().position(rodeKorsContainer).title("Marker at Røde Kors container"));
        LatLng rodeKorsContainer2 = new LatLng(57.0419468, 9.9489060);
        mMap.addMarker(new MarkerOptions().position(rodeKorsContainer2).title("Marker at Røde Kors container"));
        LatLng rodeKorsContainer3 = new LatLng(57.0322279, 9.9556209);
        mMap.addMarker(new MarkerOptions().position(rodeKorsContainer3).title("Marker at Røde Kors container"));
        LatLng rodeKorsButik = new LatLng(57.0586163, 9.9230120);
        mMap.addMarker(new MarkerOptions().position(rodeKorsButik).title("Marker at Røde Kors shop"));
        LatLng rodeKorsContainer4 = new LatLng(57.0540999, 9.9061043);
        mMap.addMarker(new MarkerOptions().position(rodeKorsContainer4).title("Marker at Røde Kors container"));
        LatLng rodeKorsContainerShop = new LatLng(57.0404778, 9.9518356);
        mMap.addMarker(new MarkerOptions().position(rodeKorsContainerShop).title("Marker at Røde Kors container and shop"));

        // Check permission and enable user location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            enableUserLocation();  // mMap is now initialized, so this will work
        }
    }
}