package com.example.anton.todoornot;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adrantiev1 on 3/12/2019.
 */

public class SpinerAdapter  extends ArrayAdapter{
    private Context context;
    private ArrayList listTitles;

    public SpinerAdapter(Context context, int textViewResourceId, ArrayList objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.listTitles = objects;
    }
    public int getCount()
    {
        return listTitles.size();
    }

    public Todo getItem(int pos)
    {
        return (Todo) listTitles.get(pos);
    }



    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        TextView label = new TextView(context);

        label.setTextColor(Color.BLACK);
        label.setText(((Todo) (listTitles.get(position))).getTodoTitle());
        return label;
    }
    // called when the dropdown opens
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        MainActivity activity = (MainActivity)context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow = inflater.inflate(R.layout.custom_spinner_row, null);



        TextView title = (TextView)spinnerRow.findViewById(R.id.textview_todo_title);
        String str = ((Todo) (listTitles.get(position))).getTodoTitle();
        title.setText(str);

        return spinnerRow;
    }
}
