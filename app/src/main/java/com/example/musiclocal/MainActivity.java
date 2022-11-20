package com.example.musiclocal;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//lưu ý đặt tên file nhạc nhớ là chữ viết thường nha
//https://www.youtube.com/watch?v=1lSkr9anY6E&list=PLV8mKc52nY0U0-0HZ2tD8G6C5d0kiD0Yk&index=2
public class MainActivity extends AppCompatActivity {
    TextView startTime,endTime,textViewTitle;
    SeekBar seekBar;
    ImageButton btnPrev,btnNext,btnPlay,btnStop;
    ArrayList<Song> listSong=new ArrayList<>();
    MediaPlayer mediaPlayer;
    int position=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Detail();
        AddSong();
//        mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(position).getFile());
//        textViewTitle.setText(listSong.get(position).getTitle());
        initialMediaPlayer();
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play);
                initialMediaPlayer();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }
                else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                setEndTime();
                UpdateTimeSong();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position>=listSong.size())
                {
                    position=0;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                initialMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                UpdateTimeSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position<0)
                {
                    position=listSong.size()-1;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                initialMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                UpdateTimeSong();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }
    private void UpdateTimeSong(){
        final Handler handler= new Handler();
        handler.postDelayed(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        SimpleDateFormat dateFormat=new SimpleDateFormat("mm:ss");
                        startTime.setText(dateFormat.format(mediaPlayer.getCurrentPosition()));
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        handler.postDelayed(this,500);
                    }
                },100);
    }
    private void AddSong(){
        listSong.add(new Song("Rollercoaster: ",R.raw.rollercoaster));
        listSong.add(new Song("Friend zone: ",R.raw.friendzoneobito));
        listSong.add(new Song("When You Look At Me:",R.raw.whenyoulookatmelenaseachainsobito));
    }
    private void Detail()
    {
        startTime=findViewById(R.id.startTime);
        endTime=findViewById(R.id.endTime);
        seekBar=findViewById(R.id.seekbar);
        textViewTitle=findViewById(R.id.textViewTitle);
        btnNext=findViewById(R.id.btnnext);
        btnPrev=findViewById(R.id.btnprev);
        btnPlay=findViewById(R.id.btnplay);
        btnStop=findViewById(R.id.btnstop);
    }
    private void initialMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(position).getFile());
        textViewTitle.setText(listSong.get(position).getTitle());
    }
    private void setEndTime(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("mm:ss");
        endTime.setText(dateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
}