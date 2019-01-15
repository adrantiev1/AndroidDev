package com.example.adrantiev1.chatter;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
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
import java.util.List;

public class ChatterSendActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatter_send);
        if(Build.VERSION.SDK_INT > 9 )
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button sendButton = (Button)findViewById(R.id.button_send_data);
        sendButton.setOnClickListener(this);
        Button reciveButton = (Button)findViewById(R.id.button_view_jitters);
        reciveButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button_view_jitters:
            {
                Intent intent = new Intent(this, ChatterReciveActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.button_send_data:
                EditText editText = (EditText)findViewById(R.id.textbox_data);
                String chatter = editText.getText().toString();
                postToChatter(chatter);
                editText.setText("");
                break;
        }//ends switch

    }// ends on Create

    private void postToChatter(String chatter)

    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("DATA", chatter));
            postParameters.add(new BasicNameValuePair("LOGIN_NAME","Vasiya_Hrenova"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);

        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error: " + e,Toast.LENGTH_LONG).show();
        }

    }
}
