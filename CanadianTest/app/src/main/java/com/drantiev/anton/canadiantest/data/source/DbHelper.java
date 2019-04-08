package com.drantiev.anton.canadiantest.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.drantiev.anton.canadiantest.MainActivity;
import com.drantiev.anton.canadiantest.Question;

import java.util.ArrayList;
import java.util.List;

import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_ANSWER;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_DIFFICULTY_ID;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_DIFFICULTY_TITLE;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_ID;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_OPTION_A;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_OPTION_B;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_OPTION_C;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_OPTION_D;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.C_QUESTION;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.TABLE_DIFFICULTY;
import static com.drantiev.anton.canadiantest.data.source.BaseDataClass.TABLE_QUESTIONS;

/**
 * Created by anton on 3/27/2019.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "Database";
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

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_DIFFICULTY + " ( "
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_DIFFICULTY_TITLE + " TEXT)";
        db.execSQL(query);
        addDifficulties();
        Log.d(TAG, "Created difficulty");
        query = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS + " ( "
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_DIFFICULTY_ID + " integer, " + C_QUESTION
                + " TEXT, " + C_ANSWER + " TEXT, " + C_OPTION_A + " TEXT, "
                + C_OPTION_B + " TEXT, " + C_OPTION_C + " TEXT, " + C_OPTION_D + " TEXT)";
        db.execSQL(query);
        addQuestions();
        Log.d(TAG, "Created questions");
//        db.close();

    }

    private void addQuestions() {
        Question q1 = new Question(1, "What did you have for breakfast today?", "A double-double and a bagel", "Cereal", "A bacon and egg breakfast sandwich", "A double-double and a bagel", "Beans and toast");
        this.addQuestion(q1);
        Question q2 = new Question(1, "What do you say when you want to get someone's attention?", "Sorry —", "Sorry —", "Hey!", "Nothing — I wave my hand until they notice me", "Excuse me —");
        this.addQuestion(q2);
        Question q3 = new Question(1, "What temperature would you consider to be REALLY cold?", "Anything below -30 (not even including wind chill)", "Wait, are we talking Celsius?", "Anything below -10", "Anything below -30 (not even including wind chill)", "Anything below 15 degrees");
        this.addQuestion(q3);
        Question q4 = new Question(1, "Canada is eliminated from the Olympic hockey finals. (HAHA YEAH RIGHT, but let's just pretend for a minute.) Who do you root for instead?", "No one, but I still gonna watch it", "No one, but I still gonna watch it", "Russia", "I don't care who wins, I just love hockey so much", "U.S.A.");
        this.addQuestion(q4);
        Question q5 = new Question(1, "What are your thoughts on bagged milk?", "NOT ALL OF CANADA HAS BAGGED MILK, GEEZ", "NOT ALL OF CANADA HAS BAGGED MILK, GEEZ", "What the hell is bagged milk?", "Do you mean tubes?", "Totally normal");
        this.addQuestion(q5);
        Question q6 = new Question(1, "It's cold outside and you need something to keep your head warm, you reach for a...", "toque", "skull cap", "toque", "scarf", "beret");
        this.addQuestion(q6);
        Question q7 = new Question(1, "Does your country have universal healthcare?", "I once went to the hospital because someone hurt my feelings", "HAHAHAHA no, no we do not, I'm swimming in medical bills", "No, but I never get sick so it's not really an issue", "I once went to the hospital because someone hurt my feelings", "Yes, but other countries have it better");
        this.addQuestion(q7);
        Question q8 = new Question(1, "How often do you go over your data limit on your phone?", "\"Limit\"? Why wouldn't you just get the unlimited data plan?", "Sometimes, but I'm usually pretty good about staying under it", "Never", "\"Limit\"? Why wouldn't you just get the unlimited data plan?", "ALL THE TIME. I'M PAYING A BAZILLION DOLLARS A MONTH FOR CRAP");
        this.addQuestion(q8);
        Question q9 = new Question(1, "What do you call the food with macaroni and cheese sauce?", "Kraft Dinner", "Macaroni & cheese", "Cheesey pasta", "Uh, \"macaroni with cheese sauce\"", "Kraft Dinner");
        this.addQuestion(q9);
        Question q10 = new Question(1, "You are feeling under the weather, you caught a cold. You will take some of this to feel better...", "Buckley's", "Mr. Pibb", "McIntosh toffee", "Buckley's", "Hall's");
        this.addQuestion(q10);


        Question q11 = new Question(2, " Medium What did you have for breakfast today?", "A double-double and a bagel", "Cereal", "A bacon and egg breakfast sandwich", "A double-double and a bagel", "Beans and toast");
        this.addQuestion(q11);
        Question q12 = new Question(2, "What do you say when you want to get someone's attention?", "Sorry —", "Sorry —", "Hey!", "Nothing — I wave my hand until they notice me", "Excuse me —");
        this.addQuestion(q12);
        Question q13 = new Question(2, "What temperature would you consider to be REALLY cold?", "Anything below -30 (not even including wind chill)", "Wait, are we talking Celsius?", "Anything below -10", "Anything below -30 (not even including wind chill)", "Anything below 15 degrees");
        this.addQuestion(q13);
        Question q14 = new Question(2, "Canada is eliminated from the Olympic hockey finals. (HAHA YEAH RIGHT, but let's just pretend for a minute.) Who do you root for instead?", "No one, but I still gonna watch it", "No one, but I still gonna watch it", "Russia", "I don't care who wins, I just love hockey so much", "U.S.A.");
        this.addQuestion(q14);
        Question q15 = new Question(2, "What are your thoughts on bagged milk?", "NOT ALL OF CANADA HAS BAGGED MILK, GEEZ", "NOT ALL OF CANADA HAS BAGGED MILK, GEEZ", "What the hell is bagged milk?", "Do you mean tubes?", "Totally normal");
        this.addQuestion(q15);
        Question q16 = new Question(2, "It's cold outside and you need something to keep your head warm, you reach for a...", "toque", "skull cap", "toque", "scarf", "beret");
        this.addQuestion(q16);
        Question q17 = new Question(2, "Does your country have universal healthcare?", "I once went to the hospital because someone hurt my feelings", "HAHAHAHA no, no we do not, I'm swimming in medical bills", "No, but I never get sick so it's not really an issue", "I once went to the hospital because someone hurt my feelings", "Yes, but other countries have it better");
        this.addQuestion(q17);
        Question q18 = new Question(2, "How often do you go over your data limit on your phone?", "\"Limit\"? Why wouldn't you just get the unlimited data plan?", "Sometimes, but I'm usually pretty good about staying under it", "Never", "\"Limit\"? Why wouldn't you just get the unlimited data plan?", "ALL THE TIME. I'M PAYING A BAZILLION DOLLARS A MONTH FOR CRAP");
        this.addQuestion(q18);
        Question q19 = new Question(2, "What do you call the food with macaroni and cheese sauce?", "Kraft Dinner", "Macaroni & cheese", "Cheesey pasta", "Uh, \"macaroni with cheese sauce\"", "Kraft Dinner");
        this.addQuestion(q19);
        Question q20 = new Question(2, "You are feeling under the weather, you caught a cold. You will take some of this to feel better...", "Buckley's", "Mr. Pibb", "McIntosh toffee", "Buckley's", "Hall's");
        this.addQuestion(q20);

        Question q21 = new Question(3, "HARD What did you have for breakfast today?", "A double-double and a bagel", "Cereal", "A bacon and egg breakfast sandwich", "A double-double and a bagel", "Beans and toast");
        this.addQuestion(q21);
        Question q22 = new Question(3, "What do you say when you want to get someone's attention?", "Sorry —", "Sorry —", "Hey!", "Nothing — I wave my hand until they notice me", "Excuse me —");
        this.addQuestion(q22);
        Question q23 = new Question(3, "What temperature would you consider to be REALLY cold?", "Anything below -30 (not even including wind chill)", "Wait, are we talking Celsius?", "Anything below -10", "Anything below -30 (not even including wind chill)", "Anything below 15 degrees");
        this.addQuestion(q23);
        Question q24 = new Question(3, "Canada is eliminated from the Olympic hockey finals. (HAHA YEAH RIGHT, but let's just pretend for a minute.) Who do you root for instead?", "No one, but I still gonna watch it", "No one, but I still gonna watch it", "Russia", "I don't care who wins, I just love hockey so much", "U.S.A.");
        this.addQuestion(q24);
        Question q25 = new Question(3, "What are your thoughts on bagged milk?", "NOT ALL OF CANADA HAS BAGGED MILK, GEEZ", "NOT ALL OF CANADA HAS BAGGED MILK, GEEZ", "What the hell is bagged milk?", "Do you mean tubes?", "Totally normal");
        this.addQuestion(q25);
        Question q26 = new Question(3, "It's cold outside and you need something to keep your head warm, you reach for a...", "toque", "skull cap", "toque", "scarf", "beret");
        this.addQuestion(q26);
        Question q27 = new Question(3, "Does your country have universal healthcare?", "I once went to the hospital because someone hurt my feelings", "HAHAHAHA no, no we do not, I'm swimming in medical bills", "No, but I never get sick so it's not really an issue", "I once went to the hospital because someone hurt my feelings", "Yes, but other countries have it better");
        this.addQuestion(q27);
        Question q28 = new Question(3, "How often do you go over your data limit on your phone?", "\"Limit\"? Why wouldn't you just get the unlimited data plan?", "Sometimes, but I'm usually pretty good about staying under it", "Never", "\"Limit\"? Why wouldn't you just get the unlimited data plan?", "ALL THE TIME. I'M PAYING A BAZILLION DOLLARS A MONTH FOR CRAP");
        this.addQuestion(q28);
        Question q29 = new Question(3, "What do you call the food with macaroni and cheese sauce?", "Kraft Dinner", "Macaroni & cheese", "Cheesey pasta", "Uh, \"macaroni with cheese sauce\"", "Kraft Dinner");
        this.addQuestion(q29);
        Question q30 = new Question(3, "You are feeling under the weather, you caught a cold. You will take some of this to feel better...", "Buckley's", "Mr. Pibb", "McIntosh toffee", "Buckley's", "Hall's");
        this.addQuestion(q30);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIFFICULTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public void addDifficulties() {
        //database = this.getWritableDatabase();
        String array[] = {"Easy", "Medium", "Hard"};
        for (String x : array) {
            ContentValues values = new ContentValues();
            values.put(C_DIFFICULTY_TITLE, x);
            //insert difficulty
            database.insert(TABLE_QUESTIONS, null, values);
        }
    }

    public void addQuestion(Question question) {
        //database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_DIFFICULTY_ID, question.getDifficultyId());
        values.put(C_QUESTION, question.getQuestion());
        values.put(C_ANSWER, question.getAnswer());
        values.put(C_OPTION_A, question.getOptionA());
        values.put(C_OPTION_B, question.getOptionB());
        values.put(C_OPTION_C, question.getOptionC());
        values.put(C_OPTION_D, question.getOptionD());
        //insert question
        database.insert(TABLE_QUESTIONS, null, values);
    }

    public List<Question> getAllQuestions(int id) {
        List<Question> questionList = new ArrayList<Question>();
        //Select all
        String selectQuesry = "SELECT * FROM " + TABLE_QUESTIONS +
                " WHERE " + C_DIFFICULTY_ID + " = '" + id + "'";
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuesry, null);
        //loop through all rows
        if (cursor.moveToNext()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setDifficultyId(1);
                question.setQuestion(cursor.getString(2));
                question.setAnswer(cursor.getString(3));
                question.setOptionA(cursor.getString(4));
                question.setOptionB(cursor.getString(5));
                question.setOptionC(cursor.getString(6));
                question.setOptionD(cursor.getString(7));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        //return question list
        return questionList;
    }

    public int rowCount() {
        int row = 0;
        //Select all
        String selectQuesry = "SELECT * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuesry, null);
        row = cursor.getCount();
        return row;
    }
}
