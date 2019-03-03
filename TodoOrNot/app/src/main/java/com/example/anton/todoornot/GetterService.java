
package com.example.anton.todoornot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by anton on 2/25/2019.
 */


public class GetterService extends Service {


    static final String TAG = "GetterService";
    public static final  String NEW_MESSAGE =   "New message";
    static final int DELAY = 10000;
    MyThread theThread = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyThread extends Thread
    {

    }
}

