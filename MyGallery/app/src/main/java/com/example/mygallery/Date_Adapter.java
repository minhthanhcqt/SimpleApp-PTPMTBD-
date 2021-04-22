package com.example.mygallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Date_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context  context;
    List<ItemDate> itemDates;

    public  Date_Adapter( Context context, List<ItemDate> itemDates)
    {
        this.context=context;
        this.itemDates=itemDates;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rowliststory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder) holder;
        viewHolder.textView.setText(itemDates.get(position).getTextdate());
        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setLayoutManager(new GridLayoutManager(this.context, 4));
        viewHolder.recyclerView.setAdapter(itemDates.get(position).getGalleryAdapter());

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.date);
            recyclerView=itemView.findViewById(R.id.list);

        }
    }
    @Override
    public int getItemCount() {
        return itemDates.size();
    }
}
