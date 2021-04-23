package com.example.mygallery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mygallery.AlbumAdapter;
import com.example.mygallery.GalleryAdapter;
import com.example.mygallery.ImageAdapter;
import com.example.mygallery.ImageGallery;
import com.example.mygallery.ItemImage;
import com.example.mygallery.R;
import com.example.mygallery.RowItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link albumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class albumFragment extends Fragment {
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

    public albumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment albumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static albumFragment newInstance(String param1, String param2) {
        albumFragment fragment = new albumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageGallery imageGallery=new ImageGallery();
        images=imageGallery.listImage(getContext());
       ArrayList <String> titles=listAlBum();
        ArrayList<ItemImage> image=listImage(titles);
       ArrayList<String> descriptions=new ArrayList<>();
        ArrayList<RowItem> rowItems = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            descriptions.add("All Image and Video from Album "+titles.get(i));
            RowItem item = new RowItem(image.get(i), titles.get(i), descriptions.get(i));
            rowItems.add(item);
        }
        AlbumAdapter adapter = new AlbumAdapter( getContext(),rowItems);
        View v = getView();
        ListView gridView = getView().findViewById(R.id.listalbum);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Hhehe", ""+adapter.getItem(position));
            }
        });

    }
    public  ArrayList< String > listAlBum( )
    {
        ArrayList<String > album=new ArrayList<>();
        for( int i=0; i< images.size(); i++)
        {
            String path= images.get(i).getPath();
            String [] word=path.split("/");
            String newWord= word[word.length-2];
            if( !album.contains(newWord))
            {
                album.add(newWord);
            }

        }
        return album;


    }
    public ArrayList<ItemImage> listImage( ArrayList<String> album)
    {
        ArrayList<ItemImage> list=new ArrayList<>();
        for (int i=0; i<album.size(); i++)
        {
           for  ( int j=0; j <images.size(); j++)
           {
               String path= images.get(j).getPath();
               String [] word=path.split("/");
               String newWord= word[word.length-2];

               if( album.get(i).equals(newWord))
               {
                   list.add(images.get(j));
                   break;
               }
           }
        }
        return list;
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
        return inflater.inflate(R.layout.fragment_album2, container, false);
    }
}