package com.together.awake.newlight;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    public static String START = "start";
    private static boolean serviceRunning = false;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            Intent newIntent = new Intent(context, NewAppWidget.class);
            newIntent.setAction(START);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, newIntent, 0);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }




    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(START)) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            // Create a fresh intent
            Intent serviceIntent = new Intent(context, MyService.class);

            if(serviceRunning) {
                context.stopService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
            serviceRunning = !serviceRunning;
            ComponentName componentName = new ComponentName(context, NewAppWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        super.onReceive(context, intent);

    }
}


