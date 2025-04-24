package com.example.p2_declutter_app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TutorialFragment extends Fragment {
    private static final String ARG_TEXT = "text";
    private static final String ARG_IMAGE = "image";

    public static TutorialFragment newInstance(String text, int imageResId) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_IMAGE, imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tut, container, false);
        TextView text = view.findViewById(R.id.tutorial_text);
        ImageView image = view.findViewById(R.id.tutorial_image);

        text.setText(getArguments().getString(ARG_TEXT));
        image.setImageResource(getArguments().getInt(ARG_IMAGE));

        return view;
    }
}
