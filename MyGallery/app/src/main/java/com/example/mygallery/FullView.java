package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FullView extends AppCompatActivity  {

    private MyFragmentAdapter  myFragmentAdapter;
    private ViewPager viewPager;
    String position;
    String name;
    private ArrayList<Fragment> fragments;
    private ArrayList<ItemImage> images;
    List<ItemImage> image;
    private Button button;
    private boolean a;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullview);

        Toolbar topbar = findViewById(R.id.topNav);
        setSupportActionBar(topbar);
        a = topbar.isShown();
        load();

    }




    public static void  delete(Context context, String file) {
        final String where = MediaStore.MediaColumns.DATA + "=?";
        final String[] selectionArgs = new String[] {
                file
        };

        File File = new File(file);
        final ContentResolver contentResolver = context.getContentResolver();
        final Uri filesUri = MediaStore.Files.getContentUri("external");

        contentResolver.delete(filesUri, where, selectionArgs);

        if (File.exists()) {

            contentResolver.delete(filesUri, where, selectionArgs);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fullview_top_menu,menu);
        return true;

    }

    void load()
    {
        ImageGallery imageGallery=new ImageGallery();
        images=imageGallery.listImage(getBaseContext());
        fragments=new ArrayList<>();
        viewPager = findViewById(R.id.all);

        Intent intent = getIntent();
        position=  intent.getStringExtra("position");
        name=intent.getStringExtra("name");
        image=new ArrayList<>();
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

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(Integer.parseInt(position), true);
    }
    void setWallpaperManager(String path)
    {
        if(isImage(path)) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 2220, true);
            try {
                // set the wallpaper by calling the setResource function and
                // passing the drawable fi
                // le
                wallpaperManager.setBitmap(bitmap);

            } catch (IOException e) {
                // here the errors can be logged instead of printStackTrace
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "Can not Set Up WallPaper",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_delete:
                int a= viewPager.getCurrentItem();

                delete(getBaseContext(),image.get(a).getPath());
                load();
                myFragmentAdapter.notifyDataSetChanged();
                Intent inten=new Intent(getBaseContext(), FirstActivity.class);
                startActivity(inten);

                //setWallpaperManager(image.get(a).getPath());
                break;
            case R.id.top_detail:
                int x=viewPager.getCurrentItem();
                String path=image.get(x).getPath();
                Intent intent1=new Intent(getBaseContext(), DetailsActivity.class);
                intent1.putExtra("Path", path);
                startActivity(intent1);


                break;
            case R.id.top_wallpaper:
                int b= viewPager.getCurrentItem();
                setWallpaperManager(image.get(b).getPath());
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