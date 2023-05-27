package com.example.qnuquizapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qnuquizapp.R;

public class LoseActivity extends AppCompatActivity {

    MediaPlayer md_lose,md_btn;
    TextView txt_score, txt_stime, btn_playAgain,btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        findID();

        //nhận data từ MainActivity _____________________________________
        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        String stime = intent.getStringExtra("stime");
        txt_score.setText(score);
        txt_stime.setText(stime);

        md_lose = MediaPlayer.create(this,R.raw.failll);
        md_lose.start();
        md_btn = MediaPlayer.create(this,R.raw.btn);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                md_btn.start();
                Intent intent = new Intent(LoseActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });
        btn_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md_btn.start();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findID() {
        txt_score = (TextView) findViewById(R.id.txt_score);
        txt_stime = (TextView) findViewById(R.id.txt_stime);
        btn_playAgain = (TextView) findViewById(R.id.btn_playagian);
        btn_exit = (TextView) findViewById(R.id.btn_Exit);
    }
}