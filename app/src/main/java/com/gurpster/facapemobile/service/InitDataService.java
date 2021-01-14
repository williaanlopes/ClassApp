package com.gurpster.facapemobile.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Student;
import com.gurpster.facapemobile.data.source.GradeRepository;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.remote.ApiService;
//import com.gurpster.facapemobile.model.Grade;
import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.model.Subject;
import com.gurpster.facapemobile.receiver.WifiScannReceiver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 20/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@ActivityScoped
public class InitDataService extends Service {

    private static final String TAG = InitDataService.class.getSimpleName();

    private List<Worker> threads = new ArrayList<>();
//    private ApiService apiService = RetrofitServiceFactory.createService(ApiService.class);
//    private List<Grades> grades = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();

    private String authToken = null;

    @Inject ApiService apiService;
    @Inject PreferencesHelper preferences;
    @Inject GradeRepository gradeRepository;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
        Log.d("Service", "onCreate()");

//        registerReceiver(new WifiScannReceiver(), new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int index = 0;
        Log.d(TAG, "onStartCommand()");

        this.authToken = preferences.getAccessToken();

        Worker worker = new Worker(startId);
        worker.start();
        threads.add(worker);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        for (Worker worker : threads) {
//            worker.ativo = false;
            stopSelf(worker.startId);
        }
        Log.d(TAG, "Stop Service");
        super.onDestroy();
    }

    private void getStudent() {
        Call<Student> call = apiService.getStudent(authToken);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()) {
                    Student student = response.body();
                    storeData(student);
                } else {
                    Log.d(TAG, response.errorBody() + "");
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void storeData(Student student) {
        Gson gson = new Gson();
        String data = gson.toJson(student);
        preferences.setStudent(data);

//        preferences.setUserName(student.getName());
//        preferences.setUserShortName(student.getShortName());
//        preferences.setCourse(student.getCourse());
//        preferences.setPeriod(student.getPeriod());
//        preferences.setShift(student.getShift());
    }

    public void getGrade() {
        Call<List<Grades>> call = apiService.getGrades(authToken);
        call.enqueue(new Callback<List<Grades>>() {
            List<Grades> grades = new ArrayList<>();
            @Override
            public void onResponse(Call<List<Grades>> call, Response<List<Grades>> response) {
                if(response.isSuccessful()){
                    grades = response.body();
                    gradeRepository.deleteAllGrades();
                    for(Grades grade : grades) {
                        gradeRepository.saveGrade(grade);
                        Log.d("SERVICE 1 RESPONSE", "grade: " + grade.getSubject());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Grades>> call, Throwable t) {

            }
        });

//        Gson gson = new Gson();
//        Grades[] grades = gson.fromJson(loadJsonFromAsset(), Grades[].class);
//        JsonReader reader = new JsonReader(new FileReader(filename));

//        for(Grades grade : grades) {
//            gradeRepository.saveGrade(grade);
//            Log.d("SERVICE 1", "save grade: " + grade.getSubject());
//        }
    }

    private void getSubjects() {

        Call<List<Subject>> call = apiService.getSubjects(authToken);
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if(response.isSuccessful()) {
                    subjects = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {

            }
        });
    }

    class Worker extends Thread {
        int startId;
        public int count = 0;
        public boolean ativo = true;

        Worker(int startId) {
            this.startId = startId;
        }

        @Override
        public void run() {
//            getGrade();
            getStudent();
            stopSelf(startId);
        }
    }
}
