package com.example.adrantiev1.chatter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adrantiev1 on 1/30/2019.
 */

public class SpinAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList chatter;
    public SpinAdapter(Context context, int rowResourceId, ArrayList objects)
    {
        super(context,rowResourceId,objects);
        this.context = context;
        this.chatter = objects;
    }
    public int getCount()
    {
     return chatter.size();
    }
    public Chat getItem(int position)
    {
        return (Chat) chatter.get(position);
    }

    // this method is called when the list closes
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLUE);
        label.setText(((Chat)(chatter.get(position))).getChatContent());
        return label;
    }
    //this method is called when each row is inflated
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        ChatSpinnerActivity activity = (ChatSpinnerActivity)context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow  =inflater.inflate(R.layout.spinner_row, null);

        TextView sender = (TextView)spinnerRow.findViewById(R.id.tv_row_sender);
        sender.setText(((Chat)(chatter.get(position))).getChatSender());

        TextView date = (TextView)spinnerRow.findViewById(R.id.tv_row_date);
        date.setText(((Chat)(chatter.get(position))).getChatDate());

        TextView message = (TextView)spinnerRow.findViewById(R.id.tv_row_message);
        message.setText(((Chat)(chatter.get(position))).getChatContent());

        return spinnerRow;
    }
}
