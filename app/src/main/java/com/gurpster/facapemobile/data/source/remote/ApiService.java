package com.gurpster.facapemobile.data.source.remote;

import com.gurpster.facapemobile.data.entity.Authorization;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Historic;
import com.gurpster.facapemobile.data.entity.Place;
import com.gurpster.facapemobile.data.entity.Semester;
import com.gurpster.facapemobile.data.entity.Student;
import com.gurpster.facapemobile.model.Grade;
//import com.gurpster.facapemobile.model.Student;
import com.gurpster.facapemobile.model.Subject;

import java.util.List;

import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Williaan Lopes (d3x773r) on 14/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public interface ApiService {

    @POST("/api/signature")
    Call<ResponseBody> signature();

    @FormUrlEncoded
//    @POST("/facapemobile/api/auth")
    @POST("/api/auth")
    Call<Authorization> auth(@Field("authentication") String authentication);
//    Call<ResponseBody> auth(@Field("authentication") String authentication);

    @Headers("Cache-Control: no-cache")
    @FormUrlEncoded
    @POST("/api/updateToken")
    Call<ResponseBody> getToken(@Field("authentication") String authentication);

//    @FormUrlEncoded
//    @POST("/api/auth")
//    Call<User> authenticate(@Field("username") String userName, @Field("passsword") String password);

    @Headers("Cache-Control: no-cache")
    @GET("/api/me")
//    @GET("/facapemobile/api/me")
    Call<Student> getStudent(@Header("Authorization") String authorization);

    @Headers("Cache-Control: no-cache")
    @GET("/api/semesters")
//    @GET("/facapemobile/api/semesters")
    Call<List<Semester>> getSemesters(@Header("Authorization") String authToken);

    @Headers("Cache-Control: no-cache")
    @GET("/api/schedule")
//    @GET("/facapemobile/api/schedule")
    Call<List<Subject>> getSubjects(@Header("Authorization") String authToken);

    @Headers("Cache-Control: no-cache")
    @GET("/api/grades") //@Header("Cache-Control") String cacheControl
//    @GET("/facapemobile/api/grades") //@Header("Cache-Control") String cacheControl
    Call<List<Grades>> getGrades(@Header("Authorization") String authToken);

    @GET("/api/old_grades/{year}/{semester}")
//    @GET("/facapemobile/api/old_grades")
//    @HTTP(method = "GET", path = "/api/old_grades", hasBody = true)
    Call<List<Grades>> getOldGrades(@Header("Authorization") String authToken, @Path("year") int year, @Path("semester") int semester);

    @GET("/api/historic")
//    @GET("/facapemobile/api/historic")
    Call<List<Historic>> getHistoric(@Header("Authorization") String authToken);

    @Headers("Cache-Control: no-cache")
    @GET("/api/debt-url")
//    @GET("/facapemobile/api/debt-url")
    Call<String> getDebtUrl(@Header("Authorization") String authToken);

    @GET("/api/messages")
//    @GET("/facapemobile/api/messages")
    Call<List<String>> getMessages();

    @GET("/api/places")
//    @GET("/facapemobile/api/places")
    Call<List<Place>> getPlaces(@Header("Authorization") String authToken);

    @Headers("Cache-Control: no-cache")
    @GET("/api/app/version")
//    @GET("/facapemobile/api/app/version")
    Call<String> appVersion();

    @GET("/developer/about")
    Call<String> about();

    @Headers("Cache-Control: no-cache")
    @GET("/api/hello")
    Call<ResponseBody> testConnection();

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
