package com.example.p2_declutter_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Declutter_PickClothingType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_pick_clothing_type);

        Button tShirtBtn = findViewById(R.id.tShirtBtn);
        Button pantsBtn = findViewById(R.id.pantsBtn);
        Button dressBtn = findViewById(R.id.dressBtn);
        Button hoodieBtn = findViewById(R.id.hoodieBtn);

        tShirtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("T-Shirt");
            }
        });

        pantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Pants");
            }
        });

        dressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Dress");
            }

        });

        hoodieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Hoodie");
            }

        });
    }

    private void showDialog(String clothingType){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout_clothing, null);

        Button dialogContinueButton = dialogView.findViewById(R.id.dialogBtn);

        setImageView(clothingType);

        AlertDialog alertDialog = builder.setView(dialogView).create();

        dialogContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Declutter_PickClothingType.this, Declutter_ClothingPicture.class);

                Bundle bundle = new Bundle();
                bundle.putString("clothingType", clothingType);
                intent.putExtras(bundle);

                startActivity(intent);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void setImageView(String clothingType){
        ImageView imageView = findViewById(R.id.dialogImage);

        switch (clothingType) {
            case "T-Shirt":
                //imageView.setImageResource(R.drawable.tshirt);
                break;
            case "Pants":
                //imageView.setImageResource(R.drawable.pants);
                break;
            case "Dress":
                //imageView.setImageResource(R.drawable.dress);
                break;
            case "Hoodie":
                //imageView.setImageResource(R.drawable.hoodie);
                break;
        }
    }

}
