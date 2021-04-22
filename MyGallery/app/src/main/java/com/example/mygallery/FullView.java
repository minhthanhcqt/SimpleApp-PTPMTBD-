package com.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FullView extends AppCompatActivity {

    private MyFragmentAdapter  myFragmentAdapter;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private List<ItemImage> path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageGallery imageGallery=new ImageGallery();
        path=imageGallery.listImage(getBaseContext());
        fragments=new ArrayList<>();
        viewPager = findViewById(R.id.all);
        for (int i = 0; i < path.size(); i++) {
            if(!isImage(path.get(i).getPath())){
                videoFragment videoFragment = new videoFragment(path.get(i).getPath());
                fragments.add(videoFragment);

            }else {
                ImagesFragment imageFragment = new ImagesFragment(path.get(i).getPath());
                fragments.add(imageFragment);
            }
        }
        Intent intent = getIntent();
        String position=  intent.getStringExtra("position");
       myFragmentAdapter= new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(Integer.parseInt(position), true);


    }
    boolean isImage(String path)
    {
        String mimeType= URLConnection.guessContentTypeFromName(path);
        return mimeType!=null &&mimeType.startsWith("image");
    }

}