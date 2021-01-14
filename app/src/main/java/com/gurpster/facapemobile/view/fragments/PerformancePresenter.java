package com.gurpster.facapemobile.view.fragments;

import android.os.Handler;

import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Semester;
import com.gurpster.facapemobile.data.source.GradeDataSource;
import com.gurpster.facapemobile.data.source.GradeRepository;
import com.gurpster.facapemobile.di.ActivityScoped;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@ActivityScoped
public class PerformancePresenter implements PerformanceContract.Presenter {

    @Nullable
    private PerformanceContract.View view;

    @Inject
    PerformancePresenter() {

    }

    @Override
    public void takeView(PerformanceContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
