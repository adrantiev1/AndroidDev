package com.example.anton.todoornot;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

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




//usecase on populate title table
// usecase retribve titles into an array
// populate drop down with titles


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Toast.makeText(this, "You have added a todo title " + messageTitle, Toast.LENGTH_LONG).show();
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

                if (todoContent.length() != 0) {

                    ContentValues values = new ContentValues();
                    values.put(DBManager.C2_CONTENT, todoContent);
                    values.put(DBManager.C1_TITLE_ID, currentItemIndex);
                    try {
                        database = myDbHelper.getWritableDatabase();
                        database.insertOrThrow(DBManager.TABLE_DETAILS, null, values);
                        Toast.makeText(this, "You have added a todo title " + todoContent, Toast.LENGTH_LONG).show();
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


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentItemIndex = position;
        Toast.makeText(this, todoDetails.get(position).getContent(), Toast.LENGTH_LONG).show();
        Log.d(TAG,"onItemClick!"+ position);


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
        String content, output;
        int id, titleId;
        //boolean bcompleted;

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            titleId = cursor.getInt(cursor.getColumnIndex(DBManager.C1_TITLE_ID));
            content = cursor.getString(cursor.getColumnIndex(DBManager.C2_CONTENT));


            TodoDetail item = new TodoDetail(id, titleId, content);
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
}
