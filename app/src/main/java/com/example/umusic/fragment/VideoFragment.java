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

public class VideoFragment extends Fragment {

    private RecyclerView rvVideoFragment;
    private ArrayList<MusicPlaylist> mMusicPlaylists;
    private MusicPlaylistAdapter mMusicPlaylistAdapter;
    private ImageView imgVideoPlaylist;
    private TextView tvVideoPlaylist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,container,false);

        InitUi(view);


        getListVideoFromRealTimeDatabase();

        return view;
    }

    private void InitUi(View view) {
        mMusicPlaylists = new ArrayList<>();
        imgVideoPlaylist = view.findViewById(R.id.img_music_playlist);
        tvVideoPlaylist = view.findViewById(R.id.tv_music_playlist);
        rvVideoFragment = view.findViewById(R.id.rv_video_fragment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvVideoFragment.setLayoutManager(linearLayoutManager);
    }

    private void getListVideoFromRealTimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("videos");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MusicPlaylist musicPlaylist = new MusicPlaylist();
                    if (dataSnapshot.exists()){
                        String name = dataSnapshot.child("name").getValue().toString();
                        String id = dataSnapshot.child("id").getValue().toString();
                        musicPlaylist.setImageurl(dataSnapshot.child("image").getValue().toString());
                        musicPlaylist.setNameMusicPlaylist(name, id);

                        mMusicPlaylists.add(musicPlaylist);
                    }


                }

                mMusicPlaylistAdapter = new MusicPlaylistAdapter(mMusicPlaylists, new MusicPlaylistAdapter.IClickListener() {
                    @Override
                    public void onClickDeleteItem(MusicPlaylist musicPlaylist) {
                        onClickDeleteData(musicPlaylist);
                    }
                });
                rvVideoFragment.setAdapter(mMusicPlaylistAdapter);
                mMusicPlaylistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Tải dữ liệu không thành công, không có kết nối mạng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAll() {
        if (mMusicPlaylists != null){
            mMusicPlaylists.clear();
            if (mMusicPlaylistAdapter != null){
                mMusicPlaylistAdapter.notifyDataSetChanged();
            }
        }
    }

    private void onClickDeleteData(MusicPlaylist musicPlaylist){
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn xóa video này?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("videos");

                        myRef.child(musicPlaylist.getId()).removeValue(new DatabaseReference.CompletionListener() {
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
                                MusicPlaylist musicPlaylist1 = snapshot.getValue(MusicPlaylist.class);
                                if (musicPlaylist1 == null || mMusicPlaylists == null || mMusicPlaylists.isEmpty()){
                                    return;
                                }
                                for (int i = 0; i < mMusicPlaylists.size(); i++){
                                    if (musicPlaylist1.getId() == mMusicPlaylists.get(i).getId()){
                                        mMusicPlaylists.remove(mMusicPlaylists.get(i));
                                        break;
                                    }
                                }
                                mMusicPlaylistAdapter.notifyDataSetChanged();
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
