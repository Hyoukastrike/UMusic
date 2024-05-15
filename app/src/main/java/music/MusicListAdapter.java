package music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.MainActivity;
import com.example.umusic.PlaylistMusic;
import com.example.umusic.PlaylistOffline;
import com.example.umusic.R;
import com.example.umusic.fragment.PlayFragment;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>{

    ArrayList<AudioModel> songLists;

    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songLists, Context context) {
        this.songLists = songLists;
        this.context = context;
    }
    public void setFilterList(ArrayList<AudioModel> list){
        this.songLists = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music_playlist,parent,false);
        return new MusicListViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        AudioModel songData = songLists.get(position);
        holder.tvTitle.setText(songData.getTitle());

        if (MyMediaPlayer.currentIndex == position){
            holder.tvTitle.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            holder.tvTitle.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("LIST", songLists);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        if (songLists != null){
            return songLists.size();
        }
        return 0;
    }


    public class MusicListViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        ImageView imgIcon;

        public MusicListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_music_playlist);
            imgIcon = itemView.findViewById(R.id.img_music_playlist);
        }
    }


}
