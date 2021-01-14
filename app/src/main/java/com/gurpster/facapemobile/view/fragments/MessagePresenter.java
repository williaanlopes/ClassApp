package com.gurpster.facapemobile.view.fragments;

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
public class MessagePresenter implements MessageContract.Presenter {

    private final ApiService apiService;

    @Nullable
    private MessageContract.View view;

    @Inject
    MessagePresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    public void loadMessages() {
        Call<List<String>> call = apiService.getMessages();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> messages = response.body();
                    view.showMessages(messages);
                } else {
                    view.showErrorView();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                view.showErrorView();
            }
        });
    }

    @Override
    public void takeView(MessageContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
