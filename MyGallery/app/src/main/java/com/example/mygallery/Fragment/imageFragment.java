package com.example.mygallery.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygallery.FullView;
import com.example.mygallery.GalleryAdapter;
import com.example.mygallery.ImageGallery;
import com.example.mygallery.ItemImage;
import com.example.mygallery.R;

import java.net.URLConnection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link imageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class imageFragment extends Fragment {
    private static final int  MY_READ_PERMISSION_CODE =101;
    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    ViewPager viewPager;
    List<ItemImage> images;
    TextView gallery_number;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public imageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment imageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static imageFragment newInstance(String param1, String param2) {
        imageFragment fragment = new imageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gallery_number=view.findViewById(R.id.size_gallery);
        recyclerView=view.findViewById(R.id.recyclerview_gallery);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                 MY_READ_PERMISSION_CODE);
        }
        else {

            loadimage();
        }

    }

    boolean isImage(String path)
    {
        String mimeType= URLConnection.guessContentTypeFromName(path);
        return mimeType!=null &&mimeType.startsWith("image");
    }
    void loadimage()
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ImageGallery imageGallery=new ImageGallery();
       images=imageGallery.listImage(getContext());

        galleryAdapter= new GalleryAdapter(getContext(), images, new GalleryAdapter.PhotoListener() {

            @Override
            public void onPhotoClick(ItemImage itemImage) {
                int position=images.indexOf(itemImage);
                Log.e("Path " , ""+ images.get(position).getPath());
                Intent intent = new Intent(getContext(), FullView.class);
                intent.putExtra("position",String.valueOf( position));
                intent.putExtra("name", String.valueOf("0"));
                startActivity(intent);

            }
        });

        recyclerView.setAdapter(galleryAdapter);
        gallery_number.setText("Photo : " +images.size());

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_READ_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),"Read External Stroge Permission Granted", Toast.LENGTH_SHORT).show();
                    loadimage();
                } else {
                    Toast.makeText(getContext(),"Read External Stroge Permission Granted", Toast.LENGTH_SHORT).show();

                }
                return;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

}