package com.gurpster.facapemobile.view.fragments;

import android.support.annotation.NonNull;

import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.data.source.ScheduleDataSource;
import com.gurpster.facapemobile.data.source.ScheduleRepository;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleGridPresenter implements ScheduleGridContract.Presenter {

    @Nullable
    private ScheduleGridContract.View view;

    private final ScheduleRepository repository;
    private final PreferencesHelper preferences;

    @Inject
    ScheduleGridPresenter(@NonNull ScheduleRepository repository, PreferencesHelper preferences) {
        this.repository = repository;
        this.preferences = preferences;
    }

    @Override
    public void loadSchedules() {
        repository.getSchedules(new ScheduleDataSource.LoadScheduleCallback() {
            @Override
            public void onScheduleLoaded(List<Schedule> schedules) {
                view.showSchedules(schedules);
            }

            @Override
            public void onDataNotAvailable() {
                // TODO
            }
        });
    }

    @Override
    public void setTimetable(String value) {
//        preferences.setDefaultTimeTable(value);
    }

    @Override
    public void takeView(ScheduleGridContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
