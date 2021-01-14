package com.gurpster.facapemobile.data.source;

import android.support.annotation.NonNull;

import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.model.Grade;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface ScheduleDataSource {

    interface LoadScheduleCallback {

        void onScheduleLoaded(List<Schedule> schedules);

        void onDataNotAvailable();
    }

    interface GetScheduleCallback {

        void onScheduleLoaded(Schedule schedule);

        void onDataNotAvailable();
    }

    void getSchedules(@NonNull LoadScheduleCallback callback);

    void getSchedule(@NonNull Long scheduleId, @NonNull GetScheduleCallback callback);

    void getSchedule(@NonNull String subjectName, @NonNull GetScheduleCallback callback);

    void save(@NonNull Schedule schedule);

    void refresh();

    void deleteAll();
}
