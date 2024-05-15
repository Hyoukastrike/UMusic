package com.example.umusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.umusic.LoginScreen;
import com.example.umusic.PlaylistOffline;
import com.example.umusic.R;

public class PersonalFragment extends Fragment {

    private TextView tvLogIn;
    private LinearLayout mMusicPlaylistDowload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        tvLogIn = view.findViewById(R.id.tv_login);
        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalFragment.this.getActivity(), LoginScreen.class);
                startActivity(intent);
            }
        });

        mMusicPlaylistDowload = view.findViewById(R.id.music_playlist_dowload);
        mMusicPlaylistDowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlaylistOffline.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
