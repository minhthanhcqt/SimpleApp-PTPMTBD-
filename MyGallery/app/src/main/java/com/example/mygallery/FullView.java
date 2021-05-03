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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FullView extends AppCompatActivity  {

    private MyFragmentAdapter  myFragmentAdapter;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ArrayList<ItemImage> images;
    private Button button;
    private boolean a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullview);

        Toolbar topbar = findViewById(R.id.topNav);
        setSupportActionBar(topbar);

        button = findViewById(R.id.onButton);
        a = topbar.isShown();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == true)
                {
                    a = false;
                    topbar.setVisibility(View.GONE);
                }
                else
                {
                    a = true;
                    topbar.setVisibility(View.VISIBLE);
                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fullview_top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_edit:
                Toast.makeText(this, "Edit edit", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.top_item2:
                Toast.makeText(this, "Item2", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.top_item3:
                Toast.makeText(this, "Item3", Toast.LENGTH_SHORT ).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean isImage(String path)
    {
        String mimeType= URLConnection.guessContentTypeFromName(path);
        return mimeType!=null &&mimeType.startsWith("image");
    }

}