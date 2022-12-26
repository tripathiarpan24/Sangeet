package com.example.sangeet;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView tittleTv,startTimeTv,totalTimeTv;
    ImageView pauseBtn,prevBtn,nextBtn,musicLogo;
    SeekBar seekbar;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int x =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        tittleTv = findViewById(R.id.song_tittle);
        startTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        pauseBtn = findViewById(R.id.pause);
        prevBtn = findViewById(R.id.previous);
        nextBtn = findViewById(R.id.next);
        musicLogo = findViewById(R.id.music_logo_big);
        seekbar = findViewById(R.id.seekbar);
        tittleTv.setSelected(true);
         songsList =(ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

         setResourcesWithMusic();
         MusicPlayerActivity.this.runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 if(mediaPlayer !=null)
                 {
                     seekbar.setProgress(mediaPlayer.getCurrentPosition());
                     startTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));


                 }
                 if(mediaPlayer.isPlaying()){
                     pauseBtn.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                     musicLogo.setRotation(x++);
                 }else{
                     pauseBtn.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                     musicLogo.setRotation(0);
                 }
                 new Handler().postDelayed(this,100);
             }
         });
         seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 if(mediaPlayer!=null&& b){
                     mediaPlayer.seekTo(i);
                 }

             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });
    }

    void setResourcesWithMusic(){
        currentSong = songsList.get(MyMediaPlayer.currentIndex);
        tittleTv.setText(currentSong.getTittle());
        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));


        pauseBtn.setOnClickListener(view -> pausePlay());
        nextBtn.setOnClickListener(view -> playNextSong());
        prevBtn.setOnClickListener(view -> playPrevSong());
        playSong();
    }

    private void playSong(){
        try {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekbar.setProgress(0);
            seekbar.setMax(mediaPlayer.getDuration());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private void playNextSong(){
        if(MyMediaPlayer.currentIndex == songsList.size()-1)
            return;
     MyMediaPlayer.currentIndex +=1 ;
     mediaPlayer.reset();
     setResourcesWithMusic();
    }
    private void playPrevSong(){
        if(MyMediaPlayer.currentIndex == 0)
            return;
        MyMediaPlayer.currentIndex -=1 ;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }
    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }


    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis)%TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis)%TimeUnit.MINUTES.toSeconds(1));
    }
}