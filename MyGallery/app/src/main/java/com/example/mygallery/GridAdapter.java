/*package com.example.mygallery;


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

public class GridAdapter extends BaseAdapter {
    Context context;
    List<ItemImage> list;

    public GridAdapter(Context context, List<ItemImage> list)
    {
        this.context=context;
        this.list=list;
    }
    private  class ViewHolder
    {
        ImageButton imageButton;
    }
    public View getView(int position , View convertView,ViewGroup parent)
    {
        ViewHolder viewHolder=null;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v=layoutInflater.inflate(R.layout.image_date, null);
        ItemImage itemImage = (ItemImage) getItem(position);
        ImageButton imageButton = v.findViewById(R.id.imagedate);
        imageButton.setImageResource(itemImage.getImageID());
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


 */
