package com.drantiev.anton.canadiantest.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.drantiev.anton.canadiantest.MainActivity;
import com.drantiev.anton.canadiantest.Question;

import java.util.ArrayList;
import java.util.List;

import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_ANSWER;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_ID;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_OPTION_A;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_OPTION_B;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_OPTION_C;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_QUESTION;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.TABLE_QUESTIONS;

/**
 * Created by anton on 3/27/2019.
 */

public class DbHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;
    //DB Name
    static final String DATABASE_NAME = "CANADIAN_QUESTIONS.db";
    SQLiteDatabase database;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS + " ("
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_QUESTION
                + "TEXT, " + C_ANSWER + " TEXT, " + C_OPTION_A + " TEXT, "
                + C_OPTION_B + " TEXT, " + C_OPTION_C + " TEXT)";
        db.execSQL(query);
        addQuestions();
//        db.close();

    }

    private void addQuestions() {
        Question q1 = new Question("How old are you?", "29", "3", "23", "54");
        this.addQuestion(q1);
        Question q2 = new Question("Whats your name?", "Anton", "Morkis", "Larias", "John");
        this.addQuestion(q2);
        Question q3 = new Question("How old are you?", "29", "3", "23", "54");
        this.addQuestion(q3);
        Question q4 = new Question("Whats your name?", "Anton", "Morkis", "Larias", "John");
        this.addQuestion(q4);
        Question q5 = new Question("How old are you?", "29", "3", "23", "54");
        this.addQuestion(q5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public void addQuestion(Question question) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_QUESTION, question.getQuestion());
        values.put(C_ANSWER, question.getAnswer());
        values.put(C_OPTION_A, question.getOptionA());
        values.put(C_OPTION_B, question.getOptionB());
        values.put(C_OPTION_C, question.getOptionC());
        //insert question
        database.insert(TABLE_QUESTIONS, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        //Select all
        String selectQuesry = "SELECT * FROM " + TABLE_QUESTIONS;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuesry, null);
        //loop through all rows
        if (cursor.moveToNext()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswer(cursor.getString(2));
                question.setOptionA(cursor.getString(3));
                question.setOptionB(cursor.getString(4));
                question.setOptionC(cursor.getString(5));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        //return question list
        return questionList;
    }
    public int rowCount(){
        int row = 0;
        //Select all
        String selectQuesry = "SELECT * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuesry,null);
        row = cursor.getCount();
        return row;
    }
}
