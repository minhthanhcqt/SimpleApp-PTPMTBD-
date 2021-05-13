package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mygallery.Fragment.viewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.List;

import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;

public class MainActivity  extends AppCompatActivity {
    ImageButton btnGallery;
    ImageButton btnEdit;
    ImageButton btnCamera;
    ImageButton btnVideo;
    Switch      aSwitch;

    //Night and day
    public static final String MyPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    public static final String KEY_DEFAULTAPP = "notasked";
    SharedPreferences sharedPreferences;
    //permission code
    public final int REQUEST_MULTI_PERMISSION = 7;
    //image picker code
    private final int REQUEST_CODE_PICKER =  100;
    //photo edit code
    private final int PHOTO_EDITOR_REQUEST_CODE = 231;
    //Camera code
    private final int IMAGE_CAPTURE = 102;
    private final int VIDEO_CAPTURE = 101;
    private Uri outUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnGallery=(ImageButton) findViewById(R.id.gallery);
        btnEdit=(ImageButton) findViewById(R.id.edit);
        btnCamera=(ImageButton) findViewById(R.id.camera);
        btnVideo = (ImageButton) findViewById(R.id.video);
        aSwitch = findViewById(R.id.nightmode);

        checkNightModeActive();


        if (!hasCamera()) {
            btnVideo.setEnabled(false);
            btnCamera.setEnabled(false);
        }

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
        btnCamera.setOnClickListener(view -> {
            openCamera();
        });
        btnVideo.setOnClickListener(view -> {
            openVideo();
        });
        btnEdit.setOnClickListener(view -> {
            openGallery();
        });

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightModeState(true);
                recreate();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightModeState(false);
                recreate();
            }
        });
    }

    private void saveNightModeState(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE,b);
        editor.apply();
    }
    private void checkNightModeActive() {
        if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            aSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveDefaultState(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE,b);
        editor.apply();
    }


    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        } else {
            return false;
        }
    }

    private void openCamera() {

        if (checkanndaskpermission() == true){
            Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outputimg = FileUtils.genEditFile();
            outUri = Uri.fromFile(outputimg);
            cam.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            startActivityForResult(cam,IMAGE_CAPTURE);
        }
        else {
            requestCamPermission();
        }
    }
    private void openVideo() {

        if (checkanndaskpermission()==true){
            Intent vid = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File outputvid = FileUtils.genVidFile();
            outUri = Uri.fromFile(outputvid);
            vid.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            startActivityForResult(vid, VIDEO_CAPTURE);
        }
        else {
            requestCamPermission();
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_CODE_PICKER);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //open cam
        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK && data != null) {
                Toast.makeText(this, "Image has been saved to:\n" +
                        outUri, Toast.LENGTH_LONG).show();
                openCamera();
            }
            else if (resultCode == RESULT_CANCELED)
            {
                FullView.delete(getBaseContext(),outUri.getPath());
            }
        }
        //open video
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK && data != null) {
                Toast.makeText(this, "Video has been saved to:\n" +
                        outUri, Toast.LENGTH_LONG).show();
                openVideo();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
                FullView.delete(getApplicationContext(),outUri.getPath());
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
                FullView.delete(getApplicationContext(),outUri.getPath());
                openVideo();
            }
        }

        //open Gall
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PICKER){
            Uri imageUri = data.getData();

            String[] filePathCollumn = { MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imageUri, filePathCollumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathCollumn[0]);
            String imagePath = cursor.getString(columnIndex);
            editImage(imagePath);
        }
        //edited images
        if (requestCode == PHOTO_EDITOR_REQUEST_CODE && data != null) {
            String newFilePath = data.getStringExtra(ImageEditorIntentBuilder.OUTPUT_PATH);
            boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IS_IMAGE_EDITED, false);
            if (isImageEdit){

                Intent output = new Intent(getApplicationContext(), OutputImageActivity.class);
                output.putExtra("imageNewPath", newFilePath);
                output.putExtra("imageOldPath", ImageEditorIntentBuilder.SOURCE_PATH);
                startActivity(output);

            }else {
                newFilePath = data.getStringExtra(ImageEditorIntentBuilder.SOURCE_PATH);
                FullView.delete(this,newFilePath);
            }
        }
        if (data == null)
        {
            recreate();
        }

    }

    private void editImage(String imagePath) {
        if (permissionGranted()) {
            try {
                File outputFile = FileUtils.genEditFile();
                Intent intent = new ImageEditorIntentBuilder(MainActivity.this, imagePath, outputFile.getAbsolutePath())
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
                EditImageActivity.start(MainActivity.this, intent, PHOTO_EDITOR_REQUEST_CODE);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            requestPermission();
        }
    }
    private boolean permissionGranted(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void requestCamPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private boolean checkanndaskpermission() {
        return ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}

