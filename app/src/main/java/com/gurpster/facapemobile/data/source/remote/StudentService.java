package com.gurpster.facapemobile.data.source.remote;

import com.gurpster.facapemobile.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Williaan Lopes (d3x773r) on 17/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface StudentService {

    @GET("/api/me")
    Call<User> getData(@Header("Authorization") String authorization);
}
