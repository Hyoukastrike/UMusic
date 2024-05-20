package com.example.umusic.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.umusic.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import music.AudioModel;
import music.Music;
import music.MusicAdapter;
import music.MusicListAdapter;
import music.MyMediaPlayer;

public class PlayFragment extends Fragment {
    TextView tvTitle, tvCurrentTime, tvTotalTime;
    SeekBar seekBar;
    ImageView pausePlay, btnNext, btnPrevious, icMusic, btnVolume;
    ArrayList<AudioModel> songLists;
    ArrayList<Music> songOnlineLists;
    AudioModel currentSong;
    MediaPlayer mediaPlayerOnline;
    Music currentOnlineSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    boolean isPlayingSongList;
//    MediaPlayer mediaPlayerOnline = MyMediaPlayer.getInstance();
    int x = 0;
    private boolean isMuted = false;

    public PlayFragment(){

    }
    public PlayFragment(ArrayList<AudioModel> songLists, ArrayList<Music> songOnlineLists) {
        this.songLists = songLists;
        this.songOnlineLists = songOnlineLists;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        InitUi(view);
//        Toast.makeText(getContext(),currentOnlineSong.getPath().toString(),Toast.LENGTH_SHORT);
        setResourcesWithMusic();

        PlayFragment.this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    tvCurrentTime.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+ ""));

                    if (mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                        icMusic.setRotation(x++);
                    }
                    else{
                        pausePlay.setImageResource(R.drawable.baseline_play_circle_outline_24);
                        icMusic.setRotation(0);
                    }

                }

                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextSong();
            }
        });

        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                if (isMuted) {
                    // Nếu đang tắt tiếng, chuyển thành âm lượng tối đa
                    btnVolume.setImageResource(R.drawable.baseline_volume_up_24);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                    isMuted = false;
                }
                else {
                    // Nếu đang bật tiếng, chuyển thành tắt tiếng
                    btnVolume.setImageResource(R.drawable.baseline_volume_off_24);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC), 0);
                    isMuted = true;
                }
            }

        });

        return view;
    }

    private void InitUi(View view) {
        tvTitle = view.findViewById(R.id.song_title);
        tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvTitle.setSelected(true);
        tvCurrentTime = view.findViewById(R.id.current_time);
        tvTotalTime = view.findViewById(R.id.total_time);
        seekBar = view.findViewById(R.id.seek_bar);
        pausePlay = view.findViewById(R.id.pause_play);
        btnNext = view.findViewById(R.id.next);
        btnPrevious = view.findViewById(R.id.previous);
        btnVolume = view.findViewById(R.id.volume);
        icMusic = view.findViewById(R.id.music_icon_big);
        mediaPlayerOnline = new MediaPlayer();

        songLists = (ArrayList<AudioModel>) getActivity().getIntent().getSerializableExtra("LIST");
        songOnlineLists = (ArrayList<Music>) getActivity().getIntent().getSerializableExtra("ONLINE");
        isPlayingSongList = getActivity().getIntent().getBooleanExtra("IS_PLAYING_SONG_LIST", true);



    }

    private void playOfflineMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private void playOnlineMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentOnlineSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private void playMusic() {
        if (!isPlayingSongList) {
            playOfflineMusic();
        } else {
            playOnlineMusic();
        }
    }

    void setResourcesWithMusicOffline(){
        if (songLists == null){
            return;
        }
        currentSong = songLists.get(MyMediaPlayer.currentIndex);

        tvTitle.setText(currentSong.getTitle());
        tvTotalTime.setText(convertToMMSS(currentSong.getDuration()));
        pausePlay.setOnClickListener(v -> pausePlay());
        btnNext.setOnClickListener(v -> playNextSong());
        btnPrevious.setOnClickListener(v -> playPreviousSong());

        playMusic();
    }

    void setResourcesWithMusicOnline(){
        if (songOnlineLists == null){
            return;
        }
        currentOnlineSong = songOnlineLists.get(MyMediaPlayer.currentIndex);

        tvTitle.setText(currentOnlineSong.getTitle_music());
        tvTotalTime.setText(convertToMMSS(currentOnlineSong.getDuration()));
        pausePlay.setOnClickListener(v -> pausePlay());
        btnNext.setOnClickListener(v -> playNextSong());
        btnPrevious.setOnClickListener(v -> playPreviousSong());

        playMusic();
    }

    private void setResourcesWithMusic() {
        if (!isPlayingSongList) {
            setResourcesWithMusicOffline();
        } else {
            setResourcesWithMusicOnline();
        }
    }

    private void playNextSong(){
        if (songLists == null){
            return;
        }
        else if (MyMediaPlayer.currentIndex == songLists.size()-1){
            return;
        }

        MyMediaPlayer.currentIndex += 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }
    private void playPreviousSong(){
        if (MyMediaPlayer.currentIndex == 0){
            return;
        }
        MyMediaPlayer.currentIndex -= 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }
    private void pausePlay(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        else
            mediaPlayer.start();
    }

    @SuppressLint("DefaultLocale")
    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }






}
