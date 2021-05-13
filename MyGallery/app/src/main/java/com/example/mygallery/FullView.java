package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;
import lib.folderpicker.FolderPicker;

public class FullView extends AppCompatActivity  {

    private MyFragmentAdapter  myFragmentAdapter;
    private ViewPager viewPager;
    private String imageallPath;
    private String position;
    private String name;
    private ArrayList<Fragment> fragments;
    private ArrayList<ItemImage> images;
    private String newpath = null;
    List<ItemImage> image;

    ArrayList<String> FavList;

    //photo edit code
    private final int PHOTO_EDITOR_REQUEST_CODE = 231;
    //copy code
    private final int DIRECTORY_CHOOSE_CODE = 230;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullview);
        Toolbar topbar = findViewById(R.id.topNav);
        setSupportActionBar(topbar);
        load();

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
                int a= viewPager.getCurrentItem();

                if(name.equals("Fav"))
                {
                    deleteFav(image.get(a).getPath());
                    Toast.makeText(FullView.this, " Remove the file from favorite", Toast.LENGTH_SHORT).show();
                }
                else {

                    delete(getBaseContext(), image.get(a).getPath());
                    Toast.makeText(FullView.this, " Remove the file from External_Storage", Toast.LENGTH_SHORT).show();

                }
                load();
                myFragmentAdapter.notifyDataSetChanged();
                Intent intent =new Intent(getBaseContext(), FirstActivity.class);
                startActivity(intent);

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
            case R.id.top_copy:
                int c= viewPager.getCurrentItem();
                imageallPath = image.get(c).getPath();
                if(permissionGranted()) {
                    Intent intent2 = new Intent(this, FolderPicker.class);
                    intent2.putExtra("title", "Select folder to copy to");
                    startActivityForResult(intent2,DIRECTORY_CHOOSE_CODE);
                }
            else {
                    requestPermission();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void copyFile(String inpath, String outpath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inpath);
            out = new FileOutputStream(outpath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;
            Toast.makeText(getApplicationContext(), "Copied to: "+outpath, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException fnfe1) {
            Log.e("Not found",fnfe1.getMessage());
            Toast.makeText(getApplicationContext(), fnfe1.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    boolean isImage(String path)
    {
        String mimeType= URLConnection.guessContentTypeFromName(path);
        return mimeType!=null &&mimeType.startsWith("image");
    }

    public void botbtnedit(View view) {
        Toast.makeText(FullView.this, "Edit", Toast.LENGTH_SHORT).show();
        int a= viewPager.getCurrentItem();
        imageallPath = image.get(a).getPath();
        editImage(imageallPath);
    }
    public void botbtnfav(View view) {
        int a= viewPager.getCurrentItem();
        imageallPath = image.get(a).getPath();
        loadData();
        if(FavList.contains(imageallPath))
        {
            Toast.makeText(FullView.this, " The File already exists in Favorite", Toast.LENGTH_SHORT).show();
        }
        else {
            addFav(imageallPath);
            Toast.makeText(FullView.this, " Added Favorite", Toast.LENGTH_SHORT).show();
        }
    }
    public void botbtnshare(View view) {
        Toast.makeText(FullView.this, "Share", Toast.LENGTH_SHORT).show();
        int a= viewPager.getCurrentItem();
        imageallPath = image.get(a).getPath();
        shareImage(imageallPath);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//edited images
        if (requestCode == PHOTO_EDITOR_REQUEST_CODE) {
            String newFilePath = data.getStringExtra(ImageEditorIntentBuilder.OUTPUT_PATH);
            boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IS_IMAGE_EDITED, false);
            if (isImageEdit){

            }else {
                newFilePath = data.getStringExtra(ImageEditorIntentBuilder.SOURCE_PATH);
            }
            Intent output = new Intent(getApplicationContext(), OutputImageActivity.class);
            output.putExtra("imageNewPath", newFilePath);
            startActivity(output);
        }
        if (requestCode == DIRECTORY_CHOOSE_CODE){
            String output = data.getExtras().getString("data")+"/"+ System.currentTimeMillis() + ".jpg";
            copyFile(imageallPath,output);
            ;
        }
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
        loadData();
        image=new ArrayList<>();

        if(name.equals("Fav"))
        {
            for(int i=0; i<images.size(); i++)
            {
                if(FavList.contains(images.get(i).getPath()))
                {
                    image.add(images.get(i));
                }
            }

        }
        else {

            if (!name.equals("0")) {

                for (int i = 0; i < images.size(); i++) {
                    String path = images.get(i).getPath();
                    String[] word = path.split("/");
                    String newWord = word[word.length - 2];
                    if (name.equals(newWord)) {
                        image.add(images.get(i));
                    }
                }

            } else {
                image = images;
            }
        }
                for (int i = 0; i < image.size(); i++) {
                    if (!isImage(image.get(i).getPath())) {
                        videoFragment videoFragment = new videoFragment(image.get(i).getPath());
                        fragments.add(videoFragment);

                } else {
                    ImagesFragment imageFragment = new ImagesFragment(image.get(i).getPath());
                    fragments.add(imageFragment);
                }
        }

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(Integer.parseInt(position), true);
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

    private void editImage(String imagePath) {
        try {
            File outputFile = FileUtils.genEditFile();

            Intent intent = new ImageEditorIntentBuilder(this, imagePath, outputFile.getAbsolutePath())
                    .withAddText()
                    .withBeautyFeature()
                    .withBrightnessFeature()
                    .withCropFeature()
                    .withFilterFeature()
                    .withPaintFeature()
                    .withRotateFeature()
                    .withStickerFeature()
                    .withSaturationFeature()
                    .forcePortrait(true)
                    .setSupportActionBarVisibility(false)
                    .build();
            EditImageActivity.start(this,intent, PHOTO_EDITOR_REQUEST_CODE);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    private void setWallpaperManager(String path)
    {
        if(isImage(path)) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 2220, true);
            try {
                // set the wallpaper by calling the setResource function and
                // passing the drawable file
                wallpaperManager.setBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Image set as WallPaper", Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                // here the errors can be logged instead of printStackTrace
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Can not Set Up WallPaper", Toast.LENGTH_LONG).show();
        }
    }

    private void addFav(String path)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        FavList.add(path);
        String json = gson.toJson(FavList);
        editor.putString("Fav", json);
        editor.apply();

    }

    private void  loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Fav", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        FavList = gson.fromJson(json, type);
        if (FavList == null) {
            FavList = new ArrayList<>();
        }

    }
    private void deleteFav(String path)
    {
        int index=FavList.indexOf(path);
        FavList.remove(index);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(FavList);
        editor.putString("Fav", json);
        editor.apply();

    }

    private void shareImage(String path) {
        StrictMode.VmPolicy.Builder builder  = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 2220, true);
        File file = FileUtils.genEditFile();
        Intent shareint;
        shareint = new Intent(Intent.ACTION_SEND);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            shareint.setType("image/jpg");
            shareint.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Can not share this file", Toast.LENGTH_LONG).show();
        }
        startActivity(Intent.createChooser(shareint, "Share Via: "));
    }
    private boolean permissionGranted(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

}
