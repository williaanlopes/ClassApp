package com.gurpster.facapemobile.view.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


/**
 * Created by Williaan Lopes (d3x773r) on 10/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new ScheduleViewsFactory(this.getApplicationContext(), intent));
    }
}
