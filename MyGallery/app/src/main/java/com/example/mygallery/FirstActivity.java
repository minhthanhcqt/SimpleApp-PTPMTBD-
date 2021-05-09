package com.example.mygallery;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mygallery.Fragment.viewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FirstActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private BottomNavigationView btnNv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager=findViewById(R.id.all);
        btnNv=findViewById(R.id.bottom);
        viewPager adapter=new viewPager(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0: btnNv.getMenu().findItem(R.id.image).setChecked(true);
                        break;
                    case 1:  btnNv.getMenu().findItem(R.id.album).setChecked(true);
                        break;
                    case 2:  btnNv.getMenu().findItem(R.id.story).setChecked(true);
                        break;
                    case 3:  btnNv.getMenu().findItem(R.id.face).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.image:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.album:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.story:
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.face:
                        viewpager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}