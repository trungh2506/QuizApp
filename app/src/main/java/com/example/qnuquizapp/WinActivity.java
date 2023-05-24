package com.example.qnuquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    MediaPlayer md_victory,md_btn;

    private TextView btn_exit,txt_score,btn_playagian,txt_stime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        findId();
        //nhận data từ MainActivity _____________________________________
        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        String stime = intent.getStringExtra("stime");
        txt_score.setText(score);
        txt_stime.setText(stime);

        //get sound effect
        md_victory  = MediaPlayer.create(this, R.raw.victory);
        md_btn = MediaPlayer.create(this,R.raw.btn);
        md_victory.start();

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                md_btn.start();
                Intent intent = new Intent(WinActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });
        btn_playagian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md_btn.start();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findId() {
        btn_exit = (TextView) findViewById(R.id.btn_wHome);
        btn_playagian = (TextView)findViewById(R.id.btn_playagian);
        txt_score = (TextView)findViewById(R.id.txt_score);
        txt_stime = (TextView)findViewById(R.id.txt_stime);
    }
}