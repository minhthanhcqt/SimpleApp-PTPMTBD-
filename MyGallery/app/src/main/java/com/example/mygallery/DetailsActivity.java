package com.example.mygallery;

import android.content.Intent;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DetailsActivity extends AppCompatActivity {
    private TextView date, name, link, size, namephone, thongso, flash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_infor);
        Intent intent=getIntent();
       String path= intent.getStringExtra("Path");
       name=findViewById(R.id.txt_Image_name);
       link=findViewById( R.id.txt_store);
       size=findViewById(R.id.img_size);
       date=findViewById(R.id.datetaken);
       namephone=findViewById(R.id.txt_name_phone);
       thongso=findViewById(R.id.txt_chi_so);
       flash=findViewById(R.id.flash);
       showExif(path);
    }

    void showExif(String photoPath) {
        File file = new File(photoPath);
        long length = file.length();
        length = length / 1024;

        String[] word = photoPath.split("/");
        String name_ = word[word.length - 1];
        String link_ = word[0];
        for (int i = 1; i < word.length - 1; i++) {
            link_ += "/" + word[i];
        }
        String size_ = String.valueOf(length) + "KB" + " ";


        if (isImage(photoPath)) {

            try {

                ExifInterface exifInterface = new ExifInterface(photoPath);
                size_ += " " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + "x" + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
                String date_ = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                String name_phone = exifInterface.getAttribute(ExifInterface.TAG_MAKE) + " " + exifInterface.getAttribute(ExifInterface.TAG_MODEL);
                String chiso = "F" + exifInterface.getAttribute(ExifInterface.TAG_F_NUMBER) + " ";
                chiso += " " + exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME) + "s";
                chiso += " " + exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH) + "mm";
                chiso += " " + "ISO" + exifInterface.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS);
                String flash_ = exifInterface.getAttribute(ExifInterface.TAG_FLASH);
                if (flash_.equals("0")) {
                    flash_ = "No Flash";
                } else {
                    flash_ = "Flash Used";
                }
                if (exifInterface.getAttribute(ExifInterface.TAG_MAKE)==null)
                {
                    date_=new Date(file.lastModified()).toString();
                    name_phone="";
                    chiso="";
                    flash_="";
                }
                name.setText(name_);
                link.setText(link_);
                size.setText(size_);
                date.setText(date_);
                namephone.setText(name_phone);
                thongso.setText(chiso);
                flash.setText(flash_);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            }
        } else
        {

            MediaPlayer mp = MediaPlayer.create(this, Uri.parse(photoPath));
            int duration = mp.getDuration();
            mp.release();
            /*convert millis to appropriate time*/
             String Dodai=String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
             MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(photoPath);
        String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String height =retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        retriever.release();
        size_+=" "+width+"x"+height+" ";
        size_+=" "+ Dodai;
        Date lastModDate = new Date(file.lastModified());
        String date_=lastModDate.toString();

            name.setText(name_);
            link.setText(link_);
            size.setText(size_);
            date.setText(date_);
            namephone.setText("");
            thongso.setText("");
            flash.setText("");


    }



    };

    boolean isImage(String path)
    {
        String mimeType= URLConnection.guessContentTypeFromName(path);
        return mimeType!=null &&mimeType.startsWith("image");
    }


}
