package ca.nait.adrantiev1.week05;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by adrantiev1 on 2/12/2019.
 */

public class ListViewAdapter extends SimpleCursorAdapter
{
    static final String[] columns = {DBManager.C_SENDER,DBManager.C_DATE,DBManager.C_MESSAGE};
    static final int[] ids = {R.id.sender,R.id.date,R.id.text};

    @Override
    public void bindView(View row, Context context, Cursor cursor) {
        super.bindView(row, context, cursor);
        //works without the following code
        //this is a demonstration on how to overide the default
        String strDate = cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));
        String strShortDate = strDate.substring(7,17);
        TextView tvDate = (TextView)row.findViewById(R.id.date);
        tvDate.setText(strShortDate);

    }

    public ListViewAdapter(Context context, Cursor cursor)
    {
        super(context,R.layout.listview_row,cursor,columns,ids);

    }
}

