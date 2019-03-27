package com.drantiev.anton.canadiantest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.results_view, menu);
        return true;
    }
}
