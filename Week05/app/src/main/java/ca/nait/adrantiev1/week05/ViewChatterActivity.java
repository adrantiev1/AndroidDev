package ca.nait.adrantiev1.week05;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ViewChatterActivity extends BaseActivity {

    static  final  String TAG = "ViewChatterActivity";
    DBManager manager;
    SQLiteDatabase database;
    Cursor cursor;
    TextView tvChatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chatter);
        manager = new DBManager(this);
        database = manager.getReadableDatabase();
        tvChatter = (TextView)findViewById(R.id.tv_chat);


    }

    @Override
    protected void onDestroy()
    {
        database.close();
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        cursor = database.query(DBManager.TABLE_NAME,null,null,null,null,null,DBManager.C_ID + " DESC");
        this.startManagingCursor(cursor);
        String sender, message, date, output;
        while (cursor.moveToNext())
        {
            sender = cursor.getString(cursor.getColumnIndex(DBManager.C_SENDER));
            message= cursor.getString(cursor.getColumnIndex(DBManager.C_MESSAGE));
            date = cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));

            output = String.format("%s: from %s  - %s\n",date,sender,message);
            tvChatter.append(output);
        }
        Log.d(TAG, "onResume");
        super.onResume();
    }
}
