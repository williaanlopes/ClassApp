package com.gurpster.facapemobile.view.fragments;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;
import com.gurpster.facapemobile.data.entity.Schedule;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface ScheduleListContract {

    interface View extends BaseView<Presenter> {

        void showSchedules(List<Schedule> schedules, int i);

        void configure(int i);

    }

    interface Presenter extends BasePresenter<View> {

        void loadSchedules();

        void setTimetable(String value);

        void takeView(ScheduleListContract.View view);

        void dropView();

    }
}
