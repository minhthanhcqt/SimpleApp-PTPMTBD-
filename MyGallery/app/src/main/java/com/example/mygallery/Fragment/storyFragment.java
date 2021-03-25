package com.example.mygallery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.mygallery.DateAdapter;
import com.example.mygallery.FaceAdapter;
import com.example.mygallery.GridAdapter;
import com.example.mygallery.ItemDate;
import com.example.mygallery.ItemFace;
import com.example.mygallery.ItemImage;
import com.example.mygallery.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link storyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class storyFragment extends Fragment {

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
        Integer[]images=new Integer[]{R.drawable.iconface, R.drawable.iconface, R.drawable.iconface, R.drawable.iconface, R.drawable.iconface,R.drawable.iconface};
        String[]titles=new String[]{ "Screen", "Story", "My Life", "Sad Story", "Happy", "SonTung"};

        ArrayList<ItemImage> itemImages = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ItemImage itemImage = new ItemImage(images[i]);
            itemImages.add(itemImage);
        }
        GridAdapter gridadapter = new GridAdapter( getContext(),itemImages);
        View view2=LayoutInflater.from(getContext()).inflate(R.layout.rowliststory, null);
        GridView gridView = view2.findViewById(R.id.dategrid);
        gridView.setAdapter(gridadapter);

        ArrayList<ItemDate> itemDates = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ItemDate itemDate = new ItemDate(gridadapter, titles[i]);
            itemDates.add(itemDate);
        }
        DateAdapter dateAdapter = new DateAdapter( getContext(),itemDates);
        ListView l = getView().findViewById(R.id.listdate);
        l.setAdapter(dateAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false);
    }
}