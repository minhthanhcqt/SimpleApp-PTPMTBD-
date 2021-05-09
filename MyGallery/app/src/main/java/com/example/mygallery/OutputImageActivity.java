package com.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class OutputImageActivity extends AppCompatActivity {

    ImageView outputImage;
    Button btnSaveNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_image);

        outputImage = findViewById(R.id.output_image);
        btnSaveNew = findViewById(R.id.btn_savenew);

        Bundle bundle = getIntent().getExtras();
        String imageNewPath = bundle.getString("imageNewPath");

        outputImage.setImageURI(Uri.parse(imageNewPath));

        btnSaveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name) + "/";
                final String filename = imageNewPath.substring(imageNewPath.lastIndexOf('/')+1);
                final File dir = new File(dirPath);
                outputImage.buildDrawingCache();
                Bitmap bmap = outputImage.getDrawingCache();
                saveImage(bmap,dir,filename);
                delete(getBaseContext(),imageNewPath);
            }
        });
    }

    private String saveImage(Bitmap image, File storageDir, String imageFileName){
        String savedImagePath = null;

        boolean success = true;
        if (!storageDir.exists()){
            success = storageDir.mkdirs();
        }
        if (success){
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            galleryAddPic(savedImagePath);
            Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_LONG).show();
        }
            Intent intent=new Intent(this, FirstActivity.class);
            startActivity(intent);
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
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
}