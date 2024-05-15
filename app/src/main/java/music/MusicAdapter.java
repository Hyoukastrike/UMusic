package music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.R;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private List<Music> mMusics;

    public void setData(List<Music> list){
        this.mMusics = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music music = mMusics.get(position);
        if (music == null){
            return;
        }

        holder.imgMusic.setImageResource(music.getResourceId());
        holder.tvTitleMusic.setText(music.getTitle());
        holder.tvTitleAuthor.setText(music.getTitle_author());
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
