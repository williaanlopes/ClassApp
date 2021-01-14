package com.gurpster.facapemobile.data.source.remote;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.source.GradeDataSource;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 09/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class GradeRemoteDataSource implements GradeDataSource {

    //    @Inject
    ApiService apiService;

    //    @Inject
    PreferencesHelper preferences;
    Application context;
    String token;

    @Inject
    public GradeRemoteDataSource(Application context, ApiService apiService, PreferencesHelper preferences) {
        this.apiService = apiService;
        this.preferences = preferences;
        this.context = context;
    }

    @Override
    public void getGrades(@NonNull final LoadGradesCallback callback) {
//        String token = preferences.getAccessToken();
//        final String gradesETag = preferences.getPrefGradesETag();

//        Call<List<Grades>> call = apiService.getGrades(token);
//        call.enqueue(new Callback<List<Grades>>() {
//
//            List<Grades> grades = new ArrayList<>();
//
//            @Override
//            public void onResponse(Call<List<Grades>> call, Response<List<Grades>> response) {
//                if(response.isSuccessful()){
//                    String eTag = response.headers().get("etag");
//                    grades = response.body();
//                    callback.onGradeLoaded(processGrades(grades, eTag);
//                } else {
//                    String r = response.code() + "";
////                callback.onDataNotAvailable(String errorMessage);
//                    callback.onDataNotAvailable();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Grades>> call, Throwable t) {
////                callback.onDataNotAvailable(String errorMessage);
//                callback.onDataNotAvailable();
//            }
//        });

        Gson gson = new Gson();
        Grades[] grades = gson.fromJson(loadJsonFromAsset(), Grades[].class);
        callback.onGradeLoaded(new ArrayList<>(Arrays.asList(grades)));

    }

    private List<Grades> processGrades(List<Grades> grades, String eTag) {
        final String gradesETag = preferences.getPrefGradesETag();
        if (!gradesETag.equals(eTag)) {
            return grades;
        } else {
            return new ArrayList<>(0);
        }
    }

    @Override
    public void getOldGrades( int year, int semester, @NonNull final LoadGradesCallback callback ) {
        Call<List<Grades>> call = apiService.getOldGrades(token, year, semester);
        call.enqueue(new Callback<List<Grades>>() {
            @Override
            public void onResponse(Call<List<Grades>> call, Response<List<Grades>> response) {
                if (response.isSuccessful()) {
                    List<Grades> grades = response.body();
                    callback.onGradeLoaded(grades);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void getGrade(@NonNull Long gradeId, @NonNull GetGradeCallback callback) {
        // not implemented
    }

    @Override
    public void saveGrade(@NonNull Grades grade) {
        // nothing to do
    }

    @Override
    public void saveGrades(@NonNull Grades grade) {
        // nothing to do
    }

    @Override
    public void refreshGrades() {

    }

    @Override
    public void deleteAllGrades() {
        // nothing to do
    }

    @Override
    public void deleteGrade(@NonNull Long gradeId) {
        // nothing to do
    }

    private String loadJsonFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("grades_dto.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
