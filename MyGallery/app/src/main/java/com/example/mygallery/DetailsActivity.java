package com.example.mygallery;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DetailsActivity extends AppCompatActivity {
    private TextView date, name, link, size, namephone, thongso, flash, location;
    private Button tagLocation;
    private  String path, la, longi;
    private Double lat_, longi_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_infor);
        Intent intent=getIntent();
       path= intent.getStringExtra("Path");
       name=findViewById(R.id.txt_Image_name);
       link=findViewById( R.id.txt_store);
       size=findViewById(R.id.img_size);
       date=findViewById(R.id.datetaken);
       namephone=findViewById(R.id.txt_name_phone);
       thongso=findViewById(R.id.txt_chi_so);
       flash=findViewById(R.id.flash);
       tagLocation=findViewById(R.id.tagLocation);
       location=findViewById(R.id.tag_location);
        try {
            showExif(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tagLocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.e("Longitude", ": "+longi_);
               Log.e("Longi", ": "+longi);
               Log.e("path:","" +path);
               Intent intent=new Intent(DetailsActivity.this, MapsActivity.class);
               intent.putExtra("Path", path);

               startActivity(intent);
           }
       });
    }

    void showExif(String photoPath) throws IOException {
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


                ExifInterface exifInterface = new ExifInterface(photoPath);
                size_ += " " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + "x" + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
                String date_ = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                String name_phone = exifInterface.getAttribute(ExifInterface.TAG_MAKE) + " " + exifInterface.getAttribute(ExifInterface.TAG_MODEL);
                String chiso = "F" + exifInterface.getAttribute(ExifInterface.TAG_F_NUMBER) + " ";
                chiso += " " + exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME) + "s";
                chiso += " " + exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH) + "mm";
                chiso += " " + "ISO" + exifInterface.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS);
                double[] latLong= exifInterface.getLatLong();
                String location_="";
                if( latLong!=null ) {
                    Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                   List< Address> addresses;
                    String address = null;
                    String city = null;
                    String state = null;
                    String country = null;

                    try {
                        addresses = geocoder.getFromLocation(latLong[0], latLong[1], 1);
                        address = addresses.get(0).getAddressLine(0);
                        city = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    location_=address+city;
                }


                String flash_ = exifInterface.getAttribute(ExifInterface.TAG_FLASH);

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
                location.setText(location_);

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
            location.setText("");


    }



    };

    Double getTitude(String[]a)
    {
        Double results;


        String []x=a[0].split("/");
        results =Double.parseDouble(x[0])/Double.parseDouble(x[1]);
        String []y=a[1].split("/");
        results+=(Double.parseDouble(y[0])/Double.parseDouble(y[1]))/60;
        String []z=a[2].split("/");
        results+=(Double.parseDouble(z[0])/Double.parseDouble(z[1]))/3600;
        return  results;




    }



    boolean isImage(String path)
    {
        String mimeType= URLConnection.guessContentTypeFromName(path);
        return mimeType!=null &&mimeType.startsWith("image");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
