package com.example.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class songListAdaptor extends RecyclerView.Adapter<songListAdaptor.ViewHolder> {

    private Context context;
    private ArrayList<SongModel> arrayList;

    public songListAdaptor(Context context, ArrayList<SongModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_list_recycler, parent, false);
        return new songListAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SongModel model = arrayList.get(position);
        holder.name.setText(model.getName());
        holder.artist.setText(model.getArtist());
        try {
            holder.duration.setText(model.getDuration());
            Bitmap bm = BitmapFactory.decodeFile(model.getArt());
            Drawable img = Drawable.createFromPath(model.getArt());
            holder.songImg.setImageDrawable(img);
        } catch (Exception e) {
            //Log.e("Error : ", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList != null)
            return arrayList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, artist, duration;
        public ImageView songImg, fav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.song_list_name);
            artist = itemView.findViewById(R.id.song_list_artist);
            duration = itemView.findViewById(R.id.duration);
            fav = itemView.findViewById(R.id.fav);
            songImg = itemView.findViewById(R.id.song_list_img);
        }
    }
}
