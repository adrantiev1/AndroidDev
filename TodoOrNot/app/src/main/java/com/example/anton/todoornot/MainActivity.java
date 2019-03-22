package com.example.anton.todoornot;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener
{

    private static final String TAG = "ToDoList_MainActivity";
    DBManager myDbHelper;

    private Spinner mySpinner;
    private TitleSpinerAdapter adapter;
    ListView listView;

    static ArrayList<Todo> todos;
    static ArrayList<TodoDetail> todoDetails;


    Cursor cursor;
    static SQLiteDatabase database;


    static int currentListIndex = 0;
    static int currentItemIndex = 0;

//usecase on populate title table
// usecase retribve titles into an array
// populate drop down with titles


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //nice to have in case something went wrong
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //for spinner


        mySpinner = (Spinner) findViewById(R.id.todo_title_spinner);
        ///mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(this);

        //spinner done
         
        Button sendButton = (Button)findViewById(R.id.button_send_to_list);
        sendButton.setOnClickListener(this);
        Button viewButton = (Button)findViewById(R.id.button_view_list);
        viewButton.setOnClickListener(this);


        listView = (ListView)findViewById(R.id.listview_todo_details) ;
        listView.setOnItemClickListener(this);

        myDbHelper = new DBManager(this);
        todos = new ArrayList<Todo>();
        //refreshSpinner();
        //refreshListView();

        Log.d(TAG, "onCreate() called");
    }

    private void refreshSpinner() {
        populateTitlesArray();
        adapter = new TitleSpinerAdapter(this,R.layout.custom_spinner_row,todos);
        mySpinner.setAdapter(adapter);

    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_send_to_list:
            {
                EditText title = (EditText)findViewById(R.id.textbox_list_name);
                EditText content = (EditText)findViewById(R.id.textbox_content);
                String messageTitle = title.getText().toString();
//                String messageCOntent = content.getText().toString();
//                String newDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//                String flag = "Y";
                if ( messageTitle.length() != 0)
                {
                    //AddData(messageTitle);
                    title.setText("");
                    content.setText("");

                }else {
                    toastMessage("You must put something in the fields");
                }
                break;

            }


        }

    }


    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentItemIndex = position;
        Toast.makeText(this,todoDetails.get(position).getContent(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Todo todo = (Todo) parent.getAdapter().getItem(position);
        currentListIndex = position;
        refreshListView();
    }

    private void refreshListView() {
        if(todos!= null && todos.size() > 0)
            cursor = populateTitlesArray();
        CursorAdapter adapter = new CursorAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    private Cursor populateTitlesArray() {
        todoDetails = new ArrayList<TodoDetail>();
        database = myDbHelper.getReadableDatabase();
        String whereClause = DBManager.C1_TITLE_ID + "=" + (todos.get(currentListIndex).getId());

        cursor = database.query(DBManager.TABLE_DETAILS,null,whereClause,null,null,null,null);
        startManagingCursor(cursor);
        String content, output;
        int id, titleId;
        //boolean bcompleted;

        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            titleId = cursor.getInt(cursor.getColumnIndex(DBManager.C1_TITLE_ID));
            content = cursor.getString(cursor.getColumnIndex(DBManager.C2_CONTENT));


            TodoDetail item = new TodoDetail(id,titleId,content);
            todoDetails.add(item);
        }


        return cursor;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
