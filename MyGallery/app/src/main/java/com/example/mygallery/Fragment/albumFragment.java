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

import com.example.mygallery.AlbumAdapter;
import com.example.mygallery.ImageAdapter;
import com.example.mygallery.R;
import com.example.mygallery.RowItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link albumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class albumFragment extends Fragment {
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
       Integer[]images=new Integer[]{R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,R.drawable.img6};
        String[]titles=new String[]{ "Screen", "Story", "My Life", "Sad Story", "Happy", "SonTung"};
        String[] descriptions = new String[] {
                "It is an  ",
                "It is the largest ", "Citrus Fruit",
                "Mixed Fruits", "Chung ta", "Hien tai" };
        ArrayList<RowItem> rowItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
            rowItems.add(item);
        }
        AlbumAdapter adapter = new AlbumAdapter( getContext(),rowItems);
        View v = getView();
        ListView gridView = getView().findViewById(R.id.listalbum);
        gridView.setAdapter(adapter);

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