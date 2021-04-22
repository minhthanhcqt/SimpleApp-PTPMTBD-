package com.example.mygallery;
import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URLConnection;
import java.util.List;
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ItemImage> images;
    protected  PhotoListener photoListener;

    public GalleryAdapter( Context context, List<ItemImage> images, PhotoListener  photoListener)
    {
        this.context=context;
        this.images=images;
        this.photoListener=photoListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0) {
            return new ViewHolder_images(LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false));
        }
        else
            return  new ViewHolder_video(LayoutInflater.from(context).inflate(R.layout.video_view, parent, false));
    }
    boolean isImage(String path)
    {
        String mimeType= URLConnection.guessContentTypeFromName(path);
        return mimeType!=null &&mimeType.startsWith("image");
    }
    @Override
    public int getItemViewType(int position) {
        if(isImage(images.get(position).getPath()))
        {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder_images viewHolder_images = (ViewHolder_images) holder;
                String image=images.get(position).getPath();
                Glide.with(context).load(image).into(viewHolder_images.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoListener.onPhotoClick(images.get(position));
                    }
                });
                break;

            case 1:
                ViewHolder_video viewHolder_video = (ViewHolder_video) holder;
                String video=images.get(position).getPath();
                Glide.with(context).load(video).into(viewHolder_video.videoView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoListener.onPhotoClick(images.get(position));
                    }
                });
                break;

        }

    }

    @Override
    public int getItemCount() {
        return images.size();
    }



    public class ViewHolder_images extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder_images(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagegallerry);

        }
    }
    public class ViewHolder_video extends RecyclerView.ViewHolder{
        ImageView videoView;
        TextView textView;
        public ViewHolder_video(@NonNull View itemView) {
            super(itemView);
            videoView=itemView.findViewById(R.id.video);

        }
    }
   public interface  PhotoListener
   {
       void  onPhotoClick(ItemImage itemImage);
   }

}
