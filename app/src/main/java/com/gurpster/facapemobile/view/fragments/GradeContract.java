package com.gurpster.facapemobile.view.fragments;

import android.support.annotation.NonNull;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Semester;
import com.gurpster.facapemobile.view.activities.DrawerContract;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface GradeContract {

    interface View extends BaseView<Presenter> {

        void showGrades(List<Grades> grades);

        void setSemesters(List<Semester> semesters);

        void stopRefresh();

        void showLoading();

        void stopLoading();

        void showError(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void loadData();

        void loadGrades(boolean forceUpdate);

//        void loadOldGrades(Semester semester);
        void loadOldGrades(int year, int semester);

        void openTaskDetails(Grades requestedGrade);

        void takeView(GradeContract.View view);

        void dropView();
    }
}
