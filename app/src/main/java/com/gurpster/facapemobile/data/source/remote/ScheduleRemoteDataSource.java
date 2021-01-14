package com.gurpster.facapemobile.data.source.remote;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.data.source.ScheduleDataSource;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Williaan Lopes (d3x773r) on 09/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class ScheduleRemoteDataSource implements ScheduleDataSource {

    private ApiService apiService;
    private PreferencesHelper preferences;
    private Application context;

    @Inject
    public ScheduleRemoteDataSource(Application context, ApiService apiService, PreferencesHelper preferences) {
        this.apiService = apiService;
        this.preferences = preferences;
        this.context = context;
    }

    @Override
    public void getSchedules(@NonNull final ScheduleDataSource.LoadScheduleCallback callback) {
//        String token = preferences.getAccessToken();
//        final String gradesETag = preferences.getPrefGradesETag();

//        Call<List<Schedule>> call = apiService.getSchedules(token);
//        call.enqueue(new Callback<List<Schedule>>() {
//
//            List<Schedule> schedules = new ArrayList<>();
//
//            @Override
//            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
//                if(response.isSuccessful()){
//                    String eTag = response.headers().get("etag");
//                    schedules = response.body();
//                    callback.onGradeLoaded(processGrades(schedules, eTag);
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
        Schedule[] schedules = gson.fromJson(loadJsonFromAsset(), Schedule[].class);
        callback.onScheduleLoaded(new ArrayList<>(Arrays.asList(schedules)));

    }

    @Override
    public void getSchedule(@NonNull Long scheduleId, @NonNull GetScheduleCallback callback) {
        // nothing to do
    }

    @Override
    public void getSchedule(@NonNull String subjectName, @NonNull GetScheduleCallback callback) {
        // nothing to do
    }

    @Override
    public void save(@NonNull Schedule schedule) {
        // nothing to do
    }

    @Override
    public void refresh() {
        // nothing to do
    }

    @Override
    public void deleteAll() {
        // nothing to do
    }

    private List<Grades> processGrades(List<Grades> grades, String eTag) {
        final String gradesETag = preferences.getPrefGradesETag();
        if (!gradesETag.equals(eTag)) {
            return grades;
        } else {
            return new ArrayList<>(0);
        }
    }

    private String loadJsonFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("schedule_dto.json");
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
