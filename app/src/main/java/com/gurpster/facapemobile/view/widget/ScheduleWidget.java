package com.gurpster.facapemobile.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.data.source.GradeDataSource;
import com.gurpster.facapemobile.data.source.GradeRepository;
import com.gurpster.facapemobile.data.source.ScheduleDataSource;
import com.gurpster.facapemobile.data.source.ScheduleRepository;
import com.gurpster.facapemobile.view.*;
import com.gurpster.facapemobile.view.activities.DrawerActivity;
import com.gurpster.facapemobile.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Williaan Lopes (d3x773r) on 10/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            Intent svcIntent = new Intent(ctxt, com.gurpster.facapemobile.view.ScheduleWidgetService.class);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(ctxt.getPackageName(), R.layout.widget_time_table);
            widget.setRemoteAdapter(appWidgetId, R.id.widget_list, svcIntent);

            Intent startActivityIntent = new Intent(ctxt, DrawerActivity.class);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(ctxt, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.widget_list, startActivityPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }
        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
    }
}
