package com.gurpster.facapemobile.di.module;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Williaan Lopes (d3x773r) on 20/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public class ExecutorModule {

    @Provides
    @Named("SingleThread")
    public Executor provideSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Named("MultiThread")
    public Executor provideMultiThreadExecutor() {
        return Executors.newCachedThreadPool();
    }
}
