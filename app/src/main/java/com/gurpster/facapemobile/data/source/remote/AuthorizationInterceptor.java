package com.gurpster.facapemobile.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 20/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class AuthorizationInterceptor implements Interceptor {

    private final ApiService apiService;
    private final PreferencesHelper preferences;
//    private Session session;

    @Inject
    public AuthorizationInterceptor(ApiService apiService, PreferencesHelper preferences) {
        this.apiService = apiService;
        this.preferences = preferences;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        Request request = chain.request();

//        if (!TextUtils.isEmpty(preferences.getLogin())) {
//            // if response code is 401 or 403, 'mainRequest' has encountered authentication error
//            if (response.code() == 401 || response.code() == 403) {
//                String authKey = getAuthorizationHeader(preferences.getLogin(), preferences.getPassword());
//                // request to login API to get fresh token
//                // synchronously calling login API
//                retrofit2.Response<Student> loginResponse = apiService.auth(authKey).execute();
//                if (loginResponse.isSuccessful()) {
//                    // login request succeed, new token generated
////                    Student authorization = loginResponse.body();
//                    // save the new token
//                    preferences.setAccessToken(loginResponse.headers().get("Authorization"));
//                    // retry the 'mainRequest' which encountered an authentication error
//                    // add new token into 'mainRequest' header and request again
//                    Request.Builder builder = request.newBuilder().header("Authorization", preferences.getAccessToken()).
//                            method(request.method(), request.body());
//                    response = chain.proceed(builder.build());
//                }
//            }
//        }

        return response;
    }

    private static String getAuthorizationHeader(String email, String password) {
        String credential = email + ":" + password;
        return Base64.encodeToString(credential.getBytes(), Base64.DEFAULT);
    }
}
