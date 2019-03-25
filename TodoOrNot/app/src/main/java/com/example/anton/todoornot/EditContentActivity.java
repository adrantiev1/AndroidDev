package com.example.anton.todoornot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditContentActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    static final String TAG = "ToDoList_MainActivity";
    private Button buttonSave, buttonDelete;
    private EditText editableText_content;

    DBManager myDbHelper;
    MainActivity mainActivity;
    private String selectedContent;
    private int selectedId;
    private String selectedTitle;
    private CheckBox checkBoxCompleted;

    SharedPreferences prefs;
    View mainview;
    String pass;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);


        //declare everything
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonSave = (Button) findViewById(R.id.button_save);
        checkBoxCompleted = (CheckBox)findViewById(R.id.checkbox_completed);
        editableText_content = (EditText) findViewById(R.id.edit_text_content);
        myDbHelper = new DBManager(this);

        //get intent from MainActivity
        Intent receivedIntent = getIntent();

        selectedId = receivedIntent.getIntExtra("id", -1);
        selectedContent = receivedIntent.getStringExtra("content");
        selectedTitle = receivedIntent.getStringExtra("title");
        editableText_content.setText(selectedContent);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        userName = prefs.getString("login_name", null);
        pass = prefs.getString("login_password", null);

        mainview = findViewById(R.id.layout_edit_content);
        String bgColor = prefs.getString("main_bg_color", "#ccdd3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editedContent = editableText_content.getText().toString();
                if (!editedContent.equals("")) {
                    myDbHelper.updateContent(editedContent, selectedId, selectedContent);
                    toastMessage("Saved succesfuly");
                } else {
                    toastMessage("You must enter some content");
                }
                //check completed
                if (checkBoxCompleted.isChecked()){
                    myDbHelper.updateStatus("1",selectedId);
                }else{
                    myDbHelper.updateStatus("0",selectedId);
                }

            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postReview(selectedContent, selectedTitle, userName, pass);
                myDbHelper.deleteContent(selectedId, selectedContent);
                editableText_content.setText("");
                toastMessage("Deleted succesfuly");
            }
        });


        checkCheckBox();



    }

    private void checkCheckBox() {
        String flagData="";
        Cursor cursor = myDbHelper.getFlag(selectedId);
        if (cursor.moveToFirst()){
            do{
                flagData = cursor.getString(cursor.getColumnIndex(DBManager.C4_COMPLETED_FLAG));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();

        if (flagData.equals("1")){
            checkBoxCompleted.setChecked(true);
        }else {
            checkBoxCompleted.setChecked(false);
        }
    }

    private void postReview(String content, String title, String userName, String pass) {


        String date = Calendar.getInstance().getTime().toString();
        String flag = "1";
        Log.d(TAG,"PAss " + pass + "Username "+ userName);
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.youcode.ca/Lab02Post.jsp");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

            postParameters.add(new BasicNameValuePair("LIST_TITLE", title));
            postParameters.add(new BasicNameValuePair("CONTENT", content));
            postParameters.add(new BasicNameValuePair("COMPLETED_FLAG", flag));
            postParameters.add(new BasicNameValuePair("ALIAS", userName));
            postParameters.add(new BasicNameValuePair("PASSWORD", pass));
            postParameters.add(new BasicNameValuePair("CREATED_DATE", date));

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);

        } catch (Exception ex) {
            toastMessage("Error: " + ex + title + content + flag + userName + pass + date);
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String bgColor = prefs.getString("main_bg_color", "#1c2c3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ActionBar bar = this.getSupportActionBar();
            bar.setBackgroundDrawable((new ColorDrawable(Color.parseColor(bgColor))));

            bar.setDisplayHomeAsUpEnabled(true);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#009999"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_prefs: {
                Intent intent = new Intent(this, prefsActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_todo_main: {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = this.getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;
    }
}
