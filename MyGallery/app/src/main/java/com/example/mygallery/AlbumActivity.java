package com.example.mygallery;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    private List<ItemImage> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_story);
        recyclerView=findViewById(R.id.listDate);
        Intent intent = getIntent();
        String nameAlbum=  intent.getStringExtra("name");
        loadimage(nameAlbum);


    }

    void loadimage( String nameAlbum)
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 4));
        ImageGallery imageGallery=new ImageGallery();
        images=imageGallery.listImage(getBaseContext());
        List<ItemImage> image=new ArrayList<>();

        for( int i=0; i<images.size(); i++)
        {
            String path= images.get(i).getPath();
            String [] word=path.split("/");
            String newWord= word[word.length-2];
            if( nameAlbum.equals(newWord))
            {
                image.add(images.get(i));
            }
        }

        galleryAdapter= new GalleryAdapter(getBaseContext(), image, new GalleryAdapter.PhotoListener() {

            @Override
            public void onPhotoClick(ItemImage itemImage) {
                int position=image.indexOf(itemImage);
                Log.e("Path " , ""+ images.get(position).getPath());
                String path = images.get(position).getPath();
                Intent intent = new Intent(getBaseContext(), FullView.class);
                intent.putExtra("position",String.valueOf( position));
                intent.putExtra("name", String.valueOf(nameAlbum));
                intent.putExtra("imgpath", path);
                startActivity(intent);

            }
        });

        recyclerView.setAdapter(galleryAdapter);

    }

}
