package com.example.qnuquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class AplashActivity extends AppCompatActivity {

    MediaPlayer intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplash);

        //play sound into
        intro = MediaPlayer.create(this,R.raw.swooh);
        intro.start();
        //run intro 3s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AplashActivity.this,IntroActivity1.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

}