package com.example.anton.todoornot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "ToDoList_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        Button sendButton = (Button)findViewById(R.id.button_send_to_list);
        sendButton.setOnClickListener(this);

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
                String message = content.getClass().toString();
                String messageTitle = title.getClass().toString();

            }

        }

    }
}
