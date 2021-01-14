package com.gurpster.facapemobile.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.provider.Settings;

import com.gurpster.facapemobile.data.source.remote.ApiService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 16/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class InternetUtils {
//    static ApiService apiService = RetrofitServiceFactory.createService(ApiService.class);
    static boolean r = false;
    /**
     * Method determine whether there is an Internet connection
     *
     * @param context The first integer to add
     * @return true if connected or false if not
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Method tests whether the internet connection actually exists
     *
     * @return true if there is a connection or false if you are connected to the internet but have no communication
     * 
     */
    public static boolean hasActiveInternetConnection(ApiService apiService) {
//        try {
//            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
//            urlc.setRequestProperty("User-Agent", "Test");
//            urlc.setRequestProperty("Connection", "close");
//            urlc.setConnectTimeout(1500);
//            urlc.connect();
//            return urlc.getResponseCode() == 200;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Call<ResponseBody> call = apiService.testConnection();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) r = true;
                else r = false;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                r = false;
            }
        });

        return r;
    }

    /**
     * Method checks if the type of internet connection is wifi
     *
     * @param context The first integer to add
     * @return true if connected or false if not
     */
    public static boolean isConnectedToWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo.isConnected();
    }

    /**
     * Method checks if the type of internet connection is mobile network
     *
     * @param context The first integer to add
     * @return true if connected or false if not
     */
    public static boolean isConnectedToMobileNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileInfo.isConnected();
    }

    public static void turnOn3g(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    public static void turnOnWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null || isConnectedToWifi(context)) {
            context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            return;
        }

        wifiManager.setWifiEnabled(true);
    }

    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    public static void turnOffAirplaneMode(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd", Locale.getDefault());
        return df.format(c.getTime());
    }
}
