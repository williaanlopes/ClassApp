package com.gurpster.facapemobile.view.activities;

import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.di.FragmentScoped;
import com.gurpster.facapemobile.view.fragments.DebtContract;
import com.gurpster.facapemobile.view.fragments.DebtFragment;
import com.gurpster.facapemobile.view.fragments.DebtPresenter;
import com.gurpster.facapemobile.view.fragments.GradeContract;
import com.gurpster.facapemobile.view.fragments.GradeFragment;
import com.gurpster.facapemobile.view.fragments.GradePresenter;
import com.gurpster.facapemobile.view.fragments.HistoricContract;
import com.gurpster.facapemobile.view.fragments.HistoricFragment;
import com.gurpster.facapemobile.view.fragments.HistoricPresenter;
import com.gurpster.facapemobile.view.fragments.MessageContract;
import com.gurpster.facapemobile.view.fragments.MessageFragment;
import com.gurpster.facapemobile.view.fragments.MessagePresenter;
import com.gurpster.facapemobile.view.fragments.PerformanceContract;
import com.gurpster.facapemobile.view.fragments.PerformanceFragment;
import com.gurpster.facapemobile.view.fragments.PerformancePresenter;
import com.gurpster.facapemobile.view.fragments.PlaceContract;
import com.gurpster.facapemobile.view.fragments.PlacePresenter;
import com.gurpster.facapemobile.view.fragments.PlacesFragment;
import com.gurpster.facapemobile.view.fragments.ScheduleGridContract;
import com.gurpster.facapemobile.view.fragments.ScheduleGridPresenter;
import com.gurpster.facapemobile.view.fragments.ScheduleListFragment;
import com.gurpster.facapemobile.view.fragments.ScheduleListContract;
import com.gurpster.facapemobile.view.fragments.ScheduleListPresenter;
import com.gurpster.facapemobile.view.fragments.TimetableFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Williaan Lopes (d3x773r) on 09/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public abstract class DrawerModule {

    @ActivityScoped
    @Binds
    abstract DrawerContract.Presenter drawerPresenter(DrawerPresenter presenter);

//    @FragmentScoped
//    @ContributesAndroidInjector
//    abstract SplashScreenFragment splashScreenFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract GradeFragment gradeFragment();

    @ActivityScoped
    @Binds
    abstract GradeContract.Presenter gradePresenter(GradePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ScheduleListFragment scheduleListFragment();

    @ActivityScoped
    @Binds
    abstract ScheduleListContract.Presenter scheduleListPresenter(ScheduleListPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MessageFragment messageFragment();

    @ActivityScoped
    @Binds
    abstract MessageContract.Presenter messagePresenter(MessagePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract TimetableFragment timetableFragment();

    @ActivityScoped
    @Binds
    abstract ScheduleGridContract.Presenter scheduleGridPresenter(ScheduleGridPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DebtFragment debtFragment();

    @ActivityScoped
    @Binds
    abstract DebtContract.Presenter debtPresenter(DebtPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PerformanceFragment performanceFragment();

    @ActivityScoped
    @Binds
    abstract PerformanceContract.Presenter performancePresenter(PerformancePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoricFragment historicFragment();

    @ActivityScoped
    @Binds
    abstract HistoricContract.Presenter historicPresenter(HistoricPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PlacesFragment placesFragment();

    @ActivityScoped
    @Binds
    abstract PlaceContract.Presenter placePresenter(PlacePresenter presenter);
}
