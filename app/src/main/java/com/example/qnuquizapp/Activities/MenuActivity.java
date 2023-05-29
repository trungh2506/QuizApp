package com.example.qnuquizapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.qnuquizapp.Helpers.CopyDBHelper;
import com.example.qnuquizapp.Models.CategoryModels;
import com.example.qnuquizapp.R;

import java.io.IOException;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private Spinner spn_danhmuc;
    private ArrayList<CategoryModels> arl_danhMuc = new ArrayList<CategoryModels>();
    private ArrayAdapter<CategoryModels> adpDM;
    private ConstraintLayout btn_start;

    //private DBHelper DBHelper;
    private CategoryModels categorySelected;
    private int position;
    private CopyDBHelper copyDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        anhXa();
        try {
            copyDBHelper = new CopyDBHelper(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setArrayDanhMuc();
        adpDM = new ArrayAdapter<CategoryModels>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arl_danhMuc);
        spn_danhmuc.setAdapter(adpDM);
        spn_danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                categorySelected = (CategoryModels) adapterView.getItemAtPosition(i);
                System.out.println("Bạn chọn: "+categorySelected.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendIntent();
            }
        });
    }
    public void anhXa(){
        spn_danhmuc = (Spinner)findViewById(R.id.spn_danhMuc);
        btn_start = (ConstraintLayout) findViewById(R.id.btn_start);
    }
    private void setArrayDanhMuc() {
        arl_danhMuc = copyDBHelper.getAllCategory();
    }
    //Lấy tên danh mục để truyền qua MainActivity bằng chuỗi "categorySelected"
    private void sendIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("categorySelected", categorySelected);
        startActivity(intent);
    }
}