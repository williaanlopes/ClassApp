package com.gurpster.facapemobile.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.util.Constants;
import com.gurpster.facapemobile.util.OfflineCacheInterceptor;
import com.gurpster.facapemobile.util.CacheInterceptor;
import com.gurpster.facapemobile.util.TokenInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Williaan Lopes (d3x773r) on 12/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public class ApiModule {

    // Dagger will only look for methods annotated with @Provides
//    @Provides
//    @Singleton
//    // Application reference must come from AppModule.class
//    SharedPreferences providesSharedPreferences(Application application) {
//        return PreferenceManager.getDefaultSharedPreferences(application);
//    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(application.getCacheDir(), "http"), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

//    @Provides
//    @Singleton
//    @Named("offline-cache-interceptor")
//    Interceptor provideOfflineCacheInterceptor (final Application context) {
//        return new Interceptor() {
//            @Override
//            public Response intercept (Chain chain) throws IOException {
//                Request request = chain.request();
//
//                if ( !NetworkUtil.isNetworkConnected(context)) {
//                    CacheControl cacheControl = new CacheControl.Builder()
//                            .maxStale( 1, TimeUnit.DAYS )
//                            .build();
//
//                    request = request.newBuilder()
//                            .cacheControl( cacheControl )
//                            .build();
//                }
//
//                return chain.proceed( request );
//            }
//        };
//    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(TokenInterceptor interceptor, CacheInterceptor cachingInterceptor, OfflineCacheInterceptor offlineInterceptor, Cache cache) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(cachingInterceptor)
                .addInterceptor(interceptor)
                .addInterceptor(offlineInterceptor)
                .cache(cache)
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    TokenInterceptor provideTokenInterceptor(PreferencesHelper preferences) {
        return new TokenInterceptor(preferences);
    }

    @Provides
    @Singleton
    CacheInterceptor provideCacheInterceptor() {
        return new CacheInterceptor();
    }

    @Provides
    @Singleton
    OfflineCacheInterceptor provideOfflineCacheInterceptor(Context context) {
        return new OfflineCacheInterceptor(context);
    }
}
