package com.example.mygallery;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

public class ImagesFragment extends Fragment {
    private PhotoView imageView;
    private String url;
    public ImagesFragment(String url )
    {
        this.url=url;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate((R.layout.activity_full_view), container, false);
        imageView=view.findViewById(R.id.img_full);
        Glide.with(view).load(new File(url)).into(imageView);
        return view;

    }
}