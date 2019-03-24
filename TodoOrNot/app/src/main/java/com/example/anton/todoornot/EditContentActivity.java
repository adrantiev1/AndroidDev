package com.example.anton.todoornot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class EditContentActivity extends AppCompatActivity {

    private Button buttonSave,buttonDelete;
    private EditText editableText_content;

    DBManager myDbHelper;
    MainActivity mainActivity;
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
                postReview(selectedContent);
                myDbHelper.deleteContent(selectedId,selectedContent);
                editableText_content.setText("");
                toastMessage("Deleted succesfuly");
            }
        });
    }
    private void postReview(String content) {
        EditText title = (EditText) findViewById(R.id.textbox_list_name);
        String messageTitle = title.getText().toString();

        //String date = Calendar.getInstance().getTime().toString();
//        String userName = prefs.getString("login_name", "Anton");
//        String pass = prefs.getString("login_password", "oscar275");
        toastMessage("Error: " + messageTitle + content + "user" + "pass" + "date");
//        try {
//            HttpClient client = new DefaultHttpClient();
//            HttpPost post = new HttpPost("http://www.youcode.ca/Lab02Post.jsp");
//            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//
//            postParameters.add(new BasicNameValuePair("LIST_TITLE", messageTitle));
//            postParameters.add(new BasicNameValuePair("CONTENT", content));
//            postParameters.add(new BasicNameValuePair("COMPLETED_FLAG", "1"));
//            postParameters.add(new BasicNameValuePair("ALIAS", userName));
//            postParameters.add(new BasicNameValuePair("PASSWORD ", pass));
//            postParameters.add(new BasicNameValuePair("CREATED_DATE ", date));
//
//            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
//            post.setEntity(formEntity);
//            client.execute(post);
//
//        } catch (Exception ex) {
//            toastMessage("Error: " + ex + messageTitle + content + "userName" + "pass" + "date");
//        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
