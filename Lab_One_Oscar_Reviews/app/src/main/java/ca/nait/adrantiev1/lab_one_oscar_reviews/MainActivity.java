package ca.nait.adrantiev1.lab_one_oscar_reviews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    RadioGroup rg;
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
        //retrieving the checked category
        rg = (RadioGroup)findViewById(R.id.rg_category);
        String value = ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                .getText().toString();

        EditText editText = (EditText)findViewById(R.id.textbox_review);
        String review = editText.getText().toString();
        postReview(review,value);
        editText.setText("");
    }

    private void postReview(String review, String value)
    {
        String userName = prefs.getString("login_main","unknown");

           try
           {
               HttpClient client = new DefaultHttpClient();
               HttpPost post = new HttpPost(" http://www.youcode.ca/?CATEGORY=" + value);
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
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
