package com.example.qnuquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qnuquizapp.Models.QuestionModels;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    MediaPlayer md_collect,md_wrong;
    Button btn_next;
    TextView txt_opA,txt_opB,txt_opC,txt_opD,txt_QT,txt_countQT,txt_countdown;
    ConstraintLayout btn_1,btn_2,btn_3,btn_4;
    ArrayList<QuestionModels> question_list = new ArrayList<>();
    private int CountQuestion = 0;
    private int numCorrect = 0;
    private int stime = 0;
    private int score = 0;
    MyCountDownTimer countDownTimer = new MyCountDownTimer(35000 , 1000);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();

        //get data
        QuestionSetOne();

        //display data
        bindingData(0);

        //start countdown
        countDownTimer.start();

        //get sound effect
        md_collect  = MediaPlayer.create(this, R.raw.ding);
        md_wrong = MediaPlayer.create(this,R.raw.wromg);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra STT câu hỏi
                if(CountQuestion < 9){
                    CountQuestion+=1;
                    // In ra câu hỏi STT đó
                    bindingData(CountQuestion);
                    // In STT
                    String a = String.valueOf(CountQuestion+1)+"/10";
                    txt_countQT.setText(a);

                    //bật và Reset bg nút Option để cho người dùng chọn đáp án
                    EnableBtnOption();
                    ResetBackGroundBTNOption();

                    //nếu đủ 10 câu hỏi thì tính điêm
                }else {
                    countDownTimer.cancel();
                    stime = Integer.parseInt(String.valueOf(txt_countdown.getText()));
                    score = numCorrect *(35- score);
                    if(score >= 140){
                        Intent intent = new Intent(MainActivity.this,WinActivity.class);
                        intent.putExtra("score",String.valueOf(score));
                        intent.putExtra("stime",String.valueOf(stime));
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(MainActivity.this,LoseActivity.class);
                        intent.putExtra("score",String.valueOf(score));
                        intent.putExtra("stime",String.valueOf(stime));
                        startActivity(intent);

                    }

                }
            }
        });
    }


    private void findID(){
        txt_countdown = (TextView)findViewById(R.id.countdownTimer);
        btn_1 = (ConstraintLayout) findViewById(R.id.btn_optionA);
        btn_2 = (ConstraintLayout) findViewById(R.id.btn_optionB);
        btn_3 = (ConstraintLayout) findViewById(R.id.btn_optionC);
        btn_4 = (ConstraintLayout) findViewById(R.id.btn_optionD);
        txt_QT = (TextView) findViewById(R.id.txt_question);
        txt_opA = (TextView) findViewById(R.id.txt_optionA);
        txt_opB = (TextView) findViewById(R.id.txt_optionB);
        txt_opC = (TextView) findViewById(R.id.txt_optionC);
        txt_opD = (TextView) findViewById(R.id.txt_optionD);
        txt_countQT = (TextView)findViewById(R.id.txtCountQT);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    //bấm giờ//////////////////////////
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onTick(long l) {
            txt_countdown.setText((l / 1000) + "");
        }

        @Override
        public void onFinish() {

            Toast.makeText(MainActivity.this,"Time's up",Toast.LENGTH_SHORT).show();

            countDownTimer.cancel();
        }
    }


    private void QuestionSetOne(){
        question_list.add(new QuestionModels("1. 1+1=?","1","3","4","2"));
        question_list.add(new QuestionModels("2. 1+2=?","1","2","4","3"));
        question_list.add(new QuestionModels("3. 1+3=?","1","3","2","4"));
        question_list.add(new QuestionModels("4. 1+4=?","1","3","4","5"));
        question_list.add(new QuestionModels("5. 1+5=?","1","3","4","6"));
        question_list.add(new QuestionModels("6. 1+6=?","1","3","4","7"));
        question_list.add(new QuestionModels("7. 1+7=?","1","3","4","8"));
        question_list.add(new QuestionModels("8. 1+8=?","1","3","4","9"));
        question_list.add(new QuestionModels("9. 2+9=?","1","3","4","11"));
        question_list.add(new QuestionModels("10. 4+9=?","1","3","4","13"));

    }
    private void bindingData(int number){
        //random option ___________________________________________________
        ArrayList<String>opTion = new ArrayList<>();
        ArrayList<String>opTion_ranDom = new ArrayList<>();

        // lấy option vào  ArrayList tên opTion
        opTion.add(question_list.get(number).getOptionA());
        opTion.add(question_list.get(number).getOptionB());
        opTion.add(question_list.get(number).getOptionC());
        opTion.add(question_list.get(number).getCorrectAnswer());

        //Random opTion vào opTion_ranDom
        Collections.shuffle(opTion);
        for(String item : opTion){
            opTion_ranDom.add(item);
        }
        //Print random
        txt_QT.setText(question_list.get(number).getQuestion());
        txt_opA.setText(opTion_ranDom.get(0));
        txt_opB.setText(opTion_ranDom.get(1));
        txt_opC.setText(opTion_ranDom.get(2));
        txt_opD.setText(opTion_ranDom.get(3));

        //đóng nút Next để khi chưa chọn đáp án nào thì không cho phép sang ccau khác
        btn_next.setEnabled(false);
    }
    public void Answer(View v){
        String getAnswer = "";
        switch (v.getId()){
            case R.id.btn_optionA:

                //lấy đáp án người dùng
                getAnswer = txt_opA.getText().toString();

                // kiểm tra đúng hay không
                if(CheckAnswer(txt_countQT.getText().toString(),getAnswer)){
                    //Cộn câu đúng
                    numCorrect++;
                    //play sound
                    md_collect.start();
                    //đúng thì đổi Background thành màu xanh lá
                    btn_1.setBackgroundResource(R.drawable.btn_green);
                }else{
                    //play sound
                    md_wrong.start();
                    //xai thì đổi backgroun thành màu đỏ
                    btn_1.setBackgroundResource(R.drawable.btn_red);
                }
                //đóng nút để không cho phép chọn câu khác
                DisableBtnOption();
                //bật nút next để sang câu hỏi tiếp theo
                btn_next.setEnabled(true);
                break;

            case R.id.btn_optionB:
                getAnswer = txt_opB.getText().toString();
                if(CheckAnswer(txt_countQT.getText().toString(),getAnswer)){
                    numCorrect++;
                    md_collect.start();
                    btn_2.setBackgroundResource(R.drawable.btn_green);
                }else{
                    md_wrong.start();
                    btn_2.setBackgroundResource(R.drawable.btn_red);
                }
                DisableBtnOption();
                btn_next.setEnabled(true);
                break;

            case R.id.btn_optionC:
                getAnswer = txt_opC.getText().toString();
                if(CheckAnswer(txt_countQT.getText().toString(),getAnswer)){
                    numCorrect++;
                    md_collect.start();
                    btn_3.setBackgroundResource(R.drawable.btn_green);
                }else{
                    md_wrong.start();
                    btn_3.setBackgroundResource(R.drawable.btn_red);
                }
                DisableBtnOption();
                btn_next.setEnabled(true);
                break;
            case R.id.btn_optionD:
                getAnswer = txt_opD.getText().toString();
                if(CheckAnswer(txt_countQT.getText().toString(),getAnswer)){
                    numCorrect++;
                    md_collect.start();
                    btn_4.setBackgroundResource(R.drawable.btn_green);
                }else{
                    md_wrong.start();
                    btn_4.setBackgroundResource(R.drawable.btn_red);
                }
                DisableBtnOption();
                btn_next.setEnabled(true);
                break;
        }
    }
    private void DisableBtnOption(){
        btn_1.setEnabled(false);
        btn_2.setEnabled(false);
        btn_3.setEnabled(false);
        btn_4.setEnabled(false);
    }
    private void EnableBtnOption(){
        btn_1.setEnabled(true);
        btn_2.setEnabled(true);
        btn_3.setEnabled(true);
        btn_4.setEnabled(true);
    }
    private void ResetBackGroundBTNOption(){
        btn_1.setBackgroundResource(R.drawable.btn_white);
        btn_2.setBackgroundResource(R.drawable.btn_white);
        btn_3.setBackgroundResource(R.drawable.btn_white);
        btn_4.setBackgroundResource(R.drawable.btn_white);
    }
    private boolean CheckAnswer(String key, String value){
        // get number of Quetion 1/10 => 1
        key = key.replace("/10","");
        int num = Integer.parseInt(key);
        if(question_list.get(num-1).getCorrectAnswer().equals(value)){
            return true;
        }else {
            return false;
        }
    }
}