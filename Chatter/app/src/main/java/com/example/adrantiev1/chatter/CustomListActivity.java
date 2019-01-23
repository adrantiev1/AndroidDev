package com.example.adrantiev1.chatter;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomListActivity extends AppCompatActivity
{
    ArrayList<HashMap<String,String>> chatter = new ArrayList<HashMap<String, String>>();

//    private static final String TAG = "CustomListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        dispalyChatter();
    }

    private void dispalyChatter()
    {
        String[] keys = new String[]{"sender","date","message"};
        int[] ids = new int[]{R.id.custom_row_sender,R.id.custom_row_date,R.id.custom_row_message};
        SimpleAdapter adapter = new SimpleAdapter(this,chatter,R.layout.custom_row_layout,keys,ids);
        ListView listView= (ListView)findViewById(R.id.listview_custom);
        populateList();
        listView.setAdapter(adapter);
        
    }

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



    private  void populateList()
    {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://youcode.ca/JitterServlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String field = "";
            while((field = in.readLine())!= null)
            {
//                Log.d(TAG, "reading from server - vslue =" + Value );
                HashMap<String,String> temp = new HashMap<String, String>();
                temp.put("sender",field);
                field = in.readLine();
                temp.put("message",field);
                field = in.readLine();
                temp.put("date",field);
                chatter.add(temp);

            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error: "+ e, Toast.LENGTH_LONG).show();
        }
    }
}
