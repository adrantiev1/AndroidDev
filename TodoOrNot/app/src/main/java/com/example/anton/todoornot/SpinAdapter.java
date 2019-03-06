package com.example.anton.todoornot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by adrantiev1 on 3/6/2019.
 */

public class SpinAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList toDoTiltleList;
    public SpinAdapter(Context context, int rowResourceId, ArrayList objects)
    {
        super(context,rowResourceId,objects);
        this.context = context;
        this.toDoTiltleList = objects;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
