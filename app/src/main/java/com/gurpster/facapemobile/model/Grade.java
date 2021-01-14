package com.gurpster.facapemobile.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Williaan Lopes (d3x773r) on 17/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Entity
public class Grade implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String subjectName; // nome da disciplina

    private String status; // status {matriculado | aprovado | reprovado}

    private String absent; //

    private String mark1; // primeira nota

    private String mark2; // segunda nota

    private String mark3; // terceira nota

    private String mark4; // final nota

    private String average; // media

    public Grade(String subjectName, String status, String absent, String mark1, String mark2, String mark3, String mark4, String average) {
        this.subjectName = subjectName;
        this.status = status;
        this.absent = absent;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.mark4 = mark4;
        this.average = average;
    }

    @Ignore
    public Grade(Long id, String subjectName, String status, String absent, String mark1, String mark2, String mark3, String mark4, String average) {
        this.id = id;
        this.subjectName = subjectName;
        this.status = status;
        this.absent = absent;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.mark4 = mark4;
        this.average = average;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        this.mark3 = mark3;
    }

    public String getMark4() {
        return mark4;
    }

    public void setMark4(String mark4) {
        this.mark4 = mark4;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", subjectName='" + subjectName + '\'' +
                ", status='" + status + '\'' +
                ", absent='" + absent + '\'' +
                ", mark1='" + mark1 + '\'' +
                ", mark2='" + mark2 + '\'' +
                ", mark3='" + mark3 + '\'' +
                ", mark4='" + mark4 + '\'' +
                ", average='" + average + '\'' +
                '}';
    }
}
