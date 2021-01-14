package com.gurpster.facapemobile.util;

import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 16/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class TokenInterceptor implements Interceptor {

    private static final String TAG = "TokenInterceptor";
    private final PreferencesHelper preferences;

    @Inject
    public TokenInterceptor(@NonNull PreferencesHelper preferences) {
        this.preferences = preferences;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String accessToken = preferences.getAccessToken();

        Request.Builder builder = request.newBuilder();
        builder.header("Accept", "application/json"); //if necessary, say to consume JSON

//        String token = preferences.getAccessToken(); //save token of this request for future
//        setAuthHeader(builder, token); //write current token to request
        request = builder.build(); //overwrite old request
        Response response = chain.proceed(request); //perform request, here original request will be executed

        if (response.code() == 401 || response.code() == 403) { //if unauthorized
            synchronized (this) { //perform all 401 in sync blocks, to avoid multiply token updates
//                String currentToken = preferences.getAccessToken(); //get currently stored token
//                if(accessToken != null && accessToken.equals(token)) { //compare current token with token that was stored before, if it was not updated - do update
                    int code = refreshToken() / 100; //refresh token
                    if(code != 2) { //if refresh token failed for some reason
//                        if(code == 4) //only if response is 400, 500 might mean that token was not updated
//                            logout(); //go to login screen
                        return response; //if token refresh failed - show error to user
                    }
//                }
                if(accessToken != null) { //retry requires new auth token,d
                    builder.header("Authorization", accessToken);
                    request = builder.build();
                    return chain.proceed(request); //repeat request with new token
                }
            }
        }

        return response;
    }

    private void setAuthHeader(Request.Builder builder, String token) {
        if (token != null) //Add Auth token to each request if authorized
            builder.header("Authorization", String.format("Bearer %s", token));
    }

    private int refreshToken() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        String userCredentials = preferences.getLogin() + ":" + preferences.getPassword();
        String encoding = Base64.encodeToString(userCredentials.getBytes(), Base64.NO_WRAP);

//        MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = new FormBody.Builder().add("authentication", encoding).build();
//        JsonObject json = new JsonObject();
        //---
//        json.addProperty(BODY_PARAM_KEY_GRANT_TYPE, BODY_PARAM_VALUE_GRANT_TYPE);
//        json.addProperty(BODY_PARAM_KEY_REFRESH_TOKEN, Session.getRefreshAccessToken());
        //---
//        RequestBody body = RequestBody.create(jsonType,json.toString());
        // Builds a request with request body...
        Request request = new Request.Builder()
                .url(Constants.TOKEN_URL_AUTH)
                .post(body)
                .build();

        Response response;
        int code = 0;
        try {
            response = client.newCall(request).execute();       //Sends Refresh token request
            if (response != null) {
                code = response.code();
                Log.d(TAG,"Token Refresh responses code: "+code);
                switch (code){
                    case 200:
//                        preferences.setServerKey(response.header("Server"));
                        preferences.setAccessToken(response.header("Authorization"));
                        break;
                    default:
                        break;
                }
                response.body().close(); //ToDo check this line
            }

        } catch (IOException e) {
            Log.d(TAG,"Error while Sending Refresh Token Request "+e.getMessage());
        }

        return code;
    }

//    private int logout() {
//        //logout your user
//        return 0; // TODO
//    }
}
