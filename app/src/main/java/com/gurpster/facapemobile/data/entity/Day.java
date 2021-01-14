package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Williaan Lopes (d3x773r) on 20/05/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Entity
public class Day implements Parcelable {

    @Expose
    @SerializedName("subject_name")
    private String subjectName; // nome da disciplina {Teoria dos grafos, Redes, Programacao 3, ...}

    @Expose
    @SerializedName("professor_name")
    private String professorName; // nome do professor da disciplina

    @Expose
    @SerializedName("period")
    private String period; // periodo pertinente a ESSA disciplina {1 periodo, 2 periodo, ...}

    @Expose
    @SerializedName("time")
    private String time; // horario da aula {7:30, 8:30, ..., 19:00, 20:30, ...}

    @Expose
    @SerializedName("course")
    private String course; // curso pertinente a ESSA disciplina {direito, administracao, contabeis, computacao, ...}

    public Day() {
    }

    @Ignore
    public Day(String subjectName, String professorName, String period, String time, String course) {
        this.subjectName = subjectName;
        this.professorName = professorName;
        this.period = period;
        this.time = time;
        this.course = course;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subjectName);
        dest.writeString(this.professorName);
        dest.writeString(this.period);
        dest.writeString(this.time);
        dest.writeString(this.course);
    }

    protected Day(Parcel in) {
        this.subjectName = in.readString();
        this.professorName = in.readString();
        this.period = in.readString();
        this.time = in.readString();
        this.course = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
