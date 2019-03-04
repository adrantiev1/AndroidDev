package com.example.adrantiev1.randomwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by adrantiev1 on 3/4/2019.
 */

public class RandomWidgetProvider extends AppWidgetProvider
{
    RemoteViews views;
    public static int currentColor = 1;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        ComponentName thisWidget = new ComponentName(context,RandomWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId: allWidgetIds)
        {
            int number = new Random().nextInt(100);
            switch (currentColor)
            {
                case 1:
                {
                    views = new RemoteViews(context.getPackageName(),R.layout.widget_layout_blue);
                    currentColor = 2;
                    break;
                }
                case 2:
                {
                    views = new RemoteViews(context.getPackageName(),R.layout.widget_layout_green);
                    currentColor = 3;
                    break;
                }
                case 3:
                {
                    views = new RemoteViews(context.getPackageName(),R.layout.widget_layout_red);
                    currentColor = 1;
                    break;
                }
            }//end of switch
            Intent intent = new Intent(context,RandomWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.textview_random_number,pendingIntent);
            views.setTextViewText(R.id.textview_random_number, String.valueOf(number));
            appWidgetManager.updateAppWidget(widgetId,views);



        }
    }
}
