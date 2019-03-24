package com.example.anton.todoornot;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener ,SharedPreferences.OnSharedPreferenceChangeListener{

    static final String TAG = "ToDoList_MainActivity";


    Spinner mySpinner;
    TitleSpinnerAdapter adapter;
    ListView listView;
    Cursor cursor;

    static ArrayList<Todo> todos;
    static ArrayList<TodoDetail> todoDetails;
    static SQLiteDatabase database;
    static DBManager myDbHelper;
    static int currentItemIndex = 0;
    static int currentListIndex = 0;


    SharedPreferences prefs;
    View mainview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > 9 )
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        mainview = findViewById(R.id.layout_main_activity);
        String bgColor = prefs.getString("main_bg_color", "#ccdd3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));


        //for spinner
        mySpinner = (Spinner) findViewById(R.id.todo_title_spinner);
        mySpinner.setOnItemSelectedListener(this);
        //spinner done

        Button sendButton = (Button) findViewById(R.id.button_send_to_list);
        sendButton.setOnClickListener(this);
        Button viewButton = (Button) findViewById(R.id.button_save_content);
        viewButton.setOnClickListener(this);


        listView = (ListView) findViewById(R.id.listview_todo_details);
        listView.setOnItemClickListener(this);
        listView.setBackgroundResource(R.drawable.rounded_corners);

        myDbHelper = new DBManager(this);
        todos = new ArrayList<Todo>();
        refreshSpinner();
        refreshListView();

        Log.d(TAG, "onCreate() called");
    }

    private void refreshSpinner() {
        populateTitlesArray();
        populateDetialsArray();
        adapter = new TitleSpinnerAdapter(this, R.layout.custom_spinner_row, todos);
        mySpinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_to_list: {
                EditText title = (EditText) findViewById(R.id.textbox_list_name);

                String messageTitle = title.getText().toString();

                if (messageTitle.length() != 0) {

                    ContentValues values = new ContentValues();
                    values.put(DBManager.C1_TITLE, messageTitle);
                    try {
                        database = myDbHelper.getWritableDatabase();
                        database.insertOrThrow(DBManager.TABLE_TITLES, null, values);
                        toastMessage("You have added a todo title ");
                        database.close();

                        refreshSpinner();

                    } catch (Exception e) {
                        Log.d(TAG, "Error" + e);
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
                    }


                } else {
                    toastMessage("You must put something in the fields");
                }
                break;
            }
            case R.id.button_save_content: {

                EditText content = (EditText) findViewById(R.id.textbox_content);
                String todoContent = content.getText().toString();
                String date = Calendar.getInstance().getTime().toString();
                String completeFlag = "0";

                if (todoContent.length() != 0) {

                    ContentValues values = new ContentValues();

                    values.put(DBManager.C1_TITLE_ID, currentItemIndex);
                    values.put(DBManager.C2_CONTENT, todoContent);
                    values.put(DBManager.C3_DATE,date);
                    values.put(DBManager.C4_COMPLETED_FLAG,completeFlag);
                    try {
                        database = myDbHelper.getWritableDatabase();
                        database.insertOrThrow(DBManager.TABLE_DETAILS, null, values);
                        toastMessage("You have added a todo content ");
                        database.close();

                    } catch (Exception e) {
                        Log.d(TAG, "Error" + e);
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
                    }
                    refreshListView();

                } else {
                    toastMessage("You must put something in the fields");
                }
                break;

            }
        }

    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentItemIndex = position;
        toastMessage(todoDetails.get(position).getContent());
        Log.d(TAG,"onItemClick!"+ position);

        String content =  todoDetails.get(position).getContent();
        Cursor data = myDbHelper.getItemID(content); //get the id og the content
        int contentId = -1;
        while (data.moveToNext()){
            contentId = data.getInt(0);
        }
        if (contentId > -1){
            Log.d(TAG,"onItemClick, content ID = "+ contentId);
            Intent editContentIntent = new Intent(MainActivity.this,EditContentActivity.class);
            editContentIntent.putExtra("id",contentId);
            editContentIntent.putExtra("content",content);
            startActivity(editContentIntent);
        }else {
            toastMessage("No ID was found for this content");

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Todo todo = (Todo) parent.getAdapter().getItem(position);
        currentItemIndex = todos.get(position).getId();
        if (currentItemIndex > 0) {
            EditText title = (EditText) findViewById(R.id.textbox_list_name);
            title.setText(todos.get(position).getTodoTitle());
        }
        currentListIndex = position;
        refreshListView();
    }

    private void refreshListView() {
//        if ( todoDetails.size() > 0)
            cursor = populateDetialsArray();
        CursorAdapter adapter = new CursorAdapter(this, cursor);
        listView.setAdapter(adapter);

    }

    private Cursor populateDetialsArray() {
        todoDetails = new ArrayList<TodoDetail>();
        database = myDbHelper.getReadableDatabase();

        String whereClause = DBManager.C1_TITLE_ID + "=" + currentItemIndex;

        cursor = database.query(DBManager.TABLE_DETAILS, null, whereClause, null, null, null, null);
        startManagingCursor(cursor);
        String content, dateCreated , completeFlag;
        int id, titleId;
        //boolean bcompleted;

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            titleId = cursor.getInt(cursor.getColumnIndex(DBManager.C1_TITLE_ID));
            content = cursor.getString(cursor.getColumnIndex(DBManager.C2_CONTENT));
            dateCreated = cursor.getString(cursor.getColumnIndex(DBManager.C3_DATE));
            completeFlag = cursor.getString(cursor.getColumnIndex(DBManager.C4_COMPLETED_FLAG));



            TodoDetail item = new TodoDetail(id, titleId, content, dateCreated, completeFlag);
            todoDetails.add(item);
        }


        return cursor;

    }

    private Cursor populateTitlesArray() {
        todos = new ArrayList<Todo>();
        database = myDbHelper.getReadableDatabase();


        cursor = database.query(DBManager.TABLE_TITLES, null, null, null, null, null, null);
        startManagingCursor(cursor);
        String title;
        int id;
        //boolean bcompleted;

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            title = cursor.getString(cursor.getColumnIndex(DBManager.C1_TITLE));


            Todo item = new Todo(id, title);
            todos.add(item);
        }


        return cursor;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String bgColor = prefs.getString("main_bg_color", "#1c2c3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP )
        {
            ActionBar bar  = this.getSupportActionBar();
            bar.setBackgroundDrawable((new ColorDrawable(Color.parseColor(bgColor))));

            bar.setDisplayHomeAsUpEnabled(true);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#009999"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_item_prefs:
            {
                Intent intent = new Intent(this,prefsActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_todo_main:
            {
                Intent intent = new Intent(this,MainActivity.class);
                this.startActivity(intent);
                break;
            }
        }

        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = this.getMenuInflater();
        inflator.inflate(R.menu.main_menu,menu);
        return true;
    }
}
