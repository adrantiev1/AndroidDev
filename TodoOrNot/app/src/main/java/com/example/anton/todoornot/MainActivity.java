package com.example.anton.todoornot;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "ToDoList_MainActivity";
    DBManager myDbHelper;



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
         
        Button sendButton = (Button)findViewById(R.id.button_send_to_list);
        sendButton.setOnClickListener(this);
        Button viewButton = (Button)findViewById(R.id.button_view_list);
        viewButton.setOnClickListener(this);

        myDbHelper = new DBManager(this);

        Log.d(TAG, "onCreate() called");
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
                String message = title.getText().toString();
                String messageTitle = content.getText().toString();
                String newDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String flag = "Y";
                if (message.length() != 0 || messageTitle.length() != 0)
                {
                    AddData(messageTitle,message,newDate,flag);
                    title.setText("");
                    content.setText("");

                }else {
                    toastMessage("You must put something in the fields");
                }

            }
            case R.id.button_view_list:
            {
                startActivity(new Intent(this,ToDoListActivity.class));
                break;
            }

        }

    }
    public void AddData(String newTitle,String newDesc,String newDate,String newFlag)
    {
        boolean insertData = myDbHelper.addData(newTitle,newDesc,newDate,newFlag);
        if (insertData){
            toastMessage("Data Successfuly Inserted");
        }else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
