package com.example.anton.todoornot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by anton on 2/25/2019.
 */

public class DBManager extends SQLiteOpenHelper
{

    static final String TAG = "DBManager";

    static final String DB_NAME = "TodoOrNot.db";
    static final int DB_VERSION = 1;

//Table one for titles
    static final String TABLE = "Title";
    static final String C_ID = "ID";
    static final String C1_TITLE = "title";


//Table two for title details
    static final String TABLE_DETAILS = "TitleDetail";
    static final String C1_DESC = "description";
    static final String C2_DATE = "date";
    static final String C3_FLAG = "titleId";
    static final String C4_COMPLETE = "completed";

    public DBManager(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " + TABLE + " (" + C_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C1_TITLE +  " text)";


//        String sql = "create table " + TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + C1_TITLE + " text, " + C2_DESC + " text, " + C3_DATE + " text, " + C4_FLAG + " text)";
        Log.d(TAG,sql);

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE); // drops the old database
        Log.d(TAG, "onUpdated");
        onCreate(db);
    }
    public boolean addData(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C1_TITLE,title);



        Log.d(TAG,"addData: Adding " + title +  "to" + TABLE);

        long result = db.insert(TABLE,null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    //return all data
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
