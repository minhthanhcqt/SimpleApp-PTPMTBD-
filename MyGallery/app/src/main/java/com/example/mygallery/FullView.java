package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FullView extends AppCompatActivity  {


    private BottomNavigationView bottomNavigationView;
    private MyFragmentAdapter  myFragmentAdapter;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private List<ItemImage> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageGallery imageGallery=new ImageGallery();
        images=imageGallery.listImage(getBaseContext());
        fragments=new ArrayList<>();
        viewPager = findViewById(R.id.all);
        Intent intent = getIntent();
        String position=  intent.getStringExtra("position");
        String name=intent.getStringExtra("name");
        List<ItemImage> image=new ArrayList<>();
        if(!name.equals("0"))
        {

            for(int i=0; i<images.size(); i++)
            {
                String path= images.get(i).getPath();
                String [] word=path.split("/");
                String newWord= word[word.length-2];
                if( name.equals(newWord))
                {
                    image.add(images.get(i));
                }
            }

        }
        else
        {
            image=images;
        }
        for (int i = 0; i < image.size(); i++) {
            if(!isImage(image.get(i).getPath())){
                videoFragment videoFragment = new videoFragment(image.get(i).getPath());
                fragments.add(videoFragment);

            }else {
                ImagesFragment imageFragment = new ImagesFragment(image.get(i).getPath());
                fragments.add(imageFragment);
            }
        }

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