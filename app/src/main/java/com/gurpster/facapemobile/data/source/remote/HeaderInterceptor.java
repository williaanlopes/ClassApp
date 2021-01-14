package com.gurpster.facapemobile.data.source.remote;

import android.util.Base64;

import com.gurpster.facapemobile.model.Login;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 14/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class HeaderInterceptor implements Interceptor {

    private static final String HEADER_AUTORIZACAO = "Authorization";
    private static final String TIPO_TOKEN = "MeuTOKEN ";

    private Login login;

    public HeaderInterceptor(Login login) {
        this.login = login;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder constructRequest = request.newBuilder();

        if(login.getLogin() != null && login.getPassword() != null){
            constructRequest.addHeader(HEADER_AUTORIZACAO, "Bearer" + encrypt(login.getLogin(), login.getPassword()));
        } else {
            constructRequest.addHeader(HEADER_AUTORIZACAO, TIPO_TOKEN + login.getToken());
        }

        Request newRequest = constructRequest.build();
        return chain.proceed(newRequest);
    }

    private String encrypt(String username, String password) {

        String userCredentials = username + ":" + password;
        String encoding = Base64.encodeToString(userCredentials.getBytes(), Base64.NO_WRAP);

        return encoding;
    }

    public static String extractTokenAutorization(Headers headers){
        return headers.get(HEADER_AUTORIZACAO).substring(TIPO_TOKEN.length());
    }

}
