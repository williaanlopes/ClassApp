package com.gurpster.facapemobile.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.gurpster.facapemobile.data.source.local.sqlite.AppDatabase;
import com.gurpster.facapemobile.util.AppExecutors;
import com.gurpster.facapemobile.util.Constants;
import com.gurpster.facapemobile.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Williaan Lopes (d3x773r) on 16/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public class AppDatabaseModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    AppDatabase provideDatabase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
