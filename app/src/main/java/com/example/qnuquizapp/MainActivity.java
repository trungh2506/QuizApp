package com.example.qnuquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qnuquizapp.Helpers.DBHelper;
import com.example.qnuquizapp.Models.QuestionModels;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    MediaPlayer md_collect,md_wrong;
    Button btn_next;
    TextView txt_opA,txt_opB,txt_opC,txt_opD,txt_QT,txt_countQT,txt_countdown;
    ConstraintLayout btn_1,btn_2,btn_3,btn_4;
    ArrayList<QuestionModels> question_list;
    private int CountQuestion = 0;
    private int numCorrect = 0;
    private int stime = 0;
    private int score = 0;
    MyCountDownTimer countDownTimer = new MyCountDownTimer(35000 , 1000);
    //
    private DBHelper DBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create database and fill data
        //createDatabase();
        //Ánh xạ
        findID();
        //get data
        //QuestionSetOne();
        DBHelper = new DBHelper(this);
        addQuestionToDB();
        getQuestionFromDB();


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

//Ánh xạ
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


    private void bindingData(int number){
        //random option ___________________________________________________
        ArrayList<String>option = new ArrayList<>();
        ArrayList<String>option_Random = new ArrayList<>();

        // lấy option vào  ArrayList tên opTion
        option.add(question_list.get(number).getOptionA());
        option.add(question_list.get(number).getOptionB());
        option.add(question_list.get(number).getOptionC());
        option.add(question_list.get(number).getCorrectAnswer());

        //Random opTion vào opTion_ranDom
        Collections.shuffle(option);
        for(String item : option){
            option_Random.add(item);
        }
        //Print random
        txt_QT.setText(question_list.get(number).getQuestionTitle());
        txt_opA.setText(option_Random.get(0));
        txt_opB.setText(option_Random.get(1));
        txt_opC.setText(option_Random.get(2));
        txt_opD.setText(option_Random.get(3));

        //đóng nút Next để khi chưa chọn đáp án nào thì không cho phép sang câu khác
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

    //Thêm dữ liệu vào DB
    public void addQuestionToDB(){
        /*DBHelper.addQuestion(new QuestionModels(0,"1. a","1","3","4","2"));
        DBHelper.addQuestion(new QuestionModels(1,"2. 1+2=?","1","2","4","3"));
        DBHelper.addQuestion(new QuestionModels(2,"3. 1+3=?","1","3","2","4"));
        DBHelper.addQuestion(new QuestionModels(3,"4. 1+4=?","1","3","4","5"));
        DBHelper.addQuestion(new QuestionModels(4,"5. 1+5=?","1","3","4","6"));
        DBHelper.addQuestion(new QuestionModels(5,"6. 1+6=?","1","3","4","7"));
        DBHelper.addQuestion(new QuestionModels(6,"7. 1+7=?","1","3","4","8"));
        DBHelper.addQuestion(new QuestionModels(7,"8. 1+8=?","1","3","4","9"));
        DBHelper.addQuestion(new QuestionModels(8,"9. 2+9=?","1","3","4","11"));
        DBHelper.addQuestion(new QuestionModels(9,"10. 4+9=?","1","3","4","13"));*/
    }
    //Lấy dữ liệu từ DB thêm vào question_list
    public void getQuestionFromDB(){
        question_list = DBHelper.getAllQuestion();
        System.out.println(question_list.size());
    }

}