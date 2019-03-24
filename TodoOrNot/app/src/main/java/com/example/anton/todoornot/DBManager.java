package com.example.anton.todoornot;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by anton on 2/25/2019.
 */

public class DBManager extends SQLiteOpenHelper {

    static final String TAG = "DBManager";

    static final String DB_NAME = "TodoOrNot.db";
    static final int DB_VERSION = 2;

    //Table one for titles
    static final String TABLE_TITLES = "Title";
    static final String C_ID = BaseColumns._ID;
    static final String C1_TITLE = "title";


    //Table two for title details
    static final String TABLE_DETAILS = "TitleDetail";
    static final String C1_TITLE_ID = "titleId";
    static final String C2_CONTENT = "description";
    static final String C3_DATE = "date";
    static final String C4_COMPLETED_FLAG = "completed";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_TITLES + " (" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C1_TITLE + " text)";
        Log.d(TAG, sql);
        db.execSQL(sql);


        sql = "create table " + TABLE_DETAILS + " (" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C1_TITLE_ID + " integer, " + C2_CONTENT + " text, " + C3_DATE + " text, " + C4_COMPLETED_FLAG + " text)";
        Log.d(TAG, sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TITLES); // drops the old database
        db.execSQL("drop table if exists " + TABLE_DETAILS); // drops the old database
        Log.d(TAG, "onUpdated");
        onCreate(db);
    }


    public Cursor getItemID(String content){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + C_ID + " FROM " + TABLE_DETAILS +
                " WHERE " + C2_CONTENT + " = '" + content + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public void updateContent(String newContent,int id,String oldContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_DETAILS + " SET " + C2_CONTENT +
                " = '" + newContent + "' WHERE " + C_ID + " = '" + id + "'" +
                " AND " + C2_CONTENT + " = '" + oldContent + "'";
        db.execSQL(query);
    }
    public void deleteContent(int id, String content) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_DETAILS + " WHERE " + C_ID + " = '" + id + "'" +
                " AND " + C2_CONTENT + " = '" + content + "'";
        db.execSQL(query);
    }
}
//    public boolean addData(String title){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(C1_TITLE,title);
//
//
//
//        Log.d(TAG,"addData: Adding " + title +  "to" + TABLE_TITLES);
//
//        long result = db.insert(TABLE_TITLES,null,contentValues);
//        if (result == -1){
//            return false;
//        }else {
//            return true;
//        }
//    }
//    //return all data
//    public Cursor getData()
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT * FROM " + TABLE_TITLES;
//        Cursor data = db.rawQuery(query,null);
//        return data;
//    }
//}
