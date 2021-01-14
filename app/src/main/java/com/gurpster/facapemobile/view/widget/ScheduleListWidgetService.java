package com.gurpster.facapemobile.view.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.gurpster.facapemobile.data.source.ScheduleRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Williaan Lopes (d3x773r) on 10/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleListWidgetService extends RemoteViewsService {

    @Inject
    ScheduleRepository scheduleRepository;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new ScheduleListViewsFactory(scheduleRepository, this.getApplicationContext(), intent));
    }
}
