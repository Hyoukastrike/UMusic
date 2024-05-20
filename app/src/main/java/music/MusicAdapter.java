package music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.MainActivity;
import com.example.umusic.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    ArrayList<Music> mMusics;
    Context context;
    public MusicAdapter(ArrayList<Music> mMusics, Context context) {
        this.mMusics = mMusics;
        this.context = context;
    }

    public void setData(ArrayList<Music> list){
        this.mMusics = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music,parent,false);
        return new MusicViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder,int position) {
        Music music = mMusics.get(position);
        if (music == null){
            return;
        }

        Picasso.get().load(music.getImg()).fit().into(holder.imgMusic);

        holder.tvTitleMusic.setText(music.getTitle_music());
        holder.tvTitleAuthor.setText(music.getTitle_author());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("ONLINE", mMusics);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                boolean isPlayingSongList = true; // Set the appropriate value here
                intent.putExtra("IS_PLAYING_SONG_LIST", isPlayingSongList);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        if (mMusics != null){
            return mMusics.size();
        }
        return 0;
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgMusic;

        private String tvDuration;
        private String tvPath;
        private TextView tvTitleMusic;
        private TextView tvTitleAuthor;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMusic = itemView.findViewById(R.id.img_music);
            tvTitleMusic = itemView.findViewById(R.id.tv_title);
            tvTitleAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
