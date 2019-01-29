package com.example.adrantiev1.chatter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class ColorSpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_spinner);
        Spinner spinner = (Spinner)findViewById(R.id.color_spinner);
        //one ways to do
        //CustomColorListner temp = new CustomColorListner();
        //spinner.setOnItemSelectedListener(temp);

        //Second way to do
        spinner.setOnItemSelectedListener(new CustomColorListner());
    }
}
class CustomColorListner implements AdapterView.OnItemSelectedListener
{

    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        View linearLayout = (View)spinner.getParent();
        String bgColor = spinner.getResources().getStringArray(R.array.color_values)[position];
        linearLayout.setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        //seems to never get called
        //because onCreate "selects" the first item
    }
}
