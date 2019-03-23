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

public class TitleSpinnerAdapter  extends ArrayAdapter{
    private Context context;
    private ArrayList todos;

    // called when the dropdown opens
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        MainActivity activity = (MainActivity)context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow = inflater.inflate(R.layout.custom_spinner_row, null);



        TextView tvTitle = (TextView)spinnerRow.findViewById(R.id.textview_todo_title);
        Todo todo = getItem(position);
        tvTitle.setText(todo.getTodoTitle());

        return spinnerRow;
    }


    public TitleSpinnerAdapter(Context context, int textViewResourceId, ArrayList objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.todos = objects;
    }

    public int getCount()
    {
        return todos.size();
    }

    public Todo getItem(int pos)
    {
        return (Todo) todos.get(pos);
    }



    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        TextView label = new TextView(context);

        label.setTextColor(Color.BLUE);
        label.setTextSize(20);
         Todo todo = getItem(position);
        label.setText(todo.getTodoTitle());
        return label;
    }

}
