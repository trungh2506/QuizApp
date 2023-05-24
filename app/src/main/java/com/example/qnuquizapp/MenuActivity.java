package com.example.qnuquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.qnuquizapp.Helpers.DBHelper;
import com.example.qnuquizapp.Models.CategoryModels;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private Spinner spn_danhmuc;
    private ArrayList<CategoryModels> arl_danhMuc = new ArrayList<CategoryModels>();
    private ArrayAdapter<CategoryModels> adpDM;
    private ConstraintLayout btn_start;

    private DBHelper DBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        anhXa();

        setArrayDanhMuc();
        adpDM = new ArrayAdapter<CategoryModels>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arl_danhMuc);
        spn_danhmuc.setAdapter(adpDM);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,MainActivity.class));
            }
        });
    }
    public void anhXa(){
        spn_danhmuc = (Spinner)findViewById(R.id.spn_danhMuc);
        btn_start = (ConstraintLayout) findViewById(R.id.btn_start);
    }
    private void setArrayDanhMuc() {
        arl_danhMuc.add(new CategoryModels("IT"));
        arl_danhMuc.add(new CategoryModels("Âm nhạc"));
        arl_danhMuc.add(new CategoryModels("Toán"));
        arl_danhMuc.add(new CategoryModels("Tiếng Anh"));
    }

}