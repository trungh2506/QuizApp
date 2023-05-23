package com.example.qnuquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    private TextView btn_exit,txt_score,btn_playagian,txt_stime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        btn_exit = (TextView) findViewById(R.id.btn_wExit);
        btn_playagian = (TextView)findViewById(R.id.btn_playagian);
        txt_score = (TextView)findViewById(R.id.txt_score);
        txt_stime = (TextView)findViewById(R.id.txt_stime);
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String value = extras.getString("numcorrect");
//            txt_score.setText(value);
//        }
        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        String stime = intent.getStringExtra("stime");
        txt_score.setText(score);
        txt_stime.setText(stime);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoseActivity.class);
                startActivity(intent);
            }
        });
        btn_playagian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_score.setText("Soulixai");
            }
        });
    }
}