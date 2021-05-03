package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        a = topbar.isShown();


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
            case R.id.top_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT ).show();
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

    public void botbtnedit(View view) {
        Toast.makeText(FullView.this, "Edit", Toast.LENGTH_SHORT).show();
    }
    public void botbtnfav(View view) {
        Toast.makeText(FullView.this, "Favorite", Toast.LENGTH_SHORT).show();
    }
    public void botbtnshare(View view) {
        Toast.makeText(FullView.this, "Share", Toast.LENGTH_SHORT).show();
    }


}