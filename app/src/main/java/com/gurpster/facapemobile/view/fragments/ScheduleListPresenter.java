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

public class ScheduleListPresenter implements ScheduleListContract.Presenter {

    @Nullable
    private ScheduleListContract.View view;

    private final ScheduleRepository repository;
    private final PreferencesHelper preferences;

    @Inject
    ScheduleListPresenter(@NonNull ScheduleRepository repository, PreferencesHelper preferences) {
        this.repository = repository;
        this.preferences = preferences;
    }

    @Override
    public void loadSchedules() {
        final int sort = Integer.parseInt(preferences.getScheduleType());
        repository.getSchedules(new ScheduleDataSource.LoadScheduleCallback() {
            @Override
            public void onScheduleLoaded(List<Schedule> schedules) {
                view.showSchedules(schedules, sort);
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

    private void loadConfigures(){
        int i = Integer.parseInt(preferences.getScheduleType());
        view.configure(i);
    }

    @Override
    public void takeView(ScheduleListContract.View view) {
        this.view = view;
        loadConfigures();
    }

    @Override
    public void dropView() {
        view = null;
    }
}
