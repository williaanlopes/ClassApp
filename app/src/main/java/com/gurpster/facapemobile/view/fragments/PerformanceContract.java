package com.gurpster.facapemobile.view.fragments;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Semester;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface PerformanceContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {

        void takeView(PerformanceContract.View view);

        void dropView();
    }
}
