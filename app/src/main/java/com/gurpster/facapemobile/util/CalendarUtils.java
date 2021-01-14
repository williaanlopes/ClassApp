package com.gurpster.facapemobile.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.gurpster.facapemobile.view.activities.DrawerActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by Williaan Lopes (d3x773r) on 05/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class CalendarUtils {

    public static String getCurrentDayBr() {

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
//        String day = dayFormat.format(calendar.getTime());

        return intToStringDayBrShort(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    }

    public static String dayUsToBr(String dayUs) {

        Map<String, String> map = new HashMap<>();

        map.put("Monday", "Segunda-Feira");
        map.put("Tuesday", "Terça-Feira");
        map.put("Wednesday", "Quarta-Feira");
        map.put("Thursday", "Quinta-Feira");
        map.put("Friday", "Sexta-Feira");
        map.put("Saturday", "Sábado");
        map.put("Sunday", "Domingo");

        return map.get(dayUs);
    }

    public static String dayBrToUs(String dayUs) {

        Map<String, String> map = new HashMap<>();

        map.put("Segunda-Feira", "Monday");
        map.put("Terça-Feira", "Tuesday");
        map.put("Quarta-Feira", "Wednesday");
        map.put("Quinta-Feira", "Thursday");
        map.put("Sexta-Feira", "Friday");
        map.put("Sábado", "Saturday");
        map.put("Domingo", "Sunday");

        return map.get(dayUs);
    }

//    public static String intToStringDayBr(int day) {
//
//        Map<Integer, String> map = new HashMap<>();
//
//        map.put(1, "Segunda-Feira");
//        map.put(2, "Terça-Feira");
//        map.put(3, "Quarta-Feira");
//        map.put(4, "Quinta-Feira");
//        map.put(5, "Sexta-Feira");
//        map.put(6, "Sábado");
//
//        return map.get(day);
//    }

    public static String intToStringDayBr(int day) {

        Map<Integer, String> map = new HashMap<>();

        map.put(2, "Segunda-Feira");
        map.put(3, "Terça-Feira");
        map.put(4, "Quarta-Feira");
        map.put(5, "Quinta-Feira");
        map.put(6, "Sexta-Feira");
        map.put(7, "Sábado");
        map.put(8, "Domingo");

        return map.get(day);
    }

    public static String intToStringDayBrShort(int day) {

        Map<Integer, String> map = new HashMap<>();

        map.put(2, "Segunda");
        map.put(3, "Terça");
        map.put(4, "Quarta");
        map.put(5, "Quinta");
        map.put(6, "Sexta");
        map.put(7, "Sábado");
        map.put(8, "Domingo");

        return map.get(day);
    }

    public static int stringToIntDayBr(String day) {

        Map<String, Integer> map = new HashMap<>();

        map.put("Segunda-Feira", 2);
        map.put("Terça-Feira", 3);
        map.put("Quarta-Feira", 4);
        map.put("Quinta-Feira", 5);
        map.put("Sexta-Feira", 6);
        map.put("Sábado", 7);
        map.put("Domingo", 8);

        return map.get(day);
    }

    public static int stringToIntDayBrShort(String day) {

        Map<String, Integer> map = new HashMap<>();

        map.put("Segunda", 2);
        map.put("Terça", 3);
        map.put("Quarta", 4);
        map.put("Quinta", 5);
        map.put("Sexta", 6);
        map.put("Sábado", 7);
        map.put("Domingo", 8);

        return map.get(day);
    }


    public static int stringToIntDayUs(String day) {

        Map<String, Integer> map = new HashMap<>();

        map.put("Monday", 2);
        map.put("Tuesday", 3);
        map.put("Wednesday", 4);
        map.put("Thursday", 5);
        map.put("Friday", 6);
        map.put("Saturday", 7);
        map.put("Sunday", 8);

        return map.get(day);
    }

    public static void createAlarm(final Context context) {

        int year = 2018; // semestre
        int month = 6; // ultimo mes do semestre {julho | dezembro}

//        Intent intent = new Intent("FACAPE_MOBILE_ALARM");
        Intent intent = new Intent(context, DrawerActivity.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 45);

        PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

        Log.d("Alarme", "Created!");

    }

    public static String getCurrentDateBrFormat() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String month = new SimpleDateFormat("MMMM", Locale.US).format(calendar.getTime());
        int year = calendar.get(Calendar.YEAR);
        return day + " de " + month + " de " + year;
    }

    public static boolean compareTime( String day, String time ) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(day));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
        calendar.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();
        int diff = DateTimeUtils.getDateDiff(calendar.getTime(), now.getTime(), DateTimeUnits.MINUTES);
        return diff >= 0;
    }
}
