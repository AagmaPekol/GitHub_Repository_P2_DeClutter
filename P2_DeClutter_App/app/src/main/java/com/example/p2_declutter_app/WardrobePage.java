package com.example.p2_declutter_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.p2_declutter_app.database.AppDatabase;
import com.example.p2_declutter_app.database.Clothing;
import com.example.p2_declutter_app.database.ClothingDao;

import java.io.File;

public class WardrobePage extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private AppDatabase db;
    private ClothingDao dao;

    private Uri photoUri;  // To store the URI of the captured image

    private EditText clothingTypeText;
    private EditText descriptionText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wardrobe_page);

        db = AppDatabase.getDatabase(this);
        dao = db.ClothingDao();

        clothingTypeText = findViewById(R.id.clothingTypeText);
        descriptionText = findViewById(R.id.descText);
        Button dbBtn = findViewById(R.id.dbBtn);
        Button cameraButton = findViewById(R.id.takePhotoBtn);
        imageView = findViewById(R.id.imageView);

        cameraButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                openCamera();
            }
        });

        dbBtn.setOnClickListener(v -> saveToDatabase());
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create a temporary file to save the image
            try {
                photoUri = getPhotoUri();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                Toast.makeText(this, "Error capturing image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (photoUri != null) {
                // Display the image using the URI
                imageView.setImageURI(photoUri);
            }
        }
    }

    private Uri getPhotoUri() throws Exception {
        // Create a unique file to store the image
        String imageFileName = "JPEG_" + System.currentTimeMillis() + ".jpg";
        File storageDir = getExternalFilesDir(null);  // Or get a specific directory
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        return Uri.fromFile(imageFile);
    }

    private void saveToDatabase() {
        String type = clothingTypeText.getText().toString();
        String desc = descriptionText.getText().toString();

        if (type.isEmpty() || desc.isEmpty() || photoUri == null) {
            Toast.makeText(this, "Fill all fields and take a picture!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the Uri to a string to save in the database
        String imageUriString = photoUri.toString();

        Clothing clothingItem = new Clothing(type, desc, imageUriString);
        new Thread(() -> {
            dao.addItem(clothingItem);
            runOnUiThread(() -> {
                clothingTypeText.setText("");
                descriptionText.setText("");
                imageView.setImageDrawable(null);
                Toast.makeText(this, "Clothing item saved!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}