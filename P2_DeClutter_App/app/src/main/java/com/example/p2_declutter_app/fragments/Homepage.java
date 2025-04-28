package com.example.p2_declutter_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.p2_declutter_app.Declutter_PickClothingType;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.mainMenuPage;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class Homepage extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_homepage,container,false);

        Button deClutterBtn = view.findViewById(R.id.deClutterBtn1);
        deClutterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Declutter_PickClothingType.class);
                startActivity(intent);
            }
        });

        return view;
    }
}