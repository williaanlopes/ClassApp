package com.gurpster.facapemobile.di.module;

import android.app.Application;

import com.gurpster.facapemobile.data.source.ScheduleRepository;
import com.gurpster.facapemobile.data.source.Local;
import com.gurpster.facapemobile.data.source.Remote;
import com.gurpster.facapemobile.data.source.ScheduleDataSource;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.local.sqlite.AppDatabase;
import com.gurpster.facapemobile.data.source.local.sqlite.ScheduleDao;
import com.gurpster.facapemobile.data.source.local.sqlite.ScheduleLocalDataSource;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.data.source.remote.ScheduleRemoteDataSource;
import com.gurpster.facapemobile.util.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Williaan Lopes (d3x773r) on 09/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

/**
 * This class is used by Dagger to inject the required arguments into the {@link ScheduleRepository}.
 */
@Module
public class ScheduleRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    @Local
    ScheduleDataSource provideScheduleLocalDataSource(ScheduleDao dao, AppExecutors executors) {
        return new ScheduleLocalDataSource(executors, dao);
    }

    @Singleton
    @Provides
    @Remote
    ScheduleDataSource provideScheduleRemoteDataSource(Application context, ApiService apiService, PreferencesHelper preferences) {
        return new ScheduleRemoteDataSource(context, apiService, preferences);
    }

//    @Singleton
//    @Provides
//    AppDatabase provideDatabase(Application context) {
//        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DB_NAME)
//                .build();
//    }

    @Singleton
    @Provides
    ScheduleDao provideScheduleDao(AppDatabase database) {
        return database.scheduleDao();
    }

//    @Singleton
//    @Provides
//    AppExecutors provideAppExecutors() {
//        return new AppExecutors(new DiskIOThreadExecutor(),
//                Executors.newFixedThreadPool(THREAD_COUNT),
//                new AppExecutors.MainThreadExecutor());
//    }
}
