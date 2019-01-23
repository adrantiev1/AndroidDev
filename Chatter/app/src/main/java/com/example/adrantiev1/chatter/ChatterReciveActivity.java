package com.example.adrantiev1.chatter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class ChatterReciveActivity extends AppCompatActivity {


    SharedPreferences prefs;
    View mainview;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_item_view_chatter:
            {
                Intent intent = new Intent(this,ChatterSimpleListActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_add_chat:
            {
                Intent intent = new Intent(this,ChatterSendActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_view_jitters:
            {
                Intent intent = new Intent(this,ChatterReciveActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_Item_view_custom_list:
            {
                Intent intent = new Intent(this,CustomListActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_preferences:
            {
                Intent intent = new Intent(this,PrefsActivity.class);
                this.startActivity(intent);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatter_recive);
        getFromChatter();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mainview = findViewById(R.id.layout_send_activity);
        String bgColor = prefs.getString("main_bg_color", "#1c2c3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));
    }

    private void getFromChatter()
    {
        BufferedReader in = null;
        TextView textView = (TextView) findViewById(R.id.textbox_receive_chatter);
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JSONServlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while((line = in.readLine())!= null)
            {
                textView.append(line + "\n");
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error: " + e,Toast.LENGTH_LONG).show();
        }
    }
}
