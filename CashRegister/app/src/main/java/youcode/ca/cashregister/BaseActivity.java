package youcode.ca.cashregister;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by GSchenk on 11/19/2018.
 */

public class BaseActivity extends AppCompatActivity
{
    static DBManager dbManager;
    static SQLiteDatabase database;

    static int currentListIndex = 0;
    static int currentItemIndex = 0;

    static ArrayList<Receipt> aReceipts;
    static ArrayList<Detail> aDetails;

    static SharedPreferences prefs;
}