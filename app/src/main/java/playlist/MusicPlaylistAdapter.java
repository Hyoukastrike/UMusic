package playlist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.umusic.R;

import java.util.ArrayList;

public class MusicPlaylistAdapter extends RecyclerView.Adapter<MusicPlaylistAdapter.MusicPlayListViewHolder> {
    private MenuBuilder menuBuilder;

    private ArrayList<MusicPlaylist> mMusicPlaylist;
    private IClickListener mIClickListener;
    public interface IClickListener{
        void onClickDeleteItem(MusicPlaylist musicPlaylist);
    }

    public MusicPlaylistAdapter(ArrayList<MusicPlaylist> mMusicPlaylist, IClickListener listener) {
        this.mMusicPlaylist = mMusicPlaylist;
        this.mIClickListener = listener;
    }

    @NonNull
    @Override
    public MusicPlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_playlist,parent,false);
        return new MusicPlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicPlayListViewHolder holder, int position) {
        MusicPlaylist musicPlaylist = mMusicPlaylist.get(position);

        Glide.with(holder.itemView.getContext()).load(mMusicPlaylist.get(position).getImageurl()).error(R.drawable.default_avatar).into(holder.imgMusicPlaylist);


        holder.tvMusicPlaylist.setText(mMusicPlaylist.get(position).getNameMusicPlaylist());

        holder.btnMoreMusic.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuPopupHelper optionMenu = new MenuPopupHelper(v.getContext(), menuBuilder, v);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.music_favor:
                                return true;
                            case R.id.music_add_playlist:
                                return true;
                            case R.id.delete_music:
                                mIClickListener.onClickDeleteItem(musicPlaylist);
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMusicPlaylist != null){
            return mMusicPlaylist.size();
        }
        return 0;
    }

    public class MusicPlayListViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgMusicPlaylist;
        private TextView tvMusicPlaylist;
        private ImageButton btnMoreMusic;


        @SuppressLint("RestrictedApi")
        public MusicPlayListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMusicPlaylist = itemView.findViewById(R.id.img_music_playlist);
            tvMusicPlaylist = itemView.findViewById(R.id.tv_music_playlist);
            btnMoreMusic = itemView.findViewById(R.id.btn_more_playlist);
            menuBuilder = new MenuBuilder(itemView.getContext());
            MenuInflater inflater = new MenuInflater(itemView.getContext());
            inflater.inflate(R.menu.popup_menu, menuBuilder);
        }
    }

}
