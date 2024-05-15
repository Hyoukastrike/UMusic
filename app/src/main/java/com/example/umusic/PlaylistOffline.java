package com.example.umusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import music.AudioModel;
import music.MusicListAdapter;

public class PlaylistOffline extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvNoMusic;
    private ImageView btnBack, btnSort;
    private Toolbar toolbar;
    private SearchView searchView;
    private MusicListAdapter adapter;

    ArrayList<AudioModel> songLists = new ArrayList<>();
    private boolean isSorted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_offline);

        InitUi();

        setSupportActionBar(toolbar);

        if (checkpermission() == false){
            requestpermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);

        while (cursor.moveToNext()){
            AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));

            if (new File(songData.getPath()).exists()){
                songLists.add(songData);
            }
        }

        if (songLists.size() == 0){
            tvNoMusic.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songLists, getApplicationContext()));
        }

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSorted){
                    SortAscending();
                    isSorted = false;
                }
                else {
                    SortDescending();
                    isSorted = true;
                }
            }
        });

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("DEBUG", "onQueryTextChange: newText = " + newText);

//                filterList(newText);
                ArrayList<AudioModel> filter = filterList(newText);
                if (filter != null) {
                    for (AudioModel audioModel : filter) {
                        Log.d("DEBUG", "Filtered item: " + audioModel.getTitle());
                    }
                }
                adapter.setFilterList(filter);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }

    @SuppressLint("WrongViewCast")
    private void InitUi() {
        recyclerView = findViewById(R.id.rv_music_playlist);
        tvNoMusic = findViewById(R.id.no_song_text);
        btnBack = findViewById(R.id.btn_back);
        btnSort = findViewById(R.id.btn_sort);
        toolbar = findViewById(R.id.playlist_toolbar);
        searchView = findViewById(R.id.searchView);
        adapter = new MusicListAdapter(songLists,this);
    }


    boolean checkpermission(){
        int result = ContextCompat.checkSelfPermission(PlaylistOffline.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            return false;
        }
    }

    void requestpermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(this,"Cung cấp quyền truy cập là điều cần thiết, hãy thiết lập trong cài đặt",Toast.LENGTH_SHORT).show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerView != null){
            recyclerView.setAdapter(new MusicListAdapter(songLists, getApplicationContext()));
        }
    }

    private void SortAscending(){
        btnSort.setImageResource(R.drawable.ic_ascending_black);
        Collections.sort(songLists, new Comparator<AudioModel>() {
            @Override
            public int compare(AudioModel o1, AudioModel o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        });
    }

    private void SortDescending(){
        btnSort.setImageResource(R.drawable.ic_descending_black);
        Collections.reverse(songLists);
    }

    private ArrayList<AudioModel> filterList(String text) {
        ArrayList<AudioModel> filterList = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            filterList.addAll(songLists);
        } else {
            String searchText = text.toLowerCase();

            for (AudioModel audioModel : songLists) {
                if (audioModel.getTitle().toLowerCase().contains(searchText)) {
                    filterList.add(audioModel);
                }
            }
        }

        adapter.setFilterList(filterList);
        adapter.notifyDataSetChanged();
        return filterList;
    }


}