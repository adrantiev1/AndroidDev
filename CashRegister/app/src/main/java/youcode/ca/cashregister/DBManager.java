package youcode.ca.cashregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by GSchenk on 11/19/2018.
 */

public class DBManager extends SQLiteOpenHelper
{
    static final String TAG = "DBManager";
    static final String DB_NAME = "CashRegister.db";
    static final int DB_VERSION = 2;
    static final String TABLE_RECEIPT = "Receipt";

    static final String TABLE_RECEIPT_DETAIL = "ReceiptDetail";


    // table Receipt
    static final String C_ID = BaseColumns._ID;
    static final String C_CUSTOMER_NAME = "customerName";



    // table Receipt Detail
    // reuse C_ID for this table as well
    static final String C_DETAIL_DESCRIPTION = "description";
    static final String C_RECEIPT_ID = "receiptId";
    static final String C_DETAIL_PRICE = "price";

    public DBManager(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String sql = "create table " + TABLE_RECEIPT + " (" + C_ID + " integer primary key autoincrement, "
                + C_CUSTOMER_NAME + " text)";
        Log.d(TAG,sql);
        database.execSQL(sql);

        sql = "create table " + TABLE_RECEIPT_DETAIL + " (" + C_ID + " integer primary key autoincrement, "
                + C_RECEIPT_ID + " integer, " + C_DETAIL_DESCRIPTION + " text, " + C_DETAIL_PRICE + " real)";
        Log.d(TAG,sql);
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + TABLE_RECEIPT);
        database.execSQL("drop table if exists " + TABLE_RECEIPT_DETAIL);
        Log.d(TAG, "onUpdated");
        onCreate(database);
    }
}
