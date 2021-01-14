package com.gurpster.facapemobile.view.activities;

import android.support.annotation.NonNull;

import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.di.ActivityScoped;

import javax.annotation.Nullable;
import javax.inject.Inject;

//import com.gurpster.facapemobile.model.Student;

/**
 * Created by Williaan Lopes (d3x773r) on 24/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@ActivityScoped
public class PlacePresenter_ implements PlaceContract_.Presenter {

    private static  final String TAG = "PlacePresenter_";
//    private static boolean debug = true;

    private final PreferencesHelper preferencesHelper;
    private final ApiService apiService;

    @Nullable
    PlaceContract_.View view;


    @Inject
    PlacePresenter_(@NonNull ApiService apiService, @NonNull PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
        this.apiService = apiService;
    }

    @Override
    public void selectView() {
        view.showListViewType();
//        Call<ResponseBody> call = apiService.testConnection();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) view.showMapViewType();
//                else view.showListViewType();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                view.showListViewType();
//            }
//        });
    }

    @Override
    public void takeView(PlaceContract_.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

}
