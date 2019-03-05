package ca.nait.adrantiev1.widgetone;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.widget.RemoteViews;

/**
 * Created by adrantiev1 on 2/26/2019.
 */

public class WidgetOneProvide extends AppWidgetProvider
{
    RemoteViews views;
    ComponentName component;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {

        context.getApplicationContext().registerReceiver(batteryReciver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        component = new ComponentName(context, WidgetOneProvide.class);
        views = new RemoteViews(context.getPackageName(), R.layout.main_layout);

        appWidgetManager.updateAppWidget(component,views);




        //All of this is to turn on and off wifi
//        ComponentName component = new ComponentName(context, WidgetOneProvide.class);
//        views = new RemoteViews(context.getPackageName(), R.layout.main_layout);
//
//        int[] currentWidgetIds = appWidgetManager.getAppWidgetIds(component);
//        for(int widgetId : currentWidgetIds)
//        {
//            Intent intent = new Intent(context,WidgetOneProvide.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//            views.setOnClickPendingIntent(R.id.text_view_wifi_state,pendingIntent);
//
//            WifiManager wifi = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
//            if (wifi.isWifiEnabled())
//            {
//                wifi.setWifiEnabled(false);
//                views.setTextViewText(R.id.text_view_wifi_state,"off");
//
//            }else {
//                wifi.setWifiEnabled(true);
//                views.setTextViewText(R.id.text_view_wifi_state,"on");
//            }
//            appWidgetManager.updateAppWidget(component,views);
//
//        }



    }
    private BroadcastReceiver batteryReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Integer level = intent.getIntExtra("level",-1);
            //may have to get views
            views.setTextViewText(R.id.text_view_wifi_state,level.toString() + "%");
            AppWidgetManager.getInstance(context).updateAppWidget(component,views);
//            super.onRecive(context,intent);

        }
    };
}
