package com.gurpster.facapemobile.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.source.GradeRepository;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.remote.ApiService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 20/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class GradeService extends Service {

    private static final boolean DEBUG = true;
    private static final String TAG = "GradeService";

    private List<Worker> threads;
//    private ApiService apiService = RetrofitServiceFactory.createService(ApiService.class);
    private String authToken = null;

    @Inject GradeRepository gradeRepository;
    @Inject PreferencesHelper preferences;
    @Inject ApiService apiService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //TODO
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
        threads = new ArrayList<>();
        if(DEBUG) Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(DEBUG) Log.d(TAG, "onStartCommand()");

        Worker worker = new Worker(startId);
        worker.start();
        threads.add(worker);

        return START_REDELIVER_INTENT;
//        return super.onStartCommand(intent, flags, startId);
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

    private void getServerKey() {
        Call<ResponseBody> call = apiService.signature();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    String serverK = response.headers().get("srvpbk");
                    if(serverK != null && !preferences.getServerKey().equals(serverK)) preferences.setServerKey(serverK);
                } else {
                    Log.d(TAG, response.errorBody() + "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void getGrade() {
//        authToken = preferences.getAccessToken();
//        Call<List<Grades>> call = apiService.getGrades(authToken);
//        call.enqueue(new Callback<List<Grades>>() {
//            List<Grades> grades = new ArrayList<>();
//            @Override
//            public void onResponse(Call<List<Grades>> call, Response<List<Grades>> response) {
//                if(response.isSuccessful()){
//                    String remoteEtag = response.headers().get("etag");
//                    String localEtag = preferences.getPrefGradesETag();
//                    if( !localEtag.equals(remoteEtag) ) {
//                        // notificar usuario sobre novas totas postadas
//                        if( preferences.getNotifyAboutNewGrades() ) {
//                            // TODO logica para notificar usuario aqui
//                        }
//                    }
//                    Log.d("SERVICE 2 RESPONSE", "ETag " + etag);
//                    grades = response.body();
//                    gradeRepository.deleteAllGrades();
//                    Log.d("SERVICE 2 RESPONSE", "deleteAllGrades ");
//                    for(Grades grade : grades) {
//                        gradeRepository.saveGrade(grade);
////                        Log.d("SERVICE 2 RESPONSE", "Save grade: " + grade.getSubject());
//                    }
//                    gradeRepository.refreshGrades();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Grades>> call, Throwable t) {
//                Log.d("SERVICE 2 RESPONSE", "Error: " + t.getMessage());
//            }
//        });
        gradeRepository.deleteAllGrades();
        Gson gson = new Gson();
        Grades[] grades = gson.fromJson(loadJsonFromAsset(), Grades[].class);
        for(Grades grade : grades) {
            gradeRepository.saveGrade(grade);
            Log.d("SERVICE 2", "save grade: " + grade.getSubject());
        }
        gradeRepository.refreshGrades();
    }

    class Worker extends Thread {
        int startId;
        int count = 0;
        boolean ativo = true;

        Worker(int startId) {
            this.startId = startId;
        }

        @Override
        public void run() {

//            while (ativo && count < 20) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                count++;
//                if(DEBUG) Log.d(TAG, "Count: " + count);
//            }
//            getServerKey();
            getGrade();

            stopSelf(startId);
        }
    }

    private String loadJsonFromAsset() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("grades_dto.json");
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

//    private void sendNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//        builder.setSmallIcon(R.drawable.ic_alarm_white_24dp);
//        builder.setColor(ContextCompat.getColor(context, R.color.accent));
//        builder.setContentTitle(context.getString(R.string.app_name));
//        builder.setContentText(alarm.getLabel());
//        builder.setTicker(alarm.getLabel());
//        builder.setVibrate(new long[] {1000,500,1000,500,1000,500});
//        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        builder.setContentIntent(pIntent);
//        builder.setAutoCancel(true);
//        builder.setPriority(Notification.PRIORITY_HIGH);
//    }
}
