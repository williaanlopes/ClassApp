package com.gurpster.facapemobile.util;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 17/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class CacheInterceptor implements Interceptor {

    @Inject
    public CacheInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Access-Control-Allow-Origin")
                .removeHeader("Vary")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + Constants.MAX_AGE)
                .build();
    }
}
