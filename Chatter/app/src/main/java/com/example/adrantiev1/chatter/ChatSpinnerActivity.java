package com.example.adrantiev1.chatter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class ChatSpinnerActivity extends AppCompatActivity {

    private Spinner spinner;
    private SpinAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_spinner);
        ArrayList chatter = new ArrayList();
        populateList(chatter);

        adapter = new SpinAdapter(this, android.R.layout.simple_spinner_item,chatter);
        spinner = (Spinner)findViewById(R.id.spinner_chatter);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MySpinnerListner(this));

    }

    private void populateList(ArrayList chatter)
    {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JitterServlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = in.readLine())!= null)
            {
                Chat temp = new Chat();
                temp.setChatSender(line);

                line = in.readLine();
                temp.setChatContent(line);

                line = in.readLine();
                temp.setChatDate(line);

                chatter.add(temp);
            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error : " + e,Toast.LENGTH_LONG).show();
        }
    }
}
class MySpinnerListner implements AdapterView.OnItemSelectedListener
{
    static ChatSpinnerActivity activity;
    public MySpinnerListner(Context context) {
        activity = (ChatSpinnerActivity)context;
    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        Chat chat = (Chat)spinner.getAdapter().getItem(position);
        EditText sender = (EditText)activity.findViewById(R.id.et_sender);
        sender.setText(chat.getChatSender());

        EditText date = (EditText)activity.findViewById(R.id.et_date);
        date.setText(chat.getChatDate());

        EditText message = (EditText)activity.findViewById(R.id.et_message);
        message.setText(chat.getChatContent());


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        //never used
    }
}