package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.mygallery.Fragment.viewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
public class MainActivity  extends AppCompatActivity {
    ImageButton btnGallery;
    ImageButton btnEdit;
    ImageButton btnCamera;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        btnGallery=(ImageButton) findViewById(R.id.gallery);
        btnEdit=(ImageButton) findViewById(R.id.edit);
        btnCamera=(ImageButton) findViewById(R.id.camera);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
    }
}

