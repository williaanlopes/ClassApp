package com.gurpster.facapemobile.view.fragments;

import android.util.Log;

import com.gurpster.facapemobile.data.entity.Place;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.di.ActivityScoped;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@ActivityScoped
public class PlacePresenter implements PlaceContract.Presenter {

    private static final String TAG = PlacePresenter.class.getSimpleName();

    private final ApiService apiService;
    private final PreferencesHelper preferences;

    @Nullable
    private PlaceContract.View view;

    @Inject
    PlacePresenter(ApiService apiService, PreferencesHelper preferences) {
        this.apiService = apiService;
        this.preferences = preferences;
    }

    @Override
    public void loadData() {
        String token = preferences.getAccessToken();
        Call<List<Place>> call = apiService.getPlaces(token);
        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.isSuccessful()) {
                    List<Place> places = response.body();
                    view.show(places);
                } else {
                    Log.e(TAG, response.errorBody() + "");
                    view.showErrorView();
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                view.showErrorView();
            }
        });
    }

    @Override
    public void takeView(PlaceContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
