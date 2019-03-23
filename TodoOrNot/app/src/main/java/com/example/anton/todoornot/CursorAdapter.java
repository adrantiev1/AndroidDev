package com.example.anton.todoornot;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

/**
 * Created by anton on 3/21/2019.
 */

public class CursorAdapter extends SimpleCursorAdapter {

    static final String TAG = "CursorAdapter";
//    static final String[] FROM = {DBManager.C_ID, DBManager.C1_TITLE_ID, DBManager.C2_CONTENT};
//    static final int[] TO = {R.id.tv_title_id, R.id.tv_detail_id, R.id.tv_content};
    static final String[] FROM = {DBManager.C2_CONTENT};
    static final int[] TO = {R.id.tv_content};


    public CursorAdapter(Context context, Cursor c) {
        super(context, R.layout.listview_row, c, FROM, TO);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
    }
}
