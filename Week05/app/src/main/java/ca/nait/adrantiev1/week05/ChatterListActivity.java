package ca.nait.adrantiev1.week05;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatter_list);
        listView = (ListView)findViewById(R.id.lv_chatter);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();


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

        super.onResume();
    }
}
