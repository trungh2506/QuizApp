package com.example.qnuquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private Spinner spn_danhmuc;
    private ArrayList<String> arl_danhMuc = new ArrayList<String>();
    private ConstraintLayout btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        spn_danhmuc = (Spinner)findViewById(R.id.spn_danhMuc);
        setArrayDanhMuc();
        ArrayAdapter<String> adpDM = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arl_danhMuc);
        spn_danhmuc.setAdapter(adpDM);

        btn_start = (ConstraintLayout) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,MainActivity.class));
            }
        });
    }
    private void setArrayDanhMuc() {
        arl_danhMuc.add("Develop");
        arl_danhMuc.add("Science and Nature");
        arl_danhMuc.add("Science and Nature");
    }
}