package com.gurpster.facapemobile.di.module;

import android.app.Application;

import com.gurpster.facapemobile.data.source.GradeDataSource;
import com.gurpster.facapemobile.data.source.GradeRepository;
import com.gurpster.facapemobile.data.source.Local;
import com.gurpster.facapemobile.data.source.Remote;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.local.sqlite.AppDatabase;
import com.gurpster.facapemobile.data.source.local.sqlite.GradeDao;
import com.gurpster.facapemobile.data.source.local.sqlite.GradeLocalDataSource;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.data.source.remote.GradeRemoteDataSource;
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
 * This class is used by Dagger to inject the required arguments into the {@link GradeRepository}.
 */
@Module
public class GradeRepositoryModule {

//    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    @Local
    GradeDataSource provideGradeLocalDataSource(GradeDao dao, AppExecutors executors) {
        return new GradeLocalDataSource(executors, dao);
    }

    @Singleton
    @Provides
    @Remote
    GradeDataSource provideGradeRemoteDataSource(Application context, ApiService apiService, PreferencesHelper preferences) {
        return new GradeRemoteDataSource(context, apiService, preferences);
    }

//    @Singleton
//    @Provides
//    AppDatabase provideDatabase(Application context) {
//        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DB_NAME)
//                .build();
//    }

    @Singleton
    @Provides
    GradeDao provideGradeDao(AppDatabase database) {
        return database.gradeDao();
    }

//    @Singleton
//    @Provides
//    AppExecutors provideAppExecutors() {
//        return new AppExecutors(new DiskIOThreadExecutor(),
//                Executors.newFixedThreadPool(THREAD_COUNT),
//                new AppExecutors.MainThreadExecutor());
//    }
}
