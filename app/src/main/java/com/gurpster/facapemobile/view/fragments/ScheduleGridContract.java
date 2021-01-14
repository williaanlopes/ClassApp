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

public interface ScheduleGridContract {

    interface View extends BaseView<Presenter> {

        void showSchedules(List<Schedule> schedules);
    }

    interface Presenter extends BasePresenter<View> {

        void loadSchedules();

        void setTimetable(String value);

        void takeView(ScheduleGridContract.View view);

        void dropView();

    }
}
