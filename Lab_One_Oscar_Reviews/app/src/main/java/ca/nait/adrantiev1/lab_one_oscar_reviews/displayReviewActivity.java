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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

import static android.R.attr.category;

public class displayReviewActivity extends AppCompatActivity {
    RadioGroup rg ;



    ArrayList<HashMap<String,String>> reviews = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_review);

        displayReviews();


    }

    private void displayReviews()
    {

        Bundle extras = getIntent().getExtras();

        String category = extras.getString("key");




        String[] keys = new String[]{"DATE","REVIEWER","CATEGORY","NOMINEE","REVIEW"};
        int[] ids = new int[]{R.id.custom_row_date,R.id.custom_row_user,R.id.custom_row_category,R.id.custom_row_nominee,R.id.custom_row_review};
        SimpleAdapter adapter = new SimpleAdapter(this,reviews,R.layout.custom_row_layout,keys,ids);
        ListView listView= (ListView)findViewById(R.id.listview_receive_review);
        populateList(category);
        listView.setAdapter(adapter);



    }

    private void populateList(String category)
    {

        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Lab01Servlet?CATEGORY="+ category));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String field = "";
            while((field = in.readLine())!= null)
            {
//                Log.d(TAG, "reading from server - vslue =" + Value );
                HashMap<String,String> temp = new HashMap<String, String>();
                temp.put("DATE",field);
                field = in.readLine();
                temp.put("REVIEWER",field);
                field = in.readLine();
                temp.put("CATEGORY",field);
                field = in.readLine();
                temp.put("NOMINEE",field);
                field = in.readLine();
                temp.put("REVIEW",field);
                reviews.add(temp);

            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error: "+ e, Toast.LENGTH_LONG).show();
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
            /*case R.id.menu_item_view_reviews:
            {
                Intent intent = new Intent(this,displayReviewActivity.class);
                this.startActivity(intent);
                break;
            }*/
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
