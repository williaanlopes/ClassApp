package com.gurpster.facapemobile.util;

/**
 * Created by Williaan Lopes (d3x773r) on 19/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class Constants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "facapemobile.db";
    public static final String PREF_NAME = "com.gurpster.facapemobile_preferences";

//    public static final String BASE_URL = "http://10.0.0.104:8080/";
    public static final String BASE_URL = "http://facapemobile-heroku.herokuapp.com/";
    public static final String TOKEN_URL_AUTH = "http://facapemobile-heroku.herokuapp.com/api/auth";

    public static final String NOTIFICATION_PARAMETER_1 = "ticker";
    public static final String NOTIFICATION_PARAMETER_2 = "title";
    public static final String NOTIFICATION_PARAMETER_3 = "message";

    public static final int MAX_AGE = 604800; // = 1 semana
    public static final int MAX_STALE = 259200; // = 3 dias

    public final static String PREFERENCES = "AlarmPreferences";
    public final static String PREF_HOUR = "hour";
    public final static String PREF_MIN = "minute";
    public final static String PREF_DELAY = "delayMinute";
    public final static String PREF_SNOOZE = "snoozeMinute";
    public final static String PREF_PLAYLIST_ID = "playlistID";
    public final static String PREF_PLAYLIST_NAME = "playlistName";
    public final static String PREF_RINGTONE_URI = "ringtoneUri";
    public final static String PREF_RINGTONE_NAME = "ringtoneName";
    public final static String PREF_ALARM_SET = "alarmSet";
    public final static String PREF_TRIGGER_TIME = "triggerTime";

    public final static int DEFAULT_HOUR = 8;
    public final static int DEFAULT_MINUTE = 30;
    public final static int DEFAULT_DELAY = 10;
    public final static int DEFAULT_SNOOZE = 10;

    private final static int RINGTONE_PICKER_REQUEST_CODE = 1234;
}
