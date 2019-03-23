package youcode.ca.cashregister;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener

{
    static final String TAG  = "MainActivity";

    ReceiptSpinnerAdapter adapter;
    Spinner spinner;
    ListView listView;

    EditText edittextCustomerName;
    EditText edittextDetailDescription;
    EditText edittextDetailPrice;

    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittextCustomerName = (EditText)findViewById(R.id.edittext_customer_name);
        edittextDetailDescription = (EditText)findViewById(R.id.edittext_detail_description);
        edittextDetailPrice = (EditText)findViewById(R.id.edittext_detail_price);

        Button receiptButton = (Button)findViewById(R.id.button_save_customer_name);
        receiptButton.setOnClickListener(this);
        Button detailButton = (Button)findViewById(R.id.button_save_detail);
        detailButton.setOnClickListener(this);

        spinner = (Spinner)findViewById(R.id.spinner_receipt);
        spinner.setOnItemSelectedListener(this);

        listView = (ListView)findViewById(R.id.listview_receipt_details);
        listView.setOnItemClickListener(this);

        dbManager = new DBManager(this);
        aReceipts = new ArrayList<Receipt>();
        refreshSpinner();
        refreshListView();

    }
    public void populateReceiptArray()
    {
        aReceipts = new ArrayList<Receipt>();

        database = dbManager.getReadableDatabase();
        Cursor cursor = database.query(DBManager.TABLE_RECEIPT,null,null,null,null,null,null);
        startManagingCursor(cursor);
        String customerName, output;
        int id;

        while(cursor.moveToNext())
        {

            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            customerName = cursor.getString(cursor.getColumnIndex(DBManager.C_CUSTOMER_NAME));
            Receipt temp = new Receipt(id,customerName);
            aReceipts.add(temp);
        }
        cursor.close();
    }
    public void refreshSpinner()
    {
        populateReceiptArray();
        adapter = new ReceiptSpinnerAdapter(this, R.layout.spinner_receipt_row, aReceipts);
        spinner.setAdapter(adapter);

    }
    ////////////////////////////////////////////////////////////////////////////
    // for the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int index, long row)
    {
        Receipt receipt = (Receipt) parent.getAdapter().getItem(index);
        currentListIndex = index;
        refreshListView();

    }
    // for the spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
    ////////////////////////////////////////////////////////////////////////////
    // for the listview
    public Cursor populateDetailArray()
    {
        aDetails = new ArrayList<Detail>();
        database = dbManager.getReadableDatabase();
        String whereClause = DBManager.C_RECEIPT_ID + "=" + (aReceipts.get(currentListIndex).getId());

        cursor = database.query(DBManager.TABLE_RECEIPT_DETAIL, null, whereClause, null, null, null, null);
        startManagingCursor(cursor);
        String description,  output;
        int id, receiptId;
        double price;
        boolean bCompleted;
        //edittextListItems.setText("");
        while(cursor.moveToNext())
        {

            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            receiptId = cursor.getInt(cursor.getColumnIndex(DBManager.C_RECEIPT_ID));
            description = cursor.getString(cursor.getColumnIndex(DBManager.C_DETAIL_DESCRIPTION));
            price = cursor.getDouble(cursor.getColumnIndex(DBManager.C_DETAIL_PRICE));

            //output = id + " - " + tempTitleId + " *** " + tempContent + "\n";
            //edittextListItems.append(output);

            Detail item = new Detail(id,receiptId,description,price);
            aDetails.add(item);
        }
        return cursor;
    }
    public void refreshListView()
    {
        if(aReceipts!= null && aReceipts.size() > 0)
            cursor = populateDetailArray();
        CursorAdapter adapter = new CursorAdapter(this, cursor);
        listView.setAdapter(adapter);

    }

    // for the buttons
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_save_customer_name:
            {
                String name = edittextCustomerName.getText().toString();
                if (!name.isEmpty())
                {
                    edittextCustomerName.setText("");

                    ContentValues values = new ContentValues();
                    values.put(DBManager.C_CUSTOMER_NAME, name);

                    try
                    {
                        database = dbManager.getWritableDatabase();
                        database.insertOrThrow(DBManager.TABLE_RECEIPT, null, values);
                        Toast.makeText(this, "You saved: " + name, Toast.LENGTH_LONG).show();
                        database.close();



                        refreshSpinner();

                        //currentListIndex = aReceipts.size()-1;
                        //spinner.setSelection(aReceipts.size()-1);
                    }
                    catch (Exception e)
                    {
                        Log.d(TAG, "Error" + e);
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Enter a new customer name", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.button_save_detail:
            {

                String name = edittextDetailDescription.getText().toString();
                double price = Double.parseDouble(edittextDetailPrice.getText().toString());
                if(!name.isEmpty())
                {
                    edittextDetailPrice.setText("");
                    edittextDetailDescription.setText("");


                    ContentValues values = new ContentValues();
                    values.put(DBManager.C_RECEIPT_ID, aReceipts.get(currentListIndex).getId());
                    values.put(DBManager.C_DETAIL_DESCRIPTION, name);
                    values.put(DBManager.C_DETAIL_PRICE, Double.valueOf(price));
                    try
                    {
                        database = dbManager.getWritableDatabase();
                        database.insertOrThrow(DBManager.TABLE_RECEIPT_DETAIL, null, values);
                        database.close();
                    }
                    catch (Exception e)
                    {
                        Log.d(TAG, "Error" + e);
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Enter TODO details", Toast.LENGTH_LONG).show();
                }
                refreshListView();

                break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long rowid)
    {
        //edittextContent.setText(aItems.get(index).getContent());
        currentItemIndex = index;
        Toast.makeText(this,aDetails.get(index).getDescription(),Toast.LENGTH_LONG).show();
    }
}
