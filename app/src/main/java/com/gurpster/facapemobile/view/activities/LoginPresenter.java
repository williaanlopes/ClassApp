package com.gurpster.facapemobile.view.activities;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.gurpster.facapemobile.data.entity.Authorization;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.di.ActivityScoped;
//import com.gurpster.facapemobile.model.Student;

import javax.annotation.Nullable;
import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 24/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@ActivityScoped
public class LoginPresenter implements LoginContract.Presenter {

    private static  final String TAG = "LOGIN";
    private static boolean debug = true;
    private final ApiService apiService;
    private final PreferencesHelper preferencesHelper;

    @Nullable
    LoginContract.View view;

    @Inject
    LoginPresenter(ApiService apiService, PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
        this.apiService = apiService;
    }

    @Override
    public void takeView(LoginContract.View view) {
        this.view = view;
        getSignature();
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void singIn(final String userName, final String password) {

        String userCredentials = userName + ":" + password;
        String encoding = Base64.encodeToString(userCredentials.getBytes(), Base64.NO_WRAP);

        Call<Authorization> call = apiService.auth(encoding);
        call.enqueue(new Callback<Authorization>() {
            @Override
            public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                if(response.isSuccessful()){
//                    String authToken = response.headers().get("Authorization");
                    String signature = response.headers().get("Signature");
                    if(TextUtils.isEmpty(preferencesHelper.getServerKey())) {
                        preferencesHelper.setServerKey(signature);
                    }
                    Authorization authorization = response.body();
                    storeData(userName, password, authorization.getAccessToken());
                    view.successful( authorization.getAccessToken() );
                } else {
                    if(debug) Log.d(TAG, "Error: " + response.code());
//                    view.failure("Error: " + response.code());
                    errorView(response.code());
                }
            }

            @Override
            public void onFailure(Call<Authorization> call, Throwable t) {
                if(debug) Log.d(TAG, "Error! ");
//                view.failure(t.getMessage());
                errorView(0);
            }
        });
    }

    @Override
    public void forgotPassword(String login) {
//        Call<ResponseBody> call = apiService.forgotPassword(login);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) {
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
    }

    private void getSignature() {
        Call<ResponseBody> call = apiService.signature();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    preferencesHelper.setServerKey(response.headers().get("Signature"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void errorView(int error) {
        switch (error) {
            case 401:
                view.failure("Usuario inválido!");
            break;
            case 404:
                view.failure("Login ou senha inválido!");
            break;
            default:
                view.failure("Não foi possivel logar!");
            break;
        }
    }

    private void storeData(String login, String password, String token) {
        preferencesHelper.setAccessToken(token);
        preferencesHelper.setLogin(login);
        preferencesHelper.setPassword(password);
        if(debug) Log.d(TAG, "Storing data: " + login + " : " + password + " : ");
    }
}
