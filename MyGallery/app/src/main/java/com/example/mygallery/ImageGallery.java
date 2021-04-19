package com.example.mygallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.DESKeySpec;

public class ImageGallery {
    public ArrayList<String> listImage(Context context) {
        Uri uri;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImage = new ArrayList<>();
        String pathImage;
       //String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        /*
        String[]projection={
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
                

        };
        String Selection=MediaStore.Files.FileColumns.MEDIA_TYPE+"="+ MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                +"OR" + MediaStore.Files.FileColumns.MEDIA_TYPE+"="+ MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        Uri queryUri=MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader=new CursorLoader(context, queryUri,projection, Selection, null, MediaStore.Files.FileColumns.DATE_ADDED+"DESC");
        cursor = cursorLoader.loadInBackground();
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

         */
        // column_index_folder_name=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

       // cursor=context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

// Return only video and image metadata.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");

        CursorLoader cursorLoader = new CursorLoader(context,
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            pathImage = cursor.getString(column_index_data);
            listOfAllImage.add(pathImage);
        }
        return listOfAllImage;
    }
}
