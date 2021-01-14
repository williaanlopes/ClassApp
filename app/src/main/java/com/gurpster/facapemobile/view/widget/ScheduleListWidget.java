package com.gurpster.facapemobile.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.gurpster.facapemobile.view.activities.DrawerActivity;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.util.CalendarUtils;

/**
 * Created by Williaan Lopes (d3x773r) on 10/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleListWidget extends AppWidgetProvider {

    public static String EXTRA_WORD = "com.commonsware.android.appwidget.lorem.WORD";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            RemoteViews widget = new RemoteViews(ctxt.getPackageName(), R.layout.widget_time_table_list);
            Intent svcIntent = new Intent(ctxt, ScheduleListWidgetService.class);

            widget.setTextViewText(R.id.widget_type_list_title, CalendarUtils.getCurrentDayBr());

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            widget.setRemoteAdapter(R.id.widget_list_type, svcIntent);
            widget.setEmptyView(R.id.widget_list_type, R.id.empty_view);

            Intent startActivityIntent = new Intent(ctxt , DrawerActivity.class);
//            startActivityIntent.putExtra("widget_type", 1);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(ctxt, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.widget_list_type, startActivityPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, widget);

        }

        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
    }
}
