package com.gurpster.facapemobile.data.source.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.di.PreferenceContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Williaan Lopes (d3x773r) on 19/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class PreferencesHelper implements PreferenceContract {

    public static final String PREF_KEY_SERVER_KEY = "PREF_KEY_SERVER_KEY";
    public static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    public static final String PREF_KEY_LOGIN = "PREF_KEY_LOGIN";
    public static final String PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD";
    public static final String PREF_KEY_FIRST_ACCESS = "PREF_KEY_FIRST_ACCESS";
    public static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";
    public static final String PREF_KEY_USER_SHORT_NAME = "PREF_KEY_USER_SHORT_NAME";
    public static final String PREF_KEY_COURSE = "PREF_KEY_COURSE";
    public static final String PREF_KEY_PERIOD = "PREF_KEY_PERIOD";
    public static final String PREF_KEY_SHIFT = "PREF_KEY_SHIFT";
    public static final String PREF_MAIN_SCREEN = "PREF_MAIN_SCREEN";
    public static final String PREF_GRADES_ETAG = "PREF_GRADES_ETAG";
    public static final String PREF_SCHEDULE_ETAG = "PREF_SCHEDULE_ETAG";
    public static final String PREF_SUBJECT_SCHEDULE_NOTIFY = "PREF_SUBJECT_SCHEDULE_NOTIFY";
    public static final String PREF_GRADE_NOTIFY = "PREF_GRADE_NOTIFY";
    public static final String PREF_SCHEDULE_TYPE = "PREF_SCHEDULE_TYPE";
    public static final String PREF_KEY_STUDENT = "PREF_KEY_STUDENT";
    public static final String PREF_KEY_SPLASH_SCREEN = "PREF_KEY_SPLASH_SCREEN";

    private final SharedPreferences mPrefs;

    @Inject
    public PreferencesHelper(@ActivityScoped Context context, @PreferenceContext String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

//    @Inject
//    public PreferencesHelper(@ApplicationContext SharedPreferences preferences) {
//        mPrefs = preferences;
//    }

    @Override
    public String getServerKey() {
        return mPrefs.getString(PREF_KEY_SERVER_KEY, null);
    }

    @Override
    public void setServerKey(String serverKey) {
        mPrefs.edit().putString(PREF_KEY_SERVER_KEY, serverKey).apply();
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getLogin() {
        return mPrefs.getString(PREF_KEY_LOGIN, null);
    }

    @Override
    public void setLogin(String login) {
        mPrefs.edit().putString(PREF_KEY_LOGIN, login).apply();
    }

    @Override
    public String getPassword() {
        return mPrefs.getString(PREF_KEY_PASSWORD, null);
    }

    @Override
    public void setPassword(String password) {
        mPrefs.edit().putString(PREF_KEY_PASSWORD, password).apply();
    }

    @Override
    public String getUserName() {
        return mPrefs.getString(PREF_KEY_USER_NAME, null);
    }

    @Override
    public void setUserName(String name) {
        mPrefs.edit().putString(PREF_KEY_USER_NAME, name).apply();
    }

    @Override
    public String getUserShortName() {
        return mPrefs.getString(PREF_KEY_USER_SHORT_NAME, null);
    }

    @Override
    public void setUserShortName(String name) {
        mPrefs.edit().putString(PREF_KEY_USER_SHORT_NAME, name).apply();
    }

    @Override
    public String getCourse() {
        return mPrefs.getString(PREF_KEY_COURSE, null);
    }

    @Override
    public void setCourse(String course) {
        mPrefs.edit().putString(PREF_KEY_COURSE, course).apply();
    }

    @Override
    public String getPeriod() {
        return mPrefs.getString(PREF_KEY_PERIOD, null);
    }

    @Override
    public void setPeriod(String period) {
        mPrefs.edit().putString(PREF_KEY_PERIOD, period).apply();
    }

    @Override
    public String getShift() {
        return mPrefs.getString(PREF_KEY_SHIFT, null);
    }

    @Override
    public void setShift(String shift) {
        mPrefs.edit().putString(PREF_KEY_SHIFT, shift).apply();
    }

    @Override
    public boolean getFirstAccess() {
        return mPrefs.getBoolean(PREF_KEY_FIRST_ACCESS, true);
    }

    @Override
    public void setFirstAccess(boolean first) {
        mPrefs.edit().putBoolean(PREF_KEY_FIRST_ACCESS, true).apply();
    }

    @Override
    public String getMainScreen() {
        return mPrefs.getString(PREF_MAIN_SCREEN, "0");
    }

    @Override
    public void setMainScreen(String timeTable) {
        mPrefs.edit().putString(PREF_MAIN_SCREEN, timeTable).apply();
    }

    @Override
    public String getPrefGradesETag() {
        return mPrefs.getString(PREF_GRADES_ETAG, "0");
    }

    @Override
    public void setPrefGradesETag(String dxTag) {
        mPrefs.edit().putString(PREF_GRADES_ETAG, dxTag).apply();
    }

    @Override
    public String getScheduleETag() {
        return mPrefs.getString(PREF_SCHEDULE_ETAG, "0");
    }

    @Override
    public void setScheduleETag(String dxTag) {
        mPrefs.edit().putString(PREF_SCHEDULE_ETAG, dxTag).apply();
    }

    @Override
    public String getScheduleType() {
        return mPrefs.getString(PREF_SCHEDULE_TYPE, "0");
    }

    @Override
    public void setScheduleType(String type) {
        mPrefs.edit().putString(PREF_SCHEDULE_TYPE, type).apply();
    }

    @Override
    public boolean getGradeNotify() {
        return mPrefs.getBoolean(PREF_GRADE_NOTIFY, false);
    }

    @Override
    public void setGradeNotify(boolean b) {
        mPrefs.edit().putBoolean(PREF_GRADE_NOTIFY, b).apply();
    }

    @Override
    public void setStudent(String student) {
        mPrefs.edit().putString(PREF_KEY_STUDENT, student).apply();
    }

    @Override
    public String getStudent() {
        return mPrefs.getString(PREF_KEY_STUDENT, null);
    }

    @Override
    public void setSplashScreenTime(String time) {
        mPrefs.edit().putString(PREF_KEY_SPLASH_SCREEN, time).apply();
    }

    @Override
    public String getSplashScreenTime() {
        return mPrefs.getString(PREF_KEY_SPLASH_SCREEN, "0");
    }
}
