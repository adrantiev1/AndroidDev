package com.example.adrantiev1.buttonwidget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.RemoteViews;

/**
 * Created by adrantiev1 on 2/27/2019.
 */

public class ButtonWidgetProvider extends AppWidgetProvider
{
    public static String BUTTON_ACTION_LAUNCH = "Button Action Launch";
    public static String BUTTON_ACTION_NOTIFY = "Button Action Notify";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);

        Intent launchIntent = new Intent(context, ButtonWidgetProvider.class);
        launchIntent.setAction(BUTTON_ACTION_LAUNCH);

        Intent notifyIntent = new Intent(context, ButtonWidgetProvider.class);
        notifyIntent.setAction(BUTTON_ACTION_NOTIFY);
        notifyIntent.putExtra("MSG", "Message posted...");

        PendingIntent launchPendingIntent = PendingIntent.getActivity(context, 0, launchIntent,0);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(context, 0, notifyIntent,0);

        remoteViews.setOnClickPendingIntent(R.id.button_one,launchPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button_two,notifyPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        final String action = intent.getAction();
        if (intent.getAction().equals(BUTTON_ACTION_NOTIFY))
        {
            String message = "null";
            try
            {
                message = intent.getStringExtra("MSG");

            }
            catch (NullPointerException e)
            {
                ///ignoring catch
            }
        }
        PendingIntent notifyIntent = PendingIntent.getActivity(context, 0, intent,0);
        NotificationManager nm = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = new  Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("Ticker Message")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Anton")
                .setContentText("My notification").build();

        nm.notify(1, notification);

        super.onReceive(context, intent);
    }
}
