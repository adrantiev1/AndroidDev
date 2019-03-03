package com.example.anton.todoornot;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by anton on 3/3/2019.
 */

public class ListViewAdapter extends SimpleCursorAdapter
{
    static  final String[] columns = {DBManager.C1_TITLE,DBManager.C2_DESC,DBManager.C3_DATE};
    static  final int[] ids = {R.id.sender,R.id.date,R.id.text};
    @Override
    public void bindView(View row, Context context, Cursor cursor)
    {
        super.bindView(row, context, cursor);
        String strDate = cursor.getString(cursor.getColumnIndex(DBManager.C3_DATE));
        String strShortDate = strDate.substring(7,17);
        TextView tvDate = (TextView)row.findViewById(R.id.date);
        tvDate.setText(strShortDate);

    }
    public ListViewAdapter(Context context,Cursor cursor)
    {
        super(context,R.layout.listview_row,cursor,columns,ids);
    }
}
