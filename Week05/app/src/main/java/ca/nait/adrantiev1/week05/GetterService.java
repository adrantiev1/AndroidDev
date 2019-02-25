package ca.nait.adrantiev1.week05;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrantiev1 on 2/5/2019.
 */

public class GetterService extends Service
{
    public static boolean bRun = false;
    //final = const
    static final String TAG = "GetterService";
    public static final  String NEW_MESSAGE =   "New message";
    static final int DELAY = 10000;
    MyThread theThread = null;


    DBManager dbManager;
    SQLiteDatabase database;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private class MyThread extends Thread
    {
        public MyThread()
        {
                super("Week05Thread");
        }
        @Override
        public void run()
        {
            GetterService parrent = GetterService.this;
            while(bRun ==true)
            {
                try
                {
                    Log.d(TAG,"while loop cycle once");
                    if (getChatter() == true)
                    {
                        Intent broadcast = new Intent(NEW_MESSAGE);
                        sendBroadcast(broadcast);
                        Log.d(TAG, "intent sent");
                    }
                    getChatter();
                    Thread.sleep(DELAY);
                }
                catch (InterruptedException ioe)
                {
                    bRun = false;
                }
            }
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        dbManager = new DBManager(this);
        theThread = new MyThread();
        Log.d(TAG,"thread instantiated in onCreate()");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        bRun = false;
        theThread.interrupt();
        theThread = null;
        Log.d(TAG,"stopped the thread in onDestroy()");
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId)
    {
        bRun = true;
        theThread.start();
        Log.d(TAG,"thread started in onStartCommand()");

        return START_STICKY;
    }

    private  boolean  getChatter()
    {
        boolean bNew = false;

        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Week05Servlet"));
            HttpResponse response = client.execute(request);
            in  = new BufferedReader((new InputStreamReader(response.getEntity().getContent())));
            String line = "";

            database = dbManager.getReadableDatabase();
            ContentValues values = new ContentValues();

            while ((line = in.readLine())!= null)
            {
                values.clear();

                values.put(DBManager.C_ID,Integer.parseInt(line));

                line = in.readLine();
                values.put(DBManager.C_SENDER,line);

                line = in.readLine();
                values.put(DBManager.C_MESSAGE,line);

                line = in.readLine();
                values.put(DBManager.C_DATE,line);
                try
                {
                    database.insertOrThrow(DBManager.TABLE_NAME,null,values);
                    Log.d(TAG,"record inserted");
                    bNew = true;
                }
                catch (SQLException sqle)
                {
                    //ignore this exception
                    Log.d(TAG, "duplicate record");
                }
            }


            //close db after loop
            database.close();
        }
        catch (Exception e)
        {
            Log.d(TAG,"read failed => " + e);
        }
        return bNew;
    }
}
