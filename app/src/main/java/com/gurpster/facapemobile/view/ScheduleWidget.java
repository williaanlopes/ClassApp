package com.gurpster.facapemobile.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.gurpster.facapemobile.R;

/**
 * Created by Williaan Lopes (d3x773r) on 10/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleWidget extends AppWidgetProvider {

    public static String EXTRA_WORD = "com.commonsware.android.appwidget.lorem.WORD";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i=0; i < appWidgetIds.length; i++) {

            Intent svcIntent = new Intent(ctxt, ScheduleWidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(ctxt.getPackageName(), R.layout.widget_time_table);

            widget.setRemoteAdapter(appWidgetIds[i], R.id.widget_list, svcIntent);

//            Intent clickIntent=new Intent(ctxt, SchedulemActivity.class);

//            PendingIntent clickPI=PendingIntent
//                    .getActivity(ctxt, 0,
//                            clickIntent,
//                            PendingIntent.FLAG_UPDATE_CURRENT);

//            widget.setPendingIntentTemplate(R.id.words, clickPI);

            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }

        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
    }

}
