package com.gurpster.facapemobile.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by Williaan Lopes (d3x773r) on 17/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class OfflineCacheInterceptor implements Interceptor {

    private final Context context;

    @Inject
    public OfflineCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        NetworkInfo networkInfo = ((ConnectivityManager)
                (context.getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
        if (networkInfo == null) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Access-Control-Allow-Origin")
                    .removeHeader("Vary")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control","public, only-if-cached, max-stale=" + Constants.MAX_STALE)
                    .build();
        }
        return chain.proceed(request);
    }
}
