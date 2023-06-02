package com.example.qnuquizapp.Activities;

import static com.example.qnuquizapp.R.drawable.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qnuquizapp.R;

public class ResultActivity extends AppCompatActivity {

    private MediaPlayer md_victory,md_lose,md_btn;
    private TextView txt_score,txt_time,txt_result;
    private ConstraintLayout btn_playAgain,btn_exit;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        txt_result = (TextView) findViewById(R.id.txt_Result);
        txt_score = (TextView) findViewById(R.id.txt_Score);
        txt_time = (TextView) findViewById(R.id.txt_time);
        btn_playAgain = (ConstraintLayout) findViewById(R.id.btn_playAgain);
        btn_exit = (ConstraintLayout) findViewById(R.id.btn_Exit);
        img = (ImageView) findViewById(R.id.imageView);

        //get sound effect
        md_lose = MediaPlayer.create(this,R.raw.failll);
        md_victory = MediaPlayer.create(this, R.raw.victory);
        md_btn = MediaPlayer.create(this, R.raw.btn);

        //nhận data từ MainActivity _____________________________________
        Intent intent = getIntent();
        int soDung = intent.getIntExtra("numCorrect",0);
        int time = intent.getIntExtra("time",0);

        if(soDung >=5){
            md_victory.start();
            img.setImageResource(congrats2_1);
            txt_result.setText("YouWin");
        }else {
            md_lose.start();
            img.setImageResource(lose1);
            txt_result.setText("You Lose!!!");
        }
        txt_score.setText(tinhDiem(soDung));
        txt_time.setText(tinhTimeConlai(time));

//

//
//
//        /////// display result ___________________________________________________
//        int score = tinhDiem(soDung);
//        int t = tinhTimeConlai(soTime);
//        if( score >=240){
//            md_victory.start();
//            txt_result.setText("You Congrats :)");
//        }else {
//            md_lose.start();
//            txt_result.setText("You Lose :(");
//        }
//        txt_score.setText(score);
//        txt_time.setText(tinhTimeConlai(t));

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md_btn.start();
                Intent intent = new Intent(getApplicationContext(),IntroActivity1.class);
                startActivity(intent);
            }
        });
        btn_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md_btn.start();
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(intent);
            }
        });
    }
    private String tinhDiem(int num){
        int temp = 0;
        temp = num * 10;
        return String.valueOf(temp);
    }
    private String tinhTimeConlai(int time){
        int temp = 0;
        temp = 60 - time;
        return String.valueOf(temp);
    }
}