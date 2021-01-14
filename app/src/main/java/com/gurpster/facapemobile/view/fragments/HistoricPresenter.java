package com.gurpster.facapemobile.view.fragments;

import com.gurpster.facapemobile.data.entity.Historic;
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
public class HistoricPresenter implements HistoricContract.Presenter {

    private final PreferencesHelper preferences;
    private final ApiService apiService;

    @Nullable
    private HistoricContract.View view;

    @Inject
    HistoricPresenter(PreferencesHelper preferences, ApiService apiService) {
        this.preferences = preferences;
        this.apiService = apiService;
    }

    private void loadDataFromServer() {

        String token = preferences.getAccessToken();

        Call<List<Historic>> call = apiService.getHistoric(token);
        call.enqueue(new Callback<List<Historic>>() {

            @Override
            public void onResponse(Call<List<Historic>> call, Response<List<Historic>> response) {
                if(response.isSuccessful()) {
                    view.onLoadDataSuccess();
                } else {
                    view.onLoadDataError();
                }
            }

            @Override
            public void onFailure(Call<List<Historic>> call, Throwable t) {
                view.onLoadDataError();
            }
        });
    }

    @Override
    public void takeView(HistoricContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
