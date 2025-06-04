package com.example.p2_declutter_app.declutterStep2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.wardrobe.WardrobeDecision;
import com.example.p2_declutter_app.mainMenuPage.MainMenuPage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Declutter_ClothingPicture extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private String currentPhotoPath;
    private Bundle bundle;

    private boolean photoTaken = false;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_clothing_picture);

        Button takePicture = findViewById(R.id.takePicture);
        Button allDoneBtn = findViewById(R.id.allDoneBtn);
        ImageButton continueBtn = findViewById(R.id.continueBtn);

        dialog();

        imageView = findViewById(R.id.takePhotoView);
        Glide.with(this)
                .load(R.drawable.pic_frame)
                .apply(new RequestOptions().override(600, 600))
                .into(imageView);

        bundle = getIntent().getExtras();
        Log.d("BUNDLE_DEBUG", "sessionId: " + bundle.getString("sessionId"));


        if (bundle != null) {
            boolean showButton = bundle.getBoolean("showButton");
            if (showButton) {
                allDoneBtn.setVisibility(View.VISIBLE);
            } else {
                allDoneBtn.setVisibility(View.GONE);
            }
        }

        allDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Declutter_ClothingPicture.this, declutterRecap.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoTaken && currentPhotoPath != null) {
                    Intent intent = new Intent(Declutter_ClothingPicture.this, Declutter_ClothingDescription.class);

                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isFirstRun", false);
                    editor.apply();

                    if (bundle != null) {
                        bundle.putString("currentPhotoPath", currentPhotoPath);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Log.e("Declutter_ClothingPicture", "Bundle is null");
                    }
                } else {
                    Toast.makeText(Declutter_ClothingPicture.this, "Please take a picture first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //      The four buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_ClothingPicture.this, MainMenuPage.class);
                warningDialog(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_ClothingPicture.this, WardrobeDecision.class);
                warningDialog(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_ClothingPicture.this, Profile_page_main.class);
                warningDialog(intent);
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
    }

    private void dispatchTakePictureIntent() {
        //Request camera permission from the user if not granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.p2_declutter_app.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                }
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save the file path
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(currentPhotoPath);

            if (imgFile.exists()) {
                photoTaken = true;
                Glide.with(this)
                        .load(imgFile)
                        .apply(new RequestOptions().override(600, 600))
                        .into(imageView);
            } else {
                photoTaken = false;
                Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            photoTaken = false;
        }
    }

    private void dialog(){
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int openCount = prefs.getInt("activity_open_count", 0);

        if (openCount == 1) {
            showDialog();
        }
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_sorted_first_item, null);

        TextView dialogTextView = dialogView.findViewById(R.id.dialogTextView);
        String formattedText = "<b>Well done!</b><br>" +
                "You have just sorted your first t-shirt!<br><br>" +
                "<b>Now put the sorted item in the designated pile</b><br><br>" +
                "Keep on taking pictures and sorting until every piece has found it’s place in a pile :D<br><br>" +
                "When all the pieces are sorted, proceed through the " +
                "<font color='#F39C12'><b>“All done”</b></font> button at the top";
        dialogTextView.setText(Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY));
        dialogTextView.setTextSize(22);
        dialogTextView.setGravity(android.view.Gravity.CENTER);

        AlertDialog alertDialog = builder.setView(dialogView).create();
        alertDialog.show();
    }

    private void warningDialog(Intent intent){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_warning_declutter, null);
        AlertDialog dialog = builder.setView(dialogView).create();

        TextView dialogHeaderWarningText = dialogView.findViewById(R.id.dialogHeaderWarningText);
        TextView dialogWarningText = dialogView.findViewById(R.id.dialogWarningText);
        Button dialogWarningBtn = dialogView.findViewById(R.id.dialogWarningBtn);

        dialogHeaderWarningText.setText("Warning!");
        dialogWarningText.setText("You are about to leave your decluttering session. Are you sure you want to continue?" +
                " \nYou will lose all your progress in this session if you do.");
        dialogWarningText.setGravity(android.view.Gravity.CENTER);
        dialogWarningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int backCount = prefs.getInt("back_press_count", 0);
        prefs.edit().putInt("back_press_count", backCount + 1).apply();

        super.onBackPressed();
    }
}