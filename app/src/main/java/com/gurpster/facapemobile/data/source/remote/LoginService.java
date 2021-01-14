package com.gurpster.facapemobile.data.source.remote;

//import retrofit2.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
        import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.POST;

/**
 * Created by Williaan Lopes (d3x773r) on 14/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface LoginService {

//    @FormUrlEncoded
//    @POST("http://10.0.0.104:8080/facapemobile/api/auth")
//    Call<Response> authenticate(@Field("username") String userName, @Field("passsword") String password);

//    @POST("/api/auth")
//    Call<Login> auth(@Body LoginA login);

    @FormUrlEncoded
    @POST("/facapemobile/api/auth")
    Call<ResponseBody> auth(@Field("authentication") String authentication);
}
