package com.gurpster.facapemobile.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Williaan Lopes (d3x773r) on 22/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

//@Singleton
public class RetrofitServiceFactory {

    private static int connectTimeout = 10;
    private static int writeTimeout = 10;
    private static int readTimeout = 15;

//    public RetrofitServiceFactory(int connectTimeout, int writeTimeout, int readTimeout) {
//        this.connectTimeout = connectTimeout;
//        this.writeTimeout = writeTimeout;
//        this.readTimeout = readTimeout;
//    }

    static final String BASE_URL = "http://10.0.0.104:8080/";

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceWithAuth(Class<S> serviceClass) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("CUSTOM_HEADER_NAME_1", "CUSTOM_HEADER_VALUE_1")
                        .addHeader("CUSTOM_HEADER_NAME_2", "CUSTOM_HEADER_VALUE_2")
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }
}
