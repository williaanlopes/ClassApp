package com.gurpster.facapemobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Williaan Lopes (d3x773r) on 22/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class Student implements Parcelable {

    private String id;
    private String name;
    private String course;
    private String period;
    private String shift;

    public Student() {
    }


    public Student(String name, String course, String period, String shift) {
        this.name = name;
        this.course = course;
        this.period = period;
        this.shift = shift;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.course);
        dest.writeString(this.period);
        dest.writeString(this.shift);
    }

    protected Student(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.course = in.readString();
        this.period = in.readString();
        this.shift = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
