package com.example.mygallery.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class viewPager extends FragmentStatePagerAdapter {

    public viewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new imageFragment();
            case 1: return new albumFragment();
            case 2: return new storyFragment();
            case 3: return new faceFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
