package com.gurpster.facapemobile.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by Williaan Lopes (d3x773r) on 16/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class DeviceUtils {

    public static String osVersion() {
        return System.getProperty("os.version");
    }

    public static String apiLevel() {
        return android.os.Build.VERSION.SDK_INT + "";
    }

    public static String device() {
        return android.os.Build.DEVICE;
    }

    public static String model() {
        return android.os.Build.MODEL;
    }

    public static String product() {
        return android.os.Build.PRODUCT;
    }

    public static String imei(Context context) {
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) context.getSystemService(serviceName);
        return m_telephonyManager.getDeviceId();
    }

    public static String imsi(Context context) {
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) context.getSystemService(serviceName);
        return m_telephonyManager.getSubscriberId();
    }

//    public static String APILevel() {
//        return android.os.Build.VERSION.SDK;
//    }

//    public static String[] deviceImformations() {
//        return
//    }
}
