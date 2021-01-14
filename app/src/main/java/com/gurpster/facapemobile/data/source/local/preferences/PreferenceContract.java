package com.gurpster.facapemobile.data.source.local.preferences;

/**
 * Created by Williaan Lopes (d3x773r) on 19/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface PreferenceContract {

    String getServerKey();

    void setServerKey(String serverKey);

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String password);

    String getUserName();

    void setUserName(String name);

    String getUserShortName();

    void setUserShortName(String name);

    String getCourse();

    void setCourse(String course);

    String getPeriod();

    void setPeriod(String period);

    String getShift();

    void setShift(String shift);

    boolean getFirstAccess();

    void setFirstAccess(boolean first);

    String getMainScreen();

    void setMainScreen(String timeTable);

    String getPrefGradesETag();

    void setPrefGradesETag(String dxTag);

    String getScheduleETag();

    void setScheduleETag(String dxTag);

    String getScheduleType();

    void setScheduleType(String type);

    boolean getGradeNotify();

    void setGradeNotify(boolean b);

    void setStudent(String student);

    String getStudent();

    void setSplashScreenTime(String time);

    String getSplashScreenTime();
}
