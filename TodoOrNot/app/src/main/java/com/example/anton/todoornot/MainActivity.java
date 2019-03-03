package com.example.anton.todoornot;

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
         
        Button sendButton = (Button)findViewById(R.id.button_send_to_list);
        sendButton.setOnClickListener(this);
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
                String message = "Test Message";
                String messageTitle = "Test title";
                String newDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String flag = "Y";
                if (message.length() != 0 || messageTitle.length() != 0)
                {
                    AddData(messageTitle,message,newDate,flag);
//                    title.setText("");
//                    content.setText("");

                }else {
                    toastMessage("You must put something in the fields");
                }

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
