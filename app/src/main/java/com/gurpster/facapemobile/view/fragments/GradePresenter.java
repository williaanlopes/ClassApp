package com.gurpster.facapemobile.view.fragments;

import android.os.Handler;
import android.util.Log;

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
public class GradePresenter implements GradeContract.Presenter {

    private final GradeRepository repository;

    @Nullable
    private GradeContract.View view;

    @Inject
    GradePresenter(GradeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void loadData() {
        view.setSemesters(new ArrayList<Semester>(0));
    }

    @Override
    public void loadGrades(boolean forceUpdate) {

        if (forceUpdate) {
            repository.refreshGrades();
        } else {
            view.showLoading();
        }

        repository.getGrades(new GradeDataSource.LoadGradesCallback() {
            @Override
            public void onGradeLoaded(List<Grades> grades) {
                view.showGrades(grades);
                view.stopRefresh();
            }

            @Override
            public void onDataNotAvailable() {
                view.stopRefresh();
            }
        });

        view.stopLoading();
    }

    @Override
    public void loadOldGrades(int year, int semester) {

        view.showLoading();

        repository.getOldGrades(year, semester, new GradeDataSource.LoadGradesCallback() {
            @Override
            public void onGradeLoaded(List<Grades> grades) {
                if (view != null) view.showGrades(grades);
//                view.stopRefresh();
            }

            @Override
            public void onDataNotAvailable() {
//                view.stopRefresh();
                view.showError("Não foi possivel executar esta operação! Tente novamente mais tarde.");
            }
        });
        view.stopLoading();

    }

    @Override
    public void openTaskDetails(Grades requestedGrade) {
//        repository.ge
    }

    @Override
    public void takeView(GradeContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
