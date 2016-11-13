package com.example.gd.ex4;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by gd on 16/10/26.
 */
public class WidgetDemo extends AppWidgetProvider {

    private static final String STATICACTION = "com.example.gd.ex4.staticreceiver";
    private static final String DYNAMICACTION = "com.example.gd.ex4.dynamicreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_demo);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            Log.v("action", intent.getAction());

            if (intent.getAction().equals(STATICACTION)) {
                Fruit f = (Fruit) bundle.getSerializable("fruit");
                rv.setImageViewResource(R.id.widget_image, f.getImage());
                rv.setTextViewText(R.id.widget_text, f.getName());
            }
            else if (intent.getAction().equals(DYNAMICACTION)) {
                rv.setImageViewResource(R.id.widget_image, R.mipmap.dynamic);
                rv.setTextViewText(R.id.widget_text, bundle.getString("message"));
            }
            AppWidgetManager am = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = am.getAppWidgetIds(new ComponentName(context, WidgetDemo.class));
            am.updateAppWidget(appWidgetIds, rv);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_demo);
        rv.setOnClickPendingIntent(R.id.widget_image, pi);
        appWidgetManager.updateAppWidget(appWidgetIds, rv);

    }
}
