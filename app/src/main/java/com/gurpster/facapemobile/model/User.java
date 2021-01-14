package com.gurpster.facapemobile.model;

import java.io.Serializable;

/**
 * Created by willi on 03/11/2017.
 */

public class User implements Serializable {

    private String name;
    private String couse;
    private String grade;

    public User(String name, String couse, String grade) {
        this.name = name;
        this.couse = couse;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouse() {
        return couse;
    }

    public void setCouse(String couse) {
        this.couse = couse;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
