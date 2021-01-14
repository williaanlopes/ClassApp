package com.gurpster.facapemobile.di.module;

import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.di.FragmentScoped;
import com.gurpster.facapemobile.view.fragments.GradeContract;
import com.gurpster.facapemobile.view.fragments.GradeFragment;
import com.gurpster.facapemobile.view.fragments.GradePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Williaan Lopes (d3x773r) on 09/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public abstract class GradeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract GradeFragment gradeFragment();

    @ActivityScoped
    @Binds
    abstract GradeContract.Presenter gradePresenter(GradePresenter presenter);
}
