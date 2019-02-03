package ca.nait.adrantiev1.lab_one_oscar_reviews;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener,SharedPreferences.OnSharedPreferenceChangeListener{


    SharedPreferences prefs;
    View mainview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button sendReview = (Button)findViewById(R.id.button_send_review);
        sendReview.setOnClickListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        mainview = findViewById(R.id.layout_main_activity);
        String bgColor = prefs.getString("main_bg_color", "#ccdd3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public void onClick(View v) {
        /*Intent intent = new Intent(this,displayReviewActivity.class);
        this.startActivity(intent);*/

        EditText editText = (EditText)findViewById(R.id.textbox_review);
        String review = editText.getText().toString();
        postReview(review);
        editText.setText("");
    }

    private void postReview(String review)
    {
        String userName = prefs.getString("login_main","unknown");
           try
           {
               HttpClient client = new DefaultHttpClient();
               HttpPost post = new HttpPost(" http://www.youcode.ca/Lab01Servlet");
               List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
               postParameters.add(new BasicNameValuePair("DATA", review));
               postParameters.add(new BasicNameValuePair("LOGIN_NAME",userName));
               UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
               post.setEntity(formEntity);
               client.execute(post);
           }
           catch (Exception ex)
           {
               Toast.makeText(this,"Error: " + ex, Toast.LENGTH_LONG).show();
           }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_item_prefs:
            {
                Intent intent = new Intent(this,prefsActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_view_reviews:
            {
                Intent intent = new Intent(this,displayReviewActivity.class);
                this.startActivity(intent);
                break;
            }
        }

        return true;
    }


    //creating up options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = this.getMenuInflater();
        inflator.inflate(R.menu.main_menu,menu);
        return true;
    }
}
