package com.gurpster.facapemobile.model;

/**
 * Created by Williaan Lopes (d3x773r) on 22/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class LoginA {

    private String login;
    private String password;

    public LoginA(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
