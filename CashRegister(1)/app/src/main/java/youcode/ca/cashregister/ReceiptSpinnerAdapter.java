package youcode.ca.cashregister;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GSchenk on 11/19/2018.
 */

public class ReceiptSpinnerAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList aReceipts;

    @Override
    public View getDropDownView(int index, View row,ViewGroup parent)
    {
        MainActivity activity = (MainActivity)context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow = inflater.inflate(R.layout.spinner_receipt_row, null);

        TextView tvName = (TextView)spinnerRow.findViewById(R.id.textview_receipt_customer_name);


        Receipt receipt = getItem(index);

        tvName.setText(receipt.getCustomerName());


        return spinnerRow;
    }

    public ReceiptSpinnerAdapter(Context context, int rowLayoutId, ArrayList objects)
    {
        super(context, rowLayoutId, objects);
        this.context = context;
        this.aReceipts = objects;
    }
    public int getCount()
    {
        return aReceipts.size();
    }
    public Receipt getItem(int index)
    {
        return (Receipt) aReceipts.get(index);
    }

    // gets called when spinner is displayed closed
    @Override
    public View getView(int index, View row, ViewGroup parent)
    {
        TextView label = new TextView(context);

        label.setTextColor(Color.parseColor("#127855"));
        label.setTextSize(20);

        Receipt receipt = getItem(index);
        label.setText(receipt.getCustomerName());

        return label;
    }
}
