package com.example.adrantiev1.chatter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class ChatterSendActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {


    SharedPreferences prefs;
    View mainview;
    private static final String TAG = "ChatterSendActivity";

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

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        mainview = findViewById(R.id.layout_send_activity);
        String bgColor = prefs.getString("main_bg_color", "#1c2c3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));



        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP )
        {
            ActionBar bar  = this.getSupportActionBar();
            bar.setBackgroundDrawable((new ColorDrawable(Color.parseColor(bgColor))));

            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeAsUpIndicator(R.mipmap.ic_rip_round);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#009999"));
        }
        Log.d(TAG, "in on create()");


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
        String username = prefs.getString("login_main","unknown");
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("DATA", chatter));
            postParameters.add(new BasicNameValuePair("LOGIN_NAME",username));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);

        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error: " + e,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String bgColor = prefs.getString("main_bg_color", "#1c2c3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP )
        {
            ActionBar bar  = this.getSupportActionBar();
            bar.setBackgroundDrawable((new ColorDrawable(Color.parseColor(bgColor))));

            bar.setDisplayHomeAsUpEnabled(true);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#009999"));
        }
    }
}
