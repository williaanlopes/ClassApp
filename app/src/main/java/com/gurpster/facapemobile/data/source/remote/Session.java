package com.gurpster.facapemobile.data.source.remote;

/**
 * Created by Williaan Lopes (d3x773r) on 20/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface Session {

    boolean isLoggedIn();

    void saveToken(String token);

    String getToken();

    void saveEmail(String email);

    String getEmail();

    void savePassword(String password);

    String getPassword();

    void invalidate();
}
