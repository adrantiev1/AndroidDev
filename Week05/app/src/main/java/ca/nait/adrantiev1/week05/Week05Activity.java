package ca.nait.adrantiev1.week05;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class Week05Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Week05Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week05);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button sendButton = (Button)findViewById(R.id.button_send_data);
        sendButton.setOnClickListener(this);
        Log.d(TAG, "in onCreate()");


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.button_send_data:
            {
                EditText text = (EditText)findViewById(R.id.textbox_data);
                String data = text.getText().toString();
                postToChatter(data);
                //clearing the text
                text.setText("");
                break;
            }
        }
    }

    private void postToChatter(String data)
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("DATA",data));
            postParameters.add(new BasicNameValuePair("LOGIN_NAME","Anton"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error: " + e, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_start_service:
            {
                break;
            }
            case R.id.menu_item_stop_service:
            {
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
