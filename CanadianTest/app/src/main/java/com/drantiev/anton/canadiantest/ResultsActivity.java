package com.drantiev.anton.canadiantest;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    //declare all needed vars
    RatingBar ratingBar;
    TextView txtResult;
    View results_view;
    RelativeLayout resultLayout;

    final int sdk = android.os.Build.VERSION.SDK_INT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultLayout = (RelativeLayout) findViewById(R.id.results_view) ;

        results_view = (View)findViewById(R.id.results_view);
        ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        ratingBar.setNumStars(10);
        ratingBar.setStepSize(1.0f);

        txtResult = (TextView)findViewById(R.id.tv_result);

        //get score
        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");
        //display score
        int resourceImage;
        ratingBar.setRating(score);
        switch (score)
        {
            case 0:
            {
                txtResult.setText("You scored 0%, keep learning ;)");
                resourceImage = R.drawable.loser;
                showImage(resourceImage);
                break;

            }
            case 1:
            {
                txtResult.setText("You have 10%, study better :p");
                resourceImage = R.drawable.loser;
                showImage(resourceImage);
                break;
            }
            case 2:
            {
                txtResult.setText("You have 20%, study better :p");
                resourceImage = R.drawable.loser;
                showImage(resourceImage);
                break;
            }
            case 3:
            {
                txtResult.setText("You have 30%, keep learning :)");
                resourceImage = R.drawable.home_android_canada;
                showImage(resourceImage);
                break;
            }
            case 4:
            {
                txtResult.setText("You have 40%, keep learning :)");
                resourceImage = R.drawable.home_android_canada;
                showImage(resourceImage);
                break;
            }

            case 5:
            {
                txtResult.setText("You have 50%, good attempt");
                resourceImage = R.drawable.canadian_droid;
                showImage(resourceImage);
                break;
            }
            case 6:
            {
                txtResult.setText("You have 60%, good attempt");
                resourceImage = R.drawable.canadian_droid;
                showImage(resourceImage);
                break;
            }
            case 7:
            {
                txtResult.setText("You have 70%, good attempt");
                resourceImage = R.drawable.canadian_droid;
                showImage(resourceImage);
                break;
            }
            case 8:
            {
                txtResult.setText("You have 80% Hmmmm.. maybe you have been reading a lot of history books");
                resourceImage =  R.drawable.opening_android_canada;
                showImage(resourceImage);
                break;
            }
            case 9:
            {
                txtResult.setText("You have 90% Hmmmm.. maybe you have been reading a lot of history books");
                resourceImage =  R.drawable.opening_android_canada;
                showImage(resourceImage);
                break;
            }
            case 10:
            {
                txtResult.setText(" Whao, you have 100%, Who are you? Great JOB!");
                resourceImage =  R.drawable.opening_android_canada;
                showImage(resourceImage);
                break;
            }

        }

    }

    public void showImage(int resourceImage){
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            resultLayout.setBackgroundDrawable(ContextCompat.getDrawable(this, resourceImage));
        } else {
            resultLayout.setBackground(ContextCompat.getDrawable(this, resourceImage));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.results_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, MainActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
