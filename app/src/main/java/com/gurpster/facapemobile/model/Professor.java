package com.gurpster.facapemobile.model;

import java.io.Serializable;

/**
 * Created by Williaan Lopes (d3x773r) on 24/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class Professor implements Serializable {

    private Long id;
    private String name;
    private String phone;
    private String email;

    public Professor(Long id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
