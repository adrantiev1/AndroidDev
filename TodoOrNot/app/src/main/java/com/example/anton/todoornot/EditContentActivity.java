package com.example.anton.todoornot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContentActivity extends AppCompatActivity {

    private Button buttonSave,buttonDelete;
    private EditText editableText_content;

    DBManager myDbHelper;

    private String selectedContent;
    private int selectedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);

        //declare everything
        buttonDelete = (Button)findViewById(R.id.button_delete);
        buttonSave = (Button)findViewById(R.id.button_save);
        editableText_content = (EditText)findViewById(R.id.edit_text_content);
        myDbHelper = new DBManager(this);

        //get intent from MainActivity
        Intent receivedIntent = getIntent();

        selectedId = receivedIntent.getIntExtra("id",-1);
        selectedContent = receivedIntent.getStringExtra("content");
        editableText_content.setText(selectedContent);

        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String editedContent = editableText_content.getText().toString();
                if (!editedContent.equals("")){
                    myDbHelper.updateContent(editedContent,selectedId,selectedContent);
                    toastMessage("Saved succesfuly");
                }else {
                    toastMessage("You must enter some content");
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                myDbHelper.deleteContent(selectedId,selectedContent);
                editableText_content.setText("");
                toastMessage("Deleted succesfuly");
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
