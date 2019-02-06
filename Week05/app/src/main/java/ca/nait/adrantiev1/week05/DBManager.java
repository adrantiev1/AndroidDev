package ca.nait.adrantiev1.week05;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by adrantiev1 on 2/6/2019.
 */

public class DBManager extends SQLiteOpenHelper{


    static final String TAG = "DBManager";
    static final String DB_NAME = "chatter.db";
    static final int DB_VERSION = 1;
    static final String TABLE_NAME = "chatter";
    static final String C_ID = BaseColumns._ID;
    static final String C_DATE = "postDate";
    static final String C_SENDER = "sender";
    static final String C_MESSAGE = "message";

    public DBManager(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String sql = "create table " + TABLE_NAME + " (" + C_ID + " int primary key, "
                                    + C_DATE + " text, "
                                    + C_SENDER + " text, "
                                    + C_MESSAGE + " text)";
        Log.d(TAG, sql);
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1)
    {
        database.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(database);
    }
}
