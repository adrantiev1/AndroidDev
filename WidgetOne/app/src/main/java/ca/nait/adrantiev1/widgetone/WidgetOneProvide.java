package ca.nait.adrantiev1.widgetone;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Created by adrantiev1 on 2/26/2019.
 */

public class WidgetOneProvide extends AppWidgetProvider
{
    RemoteViews views;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        ComponentName component = new ComponentName(context, WidgetOneProvide.class);
        views = new RemoteViews(context.getPackageName(), R.layout.main_layout);
        appWidgetManager.updateAppWidget(component,views);

    }
}
