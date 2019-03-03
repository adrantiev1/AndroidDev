package com.example.anton.todoornot;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ToDoListActivity extends AppCompatActivity {

    private static final String TAG = "ToDo List Activity";
    DBManager myDbHelper;
    private ListView myListView;

    ArrayList<HashMap<String,String>> todo = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        myListView = (ListView)findViewById(R.id.lv_todo);
        myDbHelper = new DBManager(this);


        String[] keys = new String[]{"sender","date","message"};
        int[] ids = new int[]{R.id.sender,R.id.date,R.id.message};
        SimpleAdapter adapter = new SimpleAdapter(this,todo,R.layout.listview_row,keys,ids);

        populateListView();
        myListView.setAdapter(adapter);

    }

    private void populateListView()
    {
        Log.d(TAG,"populateListView: Displaying data in the Listview");

        Cursor data =myDbHelper.getData();
        ArrayList<String> listData = new ArrayList<>();

        String field = null;
        while (data.moveToNext()){

            HashMap<String,String> temp = new HashMap<String, String>();

            temp.put("sender",data.getString(1));
            temp.put("date",data.getString(2));
            temp.put("message",data.getString(3));
//            listData.add(data.getString(1));
//            listData.add(data.getString(2));
//            listData.add(data.getString(3));
            todo.add(temp);
        }
//        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,listData);


//        myListView.setAdapter(adapter);


    }
    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
