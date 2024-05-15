package com.example.umusic.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.umusic.R;
import com.example.umusic.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.umusic.AddPlaylistActivity;
import com.example.umusic.category.PlaylistMusicx;

public class PersonalFragmentx extends Fragment {

    private TextView btnUserInfo, tvName, CountPlaylist, EnjoyPlaylist01, EnjoyPlaylist02;
    private Uri mUri;
    private ImageView imgAva, btnAddPlaylist;
    private LinearLayout mMusicPlaylist;
    private int countPlaylist = 0;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalx, container, false);

        tvName = view.findViewById(R.id.tv_name);
        mMusicPlaylist = view.findViewById(R.id.music_playlist_dowload);
        mMusicPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlaylistMusicx.class);
                startActivity(intent);
            }
        });

        btnUserInfo = view.findViewById(R.id.tv_user_info);
        imgAva = view.findViewById(R.id.img_ava);
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalFragmentx.this.getActivity(), UserActivity.class);
                startActivity(intent);
            }
        });

        btnAddPlaylist = view.findViewById(R.id.btn_add_playlist);
        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalFragmentx.this.getActivity(), AddPlaylistActivity.class);
                startActivity(intent);
            }
        });

        showUserInformation();

        EnjoyPlaylist01 = view.findViewById(R.id.enjoy_playlist01);
        EnjoyPlaylist02 = view.findViewById(R.id.enjoy_playlist02);
        CountPlaylist = view.findViewById(R.id.count_playlist);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("playlists");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    countPlaylist = (int) snapshot.getChildrenCount();
                    if (countPlaylist > 0){
                        EnjoyPlaylist01.setText("Tận hưởng playlist của bạn");
                        EnjoyPlaylist02.setVisibility(View.INVISIBLE);
                        CountPlaylist.setText("Playlist Đã Tạo (" + countPlaylist + ")");
                    }
                    else {
                        CountPlaylist.setText("Playlist Đã Tạo (0)");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

    private void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        mUri = user.getPhotoUrl();
        if (name == null){
            tvName.setText("Tên đăng nhập");
        }
        else {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);
            Glide.with(this).load(mUri).error(R.drawable.default_avatar).into(imgAva);
        }
    }

    private void onClickUpdateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){
            return;
        }

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"Đã cập nhật thành công",Toast.LENGTH_SHORT).show();
                            showUserInformation();
                        }
                    }
                });
    }
}
