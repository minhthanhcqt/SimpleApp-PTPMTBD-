package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mygallery.Fragment.viewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.wajahatkarim3.longimagecamera.LongImageCameraActivity;

public class MainActivity  extends AppCompatActivity {
    ImageButton btnGallery;
    ImageButton btnEdit;
    ImageButton btnCamera;

    //image picker code
    private final int REQUEST_CODE_PICKER =  100;
    //photo edit code
    private final int PHOTO_EDITOR_REQUEST_CODE = 231;

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
        btnCamera.setOnClickListener(view -> {
            openCamera();
        });
        btnEdit.setOnClickListener(view -> {
            openGallery();
        });
    }

    private void openCamera() {
        LongImageCameraActivity.launch(MainActivity.this);
    }
    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_CODE_PICKER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //open cam
        if (resultCode == RESULT_OK && requestCode == LongImageCameraActivity.LONG_IMAGE_RESULT_CODE && data!= null){

            String imagePath = data.getStringExtra(LongImageCameraActivity.IMAGE_PATH_KEY);
            editImage(imagePath);

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
    }

    private void editImage(String imagePath) {

        try {

        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}

