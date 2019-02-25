package ca.nait.adrantiev1.week05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ChatterListActivity extends BaseActivity
{
    ListViewAdapter adapter;
    ListView listView;
    Cursor cursor;
    SQLiteDatabase database;
    DBManager dbManager;
    NewMessageReciver reciver;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatter_list);
        listView = (ListView)findViewById(R.id.lv_chatter);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();
        reciver = new NewMessageReciver();
        filter = new IntentFilter(GetterService.NEW_MESSAGE);


    }

    @Override
    protected void onResume() {
        cursor = database.query(DBManager.TABLE_NAME,
                                null,
                                null,
                                null,
                                null,
                                null,
                                DBManager.C_ID + " DESC");
        startManagingCursor(cursor);
        adapter = new ListViewAdapter(this,cursor);
        listView.setAdapter(adapter);

        registerReceiver(reciver,filter);
        super.onResume();
    }
    class NewMessageReciver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            cursor.requery();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(reciver);
    }
}
