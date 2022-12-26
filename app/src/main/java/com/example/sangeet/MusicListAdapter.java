package com.example.sangeet;

import static com.example.sangeet.MyMediaPlayer.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{
    ArrayList<AudioModel> songsList;
    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;

    }



@NonNull
@Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusicListAdapter.ViewHolder(view);
    }
@Override
   public void onBindViewHolder(MusicListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position){
        AudioModel songData = songsList.get(position);
        holder.tittleTextView.setText(songData.getTittle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInstance().reset();
                currentIndex = position;
                Intent intent = new Intent(context,MusicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }

        );

   }

    @Override
    public int getItemCount() {

        return songsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tittleTextView;
        ImageView iconImageView;
        public ViewHolder( View itemView) {
            super(itemView);
            tittleTextView =itemView.findViewById(R.id.music_tittle_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
        }
    }


}
