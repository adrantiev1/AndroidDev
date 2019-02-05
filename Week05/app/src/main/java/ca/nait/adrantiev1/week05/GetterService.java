package ca.nait.adrantiev1.week05;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by adrantiev1 on 2/5/2019.
 */

public class GetterService extends Service
{
    boolean bRun = false;
    //final = const
    static final String TAG = "GetterService";
    static final int DELAY = 10000;
    MyThread theThread = null;


    @Nullable
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
}
