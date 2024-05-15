package com.example.umusic.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.umusic.R;
import com.example.umusic.fragment.MusicFragment;
import com.example.umusic.fragment.PlaylistFragment;
import com.example.umusic.fragment.VideoFragment;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class PlaylistMusicx extends AppCompatActivity {

    private static final String TAG = PlaylistMusicx.class.getName();

    private AnimatedBottomBar animatedBottomBar;
    private ImageView btnBack;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_musicx);

        InitUi();

        if (savedInstanceState == null){
            animatedBottomBar.selectTabById(R.id.action_music,true);
            fragmentManager = getSupportFragmentManager();
            MusicFragment musicFragment = new MusicFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,musicFragment).commit();
        }

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()){
                    case R.id.action_music:
                        fragment = new MusicFragment();
                        break;
                    case R.id.action_playlist:
                        fragment = new PlaylistFragment();
                        break;
                    case R.id.action_video:
                        fragment = new VideoFragment();
                        break;
                }

                if (fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
                }
                else {
                    Log.e(TAG, "Lỗi chuyển trang");
                }

            }
            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }

        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void InitUi() {
        animatedBottomBar = findViewById(R.id.animated_bottom_nav);
        btnBack = findViewById(R.id.btn_back);

    }
}