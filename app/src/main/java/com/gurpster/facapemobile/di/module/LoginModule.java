package com.gurpster.facapemobile.di.module;

import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.view.activities.LoginContract;
import com.gurpster.facapemobile.view.activities.LoginPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Williaan Lopes (d3x773r) on 24/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
@Module
public abstract class LoginModule {

    @ActivityScoped
    @Binds abstract LoginContract.Presenter loginPresenter(LoginPresenter presenter);
}
