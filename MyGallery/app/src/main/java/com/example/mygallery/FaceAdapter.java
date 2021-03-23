package com.example.mygallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FaceAdapter extends BaseAdapter {
    Context context;
    List<ItemFace> list;

    public FaceAdapter(Context context, List<ItemFace> list)
    {
        this.context=context;
        this.list=list;
    }
    private  class ViewHolder
    {
         ImageButton imageButton;
         TextView textView;
    }
    public View getView(int position , View convertView,ViewGroup parent)
    {
        ViewHolder viewHolder=null;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v=layoutInflater.inflate(R.layout.faceitem, null);
        ItemFace itemFace = (ItemFace) getItem(position);
        TextView textView = v.findViewById(R.id.nameface);
        ImageButton imageButton = v.findViewById(R.id.imageface);
        textView.setText(itemFace.getNumimage());
        imageButton.setImageResource(itemFace.getFaceID());

        return v;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }


}
