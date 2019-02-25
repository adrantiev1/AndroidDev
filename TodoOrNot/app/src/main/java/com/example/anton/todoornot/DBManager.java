package com.example.anton.todoornot;

import android.content.ContentValues;
import android.content.Context;
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
    static final String TABLE = "Active";
    static final String C_ID = BaseColumns._ID;
    static final String C_TITLE = "title";
    static final String C_DESC = "description";
    static final String C_DATE = "postDate";
    static final String C_FLAG = "flag";

    public DBManager(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " + TABLE + " (" + C_ID + " int primary key, "
                + C_TITLE + " text, " + C_DESC + " text, " + C_DATE + " text, " + C_FLAG + " text)";

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
    public boolean addData(String title,String desc,String date,String flag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_TITLE,title);
        contentValues.put(C_DESC,desc);
        contentValues.put(C_DATE,date);
        contentValues.put(C_FLAG,flag);


        Log.d(TAG,"addData: Adding " + title + " " + desc + "to" + TABLE);

        long result = db.insert(TABLE,null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
}
