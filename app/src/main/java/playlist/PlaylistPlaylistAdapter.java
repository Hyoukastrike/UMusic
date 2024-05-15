package playlist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.umusic.R;

import java.util.ArrayList;

public class PlaylistPlaylistAdapter extends RecyclerView.Adapter<PlaylistPlaylistAdapter.PlaylistPlaylistViewHoler> {
    private MenuBuilder menuBuilder;

    private ArrayList<PlaylistPlaylist> mPlaylistPlaylist;
    private IClickPlaylistListener iClickPlaylistListener;
    public interface IClickPlaylistListener{
        void onClickDeleteItem(PlaylistPlaylist playlist);
    }

    public PlaylistPlaylistAdapter(ArrayList<PlaylistPlaylist> mPlaylistPlaylist, IClickPlaylistListener iClickPlaylistListener) {
        this.mPlaylistPlaylist = mPlaylistPlaylist;
        this.iClickPlaylistListener = iClickPlaylistListener;
    }

    @NonNull
    @Override
    public PlaylistPlaylistViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_playlist,parent,false);
        return new PlaylistPlaylistViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistPlaylistViewHoler holder, int position) {
        PlaylistPlaylist playlist = mPlaylistPlaylist.get(position);

        holder.tvPlaylistPlaylist.setText(mPlaylistPlaylist.get(position).getNamePlaylistPlaylist());

        holder.btnMorePlaylist.setOnClickListener(new View.OnClickListener() {
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
                            case R.id.delete_music:
                                iClickPlaylistListener.onClickDeleteItem(playlist);
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
        if (mPlaylistPlaylist != null){
            return mPlaylistPlaylist.size();
        }
        return 0;
    }

    public class PlaylistPlaylistViewHoler extends RecyclerView.ViewHolder{

        private TextView tvPlaylistPlaylist;
        private ImageButton btnMorePlaylist;

        @SuppressLint("RestrictedApi")
        public PlaylistPlaylistViewHoler(@NonNull View itemView) {
            super(itemView);

            tvPlaylistPlaylist = itemView.findViewById(R.id.tv_playlist_playlist);

            btnMorePlaylist = itemView.findViewById(R.id.btn_more_playlist);
            menuBuilder = new MenuBuilder(itemView.getContext());
            MenuInflater inflater = new MenuInflater(itemView.getContext());
            inflater.inflate(R.menu.popup_menu_playlist, menuBuilder);
        }
    }
}
