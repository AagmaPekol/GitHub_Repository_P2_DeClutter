package com.example.p2_declutter_app.tutorial;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.p2_declutter_app.R;

public class TutorialAdapter extends FragmentStateAdapter {
    public TutorialAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return TutorialFragment.newInstance("Welcome to our app!", R.drawable.ic_launcher_foreground);
            case 1:
                return TutorialFragment.newInstance("Easily track your goals.", R.drawable.ic_launcher_foreground);
            case 2:
                return TutorialFragment.newInstance("Stay productive!", R.drawable.ic_launcher_foreground);
            default:
                return TutorialFragment.newInstance("Ready to get started?", R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

