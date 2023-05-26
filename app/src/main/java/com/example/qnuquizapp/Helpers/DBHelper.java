package com.example.qnuquizapp.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.qnuquizapp.Models.QuestionModels;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "databases/QuizDB.db";
    public static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME ="Question";
    private static final String ID = "ID";
    private static final String category = "Category";
    private static final String questionTitle ="QuestionTitle";
    private static final String optionA ="OptionA";
    private static final String optionB ="OptionB";
    private static final String optionC ="OptionC";
    private static final String correctAnswer ="CorrectAnswer";
    private Context context;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION); //tạo csdl
        Log.d("DBHelper", "DBHelper: ");
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Question("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Category TEXT,"
                + "QuestionTitle TEXT,"
                + "OptionA TEXT,"
                + "OptionB TEXT,"
                + "OptionC TEXT,"
                + "CorrectAnswer TEXT)");
        Toast.makeText(context, "Create successfully",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully",
                Toast.LENGTH_SHORT).show();
    }
    //Thêm một question vào db
    public void addQuestion(QuestionModels question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(category, question.getCategory());
        values.put(questionTitle, question.getQuestionTitle());
        values.put(optionA, question.getOptionA());
        values.put(optionB, question.getOptionB());
        values.put(optionC, question.getOptionC());
        values.put(correctAnswer, question.getCorrectAnswer());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    // Getting All Question
    public ArrayList<QuestionModels> getAllQuestion() {
        ArrayList<QuestionModels> questionList = new ArrayList<QuestionModels>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(1);
                String questionTitle = cursor.getString(2);
                String optionA = cursor.getString(3);
                String optionB = cursor.getString(4);
                String optionC = cursor.getString(5);
                String correctAnswer = cursor.getString(6);
                QuestionModels question =  new QuestionModels(category, questionTitle, optionA, optionB, optionC, correctAnswer);
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return questionList;
    }
    public ArrayList<String> getDistinctCategories() {
        ArrayList<String> categories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT Category FROM Question" , null);
        while (cursor.moveToNext()) {
            String category = cursor.getString(0);
            categories.add(category);
        }
        cursor.close();
        return categories;
    }
    //Truy vấn không trả kết quả
    public void queryData(String sql){
        SQLiteDatabase dtb = getWritableDatabase();
        dtb.execSQL(sql);
    }
    //Truy vấn trả kết quả
    public Cursor getData(String sql){
        SQLiteDatabase dtb = getReadableDatabase();
        return dtb.rawQuery(sql, null);
    }
}
