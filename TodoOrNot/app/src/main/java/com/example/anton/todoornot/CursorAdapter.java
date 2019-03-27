package com.example.anton.todoornot;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by anton on 3/21/2019.
 */

public class CursorAdapter extends SimpleCursorAdapter {

    static final String TAG = "CursorAdapter";

    static final String[] FROM = {DBManager.C2_CONTENT, DBManager.C3_DATE,DBManager.C4_COMPLETED_FLAG};
    static final int[] TO = {R.id.tv_content, R.id.tv_date_created,R.id.tv_completed_flag};


    public CursorAdapter(Context context, Cursor c) {
        super(context, R.layout.listview_row, c, FROM, TO);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //change color for completed tasks to gary
        String label = cursor.getString(cursor.getColumnIndex(DBManager.C4_COMPLETED_FLAG));
        if (label.equals("1")){
            int color = Color.parseColor("#D3D3D3");
            view.setBackgroundColor(color);
        }else{
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }


        super.bindView(view, context, cursor);
    }
}
