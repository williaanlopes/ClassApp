package com.gurpster.facapemobile.data.source.remote;

import android.support.annotation.NonNull;

import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 20/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class RenewTokenInterceptor implements Interceptor {

//    private Session session;
    private final PreferencesHelper preferences;

    @Inject
    public RenewTokenInterceptor(PreferencesHelper preferences) {
        this.preferences = preferences;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        // if 'x-auth-token' is available into the response header
        // save the new token into session.The header key can be
        // different upon implementation of backend.
        String newToken = response.header("x-auth-token");
        if (newToken != null) {
            preferences.setAccessToken(newToken);
        }

        return response;
    }
}
