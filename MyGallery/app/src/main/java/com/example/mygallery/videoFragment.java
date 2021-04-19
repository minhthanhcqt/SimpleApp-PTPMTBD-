package com.example.mygallery;

import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.File;

public class videoFragment extends  Fragment {
    private VideoView videoView;
    private String url;
    private View view;
    MediaController mediaController;
    public videoFragment(String url )
    {
        this.url=url;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate((R.layout.full_video), container, false);
        videoView=(VideoView)view.findViewById(R.id.fullvideo);
        mediaController=new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.setVideoPath(this.url);
        videoView.seekTo(1);
        return view;
    }
}