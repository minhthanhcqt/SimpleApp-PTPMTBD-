package com.example.mygallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class DateAdapter extends BaseAdapter {
    Context context;
    List<ItemDate> list;

    public DateAdapter(Context context, List<ItemDate> list)
    {
        this.context=context;
        this.list=list;
    }
    private  class ViewHolder
    {
        ImageAdapter imageAdapter;
        TextView textView;
    }
    public View getView(int position , View convertView,ViewGroup parent)
    {
        DateAdapter.ViewHolder viewHolder=null;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v=layoutInflater.inflate(R.layout.rowliststory, null);
        ItemDate itemDate = (ItemDate) getItem(position);
        TextView textView = v.findViewById(R.id.datestory);
         GridView gridView = v.findViewById(R.id.dategrid);
        textView.setText(itemDate.getTextdate());
        gridView.setAdapter(itemDate.getImageAdapter());

        return v;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
