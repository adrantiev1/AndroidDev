package ca.nait.adrantiev1.lab_one_oscar_reviews;

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

import static android.R.attr.category;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,SharedPreferences.OnSharedPreferenceChangeListener{

    RadioGroup rg;
    SharedPreferences prefs;
    View mainview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT > 9 )
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        Button sendReview = (Button)findViewById(R.id.button_send_review);
        sendReview.setOnClickListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        mainview = findViewById(R.id.layout_main_activity);
        String bgColor = prefs.getString("main_bg_color", "#ccdd3c");
        mainview.setBackgroundColor(Color.parseColor(bgColor));
    }


    public String getRbCategory(){
        //retrieving the checked category
        rg = (RadioGroup)findViewById(R.id.rg_category);
        //int category = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
        ;
        String category = null;


        switch (rg.getCheckedRadioButtonId())
        {
            case R.id.radioBestPicture:
            {
                category = "film";
                break;
            }
            case R.id.radioBestActor:
            {
                category = "actor";
                break;
            }
            case R.id.radioBestActress:
            {
                category = "actress";
                break;
            }
            case R.id.radioFilmEditing:
            {
                category = "editing";
                break;
            }
            case R.id.radioVisualEffects:
            {
                category = "effects";
                break;
            }

        }
        return category;
    }
    @Override
    public void onClick(View v) {

        EditText review_text = (EditText)findViewById(R.id.textbox_review);
        String review = review_text.getText().toString();

        EditText nominee_text = (EditText)findViewById(R.id.textbox_nominee);
        String nominee = nominee_text.getText().toString();

        postReview(review,getRbCategory(),nominee);

        review_text.setText("");
        nominee_text.setText("");



    }

    private void postReview(String review, String category,String nominee)
    {
        String url = prefs.getString("url","http://www.youcode.ca/Lab01Servlet");
        String userName = prefs.getString("login_name","Anton");
        String pass = prefs.getString("login_password","oscar275");

           try
           {
               HttpClient client = new DefaultHttpClient();
               HttpPost post = new HttpPost(url);
               List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

               postParameters.add(new BasicNameValuePair("REVIEW", review));
               postParameters.add(new BasicNameValuePair("REVIEWER",userName));
               postParameters.add(new BasicNameValuePair("NOMINEE",nominee));
               postParameters.add(new BasicNameValuePair("CATEGORY",category));
               postParameters.add(new BasicNameValuePair("PASSWORD",pass));

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
                intent.putExtra("key",getRbCategory());
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
