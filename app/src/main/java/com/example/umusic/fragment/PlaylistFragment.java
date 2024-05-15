package com.example.umusic.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import playlist.MusicPlaylist;
import playlist.MusicPlaylistAdapter;
import playlist.PlaylistPlaylist;
import playlist.PlaylistPlaylistAdapter;

public class PlaylistFragment extends Fragment {

    private RecyclerView rvPlaylistFragment;
    private ArrayList<PlaylistPlaylist> mPlaylistPlaylists;
    private PlaylistPlaylistAdapter mPlaylistPlaylistAdapter;
    private ImageView imgPlaylistPlaylist;
    private TextView tvPlaylistPlaylist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist,container,false);

        InitUi(view);

        getListPlaylistFromRealTimeDatabase();

        return view;
    }

    private void InitUi(View view) {
        mPlaylistPlaylists = new ArrayList<>();
        imgPlaylistPlaylist = view.findViewById(R.id.img_playlist_playlist);
        tvPlaylistPlaylist = view.findViewById(R.id.tv_playlist_playlist);
        rvPlaylistFragment = view.findViewById(R.id.rv_playlist_fragment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvPlaylistFragment.setLayoutManager(linearLayoutManager);

    }

    private void getListPlaylistFromRealTimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("playlists");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PlaylistPlaylist playlistPlaylist = new PlaylistPlaylist();
                    if (dataSnapshot.exists()){
                        String name = dataSnapshot.child("namePlaylistPlaylist").getValue().toString();
                        String id = dataSnapshot.child("id").getValue().toString();
                        playlistPlaylist.setNamePlaylistPlaylist(name, id);

                        mPlaylistPlaylists.add(playlistPlaylist);
                    }
                }

                mPlaylistPlaylistAdapter = new PlaylistPlaylistAdapter(mPlaylistPlaylists, new PlaylistPlaylistAdapter.IClickPlaylistListener() {
                    @Override
                    public void onClickDeleteItem(PlaylistPlaylist playlist) {
                        onClickDeleteData(playlist);
                    }
                });
                rvPlaylistFragment.setAdapter(mPlaylistPlaylistAdapter);
                mPlaylistPlaylistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Tải dữ liệu không thành công, không có kết nối mạng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAll() {
        if (mPlaylistPlaylists != null){
            mPlaylistPlaylists.clear();
            if (mPlaylistPlaylistAdapter != null){
                mPlaylistPlaylistAdapter.notifyDataSetChanged();
            }
        }
    }

    private void onClickDeleteData(PlaylistPlaylist playlist) {
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn xóa playlist này?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("playlists");

                        myRef.child(playlist.getId()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                            }
                        });

                        myRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                PlaylistPlaylist playlist1 = snapshot.getValue(PlaylistPlaylist.class);
                                if (playlist1 == null || mPlaylistPlaylists == null || mPlaylistPlaylists.isEmpty()){
                                    return;
                                }
                                for (int i = 0; i < mPlaylistPlaylists.size(); i++){
                                    if (playlist1.getId() == mPlaylistPlaylists.get(i).getId()){
                                        mPlaylistPlaylists.remove(mPlaylistPlaylists.get(i));
                                        break;
                                    }
                                }
                                mPlaylistPlaylistAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                })
                .setNegativeButton("Hủy",null).show();
    }
}
