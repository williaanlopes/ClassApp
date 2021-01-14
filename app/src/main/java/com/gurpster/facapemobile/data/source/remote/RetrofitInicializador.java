package com.gurpster.facapemobile.data.source.remote;

import com.gurpster.facapemobile.model.Login;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Williaan Lopes (d3x773r) on 14/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class RetrofitInicializador {

    private static final String API_BASE_URL = "http://";

    private Retrofit retrofit;
    private Login login;

    public RetrofitInicializador(Login login) {
        this.login = login;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(applicationInterceptor())
                .build();
    }

    private OkHttpClient applicationInterceptor() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor(login))
                .build();

        return  client;
    }

    public LoginService getLoginService() {
        return retrofit.create(LoginService.class);
    }
}
