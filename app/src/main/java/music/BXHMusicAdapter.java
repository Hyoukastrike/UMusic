package music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.R;

import java.util.List;

public class BXHMusicAdapter extends RecyclerView.Adapter<BXHMusicAdapter.BXHMusicViewHolder> {
    private List<BXHMusic> bxhMusics;

    public void setData(List<BXHMusic> list){
        this.bxhMusics = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BXHMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bxh,parent,false);
        return new BXHMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BXHMusicViewHolder holder, int position) {
        BXHMusic bxhMusic = bxhMusics.get(position);
        if (bxhMusic == null){
            return;
        }

        holder.imgBXHMusic.setImageResource(bxhMusic.getResourceId());
        holder.tvNums.setText(bxhMusic.getNums());
        holder.tvTitleBXHMusic.setText(bxhMusic.getTitle_music());
        holder.tvTitleBXHAuthor.setText(bxhMusic.getTitle_author());
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
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvTitleBXHMusic = itemView.findViewById(R.id.tv_title);
            tvTitleBXHAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
