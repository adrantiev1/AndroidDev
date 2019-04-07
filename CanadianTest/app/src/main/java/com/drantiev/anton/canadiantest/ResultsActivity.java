package com.drantiev.anton.canadiantest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    //declare all needed vars
    RatingBar ratingBar;
    TextView txtResult;
    View results_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        results_view = (View)findViewById(R.id.results_view);
        ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.5f);

        txtResult = (TextView)findViewById(R.id.tv_result);

        //get score
        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");
        //display score
        ratingBar.setRating(score);
        switch (score)
        {
            case 0: txtResult.setText("You scored 0%, keep learning");
                break;
            case 1: txtResult.setText("You have 20%, study better");
                break;
            case 2: txtResult.setText("You have 40%, keep learning");
                break;
            case 3: txtResult.setText("You have 60%, good attempt");
                break;
            case 4: txtResult.setText("You have 80% Hmmmm.. maybe you have been reading a lot of AndroidProgramming quiz");
                break;
            case 5: txtResult.setText(" Whao, you have 100%, Who are you? An Android Jet brain");
                break;
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
