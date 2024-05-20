package music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.MainActivity;
import com.example.umusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BXHMusicAdapter extends RecyclerView.Adapter<BXHMusicAdapter.BXHMusicViewHolder> {
    private ArrayList<BXHMusic> bxhMusics;
    Context context;

    public void setData(ArrayList<BXHMusic> list, Context context){
        this.bxhMusics = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BXHMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bxh,parent,false);
        return new BXHMusicViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull BXHMusicViewHolder holder, int position) {
        BXHMusic bxhMusic = bxhMusics.get(position);
        if (bxhMusic == null){
            return;
        }


        Picasso.get().load(bxhMusic.getImg()).fit().into(holder.imgBXHMusic);

        holder.tvTitleBXHMusic.setText(bxhMusic.getTitle_music());
        holder.tvTitleBXHAuthor.setText(bxhMusic.getTitle_author());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("ONLINE", bxhMusics);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                boolean isPlayingSongList = true; // Set the appropriate value here
                boolean isPlayingSongListBXH = true; // Set the appropriate value here
                intent.putExtra("IS_PLAYING_SONG_LIST", isPlayingSongList);
                intent.putExtra("IS_PLAYING_SONG_LIST_BXH", isPlayingSongListBXH);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        if (bxhMusics != null){
            return bxhMusics.size();
        }
        return 0;
    }

    public class BXHMusicViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgBXHMusic;
        private TextView tvNums;
        private TextView tvTitleBXHMusic;
        private TextView tvTitleBXHAuthor;

        public BXHMusicViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBXHMusic = itemView.findViewById(R.id.img_bxhmusic);
            tvTitleBXHMusic = itemView.findViewById(R.id.tv_title);
            tvTitleBXHAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
