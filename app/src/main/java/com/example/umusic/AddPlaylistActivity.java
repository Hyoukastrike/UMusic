package com.example.umusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import playlist.PlaylistPlaylist;

public class AddPlaylistActivity extends AppCompatActivity {

    private EditText etNamePlaylist, etIDPlaylist;
    private Button btnAddPlaylist;
    private ImageView btnBack;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);

        InitUi();

        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etIDPlaylist.getText().toString().trim();
                String name = etNamePlaylist.getText().toString().trim();
                PlaylistPlaylist playlist = new PlaylistPlaylist(id,name);

                onClickAddPlaylist(playlist);
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
        etNamePlaylist = findViewById(R.id.et_inputname_playlist);
        etIDPlaylist = findViewById(R.id.et_inputid_playlist);
        btnAddPlaylist = findViewById(R.id.btn_add_playlist);
        btnBack = findViewById(R.id.btn_back);
        progressBar = findViewById(R.id.progressBar);
    }



    private void onClickAddPlaylist(PlaylistPlaylist playlist) {
        btnAddPlaylist.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("playlists");

        String pathObject = String.valueOf(playlist.getId());
        myRef.child(pathObject).setValue(playlist, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                btnAddPlaylist.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddPlaylistActivity.this,"Tạo playlist thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
}