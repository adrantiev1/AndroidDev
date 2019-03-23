package youcode.ca.cashregister;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

/**
 * Created by GSchenk on 11/19/2018.
 */

public class CursorAdapter extends SimpleCursorAdapter
{
    static final String TAG = "CursorAdapter";
    static final String[] FROM = {DBManager.C_ID, DBManager.C_RECEIPT_ID, DBManager.C_DETAIL_DESCRIPTION, DBManager.C_DETAIL_PRICE};
    static final int[] TO = {R.id.tv_receipt_id, R.id.tv_detail_id, R.id.tv_detail_description, R.id.tv_detail_price};

    public CursorAdapter(Context context, Cursor cursor)
    {
        super(context, R.layout.listview_row, cursor, FROM, TO );
    }

    @Override
    public void bindView(View row, Context context, Cursor cursor)
    {
        super.bindView(row, context, cursor);
    }
}
