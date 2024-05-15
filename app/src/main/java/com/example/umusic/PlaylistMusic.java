package com.example.umusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import playlist.MusicPlaylist;
import playlist.MusicPlaylistAdapter;

public class PlaylistMusic extends AppCompatActivity {
    private RecyclerView rvMusicPlaylist;
    private MusicPlaylistAdapter mMusicPlaylistAdapter;
    private ImageView imgMusicPlaylist, btnBack, btnSort;
    private TextView tvMusicPlaylist;
    private ArrayList<MusicPlaylist> mMusicPlaylists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_music);

        InitUi();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        getListMusicFromRealTimeDatabase();

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSort();
            }
        });
    }

    private void InitUi() {
        btnBack = findViewById(R.id.btn_back);
        rvMusicPlaylist = findViewById(R.id.rv_music_playlist);
        imgMusicPlaylist = findViewById(R.id.img_music_playlist);
        tvMusicPlaylist = findViewById(R.id.tv_music_playlist);
        btnSort = findViewById(R.id.btn_sort);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvMusicPlaylist.setLayoutManager(linearLayoutManager);

        mMusicPlaylists = new ArrayList<>();

    }

    private void getListMusicFromRealTimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("musics");

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
                rvMusicPlaylist.setAdapter(mMusicPlaylistAdapter);
                mMusicPlaylistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PlaylistMusic.this,"Tải dữ liệu không thành công, không có kết nối mạng",Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn xóa bài hát này?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("musics");

                        myRef.child(musicPlaylist.getId()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(PlaylistMusic.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
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

    private void onClickSort(){
        Collections.sort(mMusicPlaylists, new Comparator<MusicPlaylist>() {
            @Override
            public int compare(MusicPlaylist o1, MusicPlaylist o2) {
                return o1.getNameMusicPlaylist().compareToIgnoreCase(o2.getNameMusicPlaylist());
            }
        });

        mMusicPlaylistAdapter = new MusicPlaylistAdapter(mMusicPlaylists, new MusicPlaylistAdapter.IClickListener() {
            @Override
            public void onClickDeleteItem(MusicPlaylist musicPlaylist) {
                onClickDeleteItem(musicPlaylist);
            }
        });
        rvMusicPlaylist.setAdapter(mMusicPlaylistAdapter);
        mMusicPlaylistAdapter.notifyDataSetChanged();
        final boolean[] icon = {new Boolean(true)};

        if (icon[0] == true){
            btnSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Collections.reverse(mMusicPlaylists);

                    mMusicPlaylistAdapter = new MusicPlaylistAdapter(mMusicPlaylists, new MusicPlaylistAdapter.IClickListener() {
                        @Override
                        public void onClickDeleteItem(MusicPlaylist musicPlaylist) {
                            onClickDeleteItem(musicPlaylist);
                        }
                    });
                    rvMusicPlaylist.setAdapter(mMusicPlaylistAdapter);
                    mMusicPlaylistAdapter.notifyDataSetChanged();
                    icon[0] = new Boolean(false);

                }
            });
        }
        else {
            btnSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Collections.reverse(mMusicPlaylists);

                    mMusicPlaylistAdapter = new MusicPlaylistAdapter(mMusicPlaylists, new MusicPlaylistAdapter.IClickListener() {
                        @Override
                        public void onClickDeleteItem(MusicPlaylist musicPlaylist) {
                            onClickDeleteItem(musicPlaylist);
                        }
                    });
                    rvMusicPlaylist.setAdapter(mMusicPlaylistAdapter);
                    mMusicPlaylistAdapter.notifyDataSetChanged();
                    icon[0] = new Boolean(true);

                }
            });
        }

    }


}