package com.example.mygallery.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.mygallery.Date_Adapter;
import com.example.mygallery.FullView;
import com.example.mygallery.GalleryAdapter;
import com.example.mygallery.ImageGallery;
import com.example.mygallery.ItemDate;
import com.example.mygallery.ItemImage;
import com.example.mygallery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link storyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class storyFragment extends Fragment {
    private static final int  MY_READ_PERMISSION_CODE =101;
    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;

    List<ItemImage>images;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public storyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment storyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static storyFragment newInstance(String param1, String param2) {
        storyFragment fragment = new storyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.listDate);
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

    void loadimage()
    {
        ImageGallery imageGallery=new ImageGallery();
        images=imageGallery.listImage(getContext());
        ArrayList<String> date=new ArrayList<>();
        for( int i=0; i<images.size(); i++)
        {
            if(! date.contains(images.get(i).getDate()))
            {
                date.add(images.get(i).getDate());
            }
        }
        ArrayList<ItemDate> itemDates=new ArrayList<>();
        int Flagg=0;
        for( int i=0; i< date.size(); i++)
        {
            ArrayList<ItemImage>  itemImages=new ArrayList<>();
            for( int j=Flagg; j< images.size(); j++)
            {
                if( images.get(j).getDate().equals(date.get(i)))
                {
                    itemImages.add(images.get(j));
                }
                else
                {
                    Flagg=j;
                    break;
                }
            }
        galleryAdapter= new GalleryAdapter(getContext(), itemImages, new GalleryAdapter.PhotoListener() {

            @Override
            public void onPhotoClick(ItemImage itemImage) {
                int position=images.indexOf(itemImage);;
                String path = images.get(position).getPath();
                Intent intent = new Intent(getContext(), FullView.class);
                intent.putExtra("position",String.valueOf( position));
                intent.putExtra("name", String.valueOf("0"));
                intent.putExtra("imgpath", path);
                startActivity(intent);

            }
        });


            ItemDate itemDate = new ItemDate(galleryAdapter, date.get(i));
            itemDates.add(itemDate);
        }

        Date_Adapter date_adapter=new Date_Adapter(getContext(), itemDates);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(date_adapter);


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

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = 305;//listItem.getMeasuredHeight();

        float x = 1;
        if( items > columns ){
            x = items/columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false);
    }
}