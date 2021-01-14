/**
 * 
 */
package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author  Williaan Souza (d3x773r)
 * @company NTI Sistemas - FACAPE
 * @mail    williaan.lopes@facape.br
 * @date    16 de nov de 2017
 */

@Entity
public class Student implements Parcelable {

    @Expose(deserialize = false, serialize = false)
	private int id;

    @Expose
    @SerializedName("name")
	private String name; // nome do aluno

    @Expose
    @SerializedName("short_name")
    private String shortName; // nome "curto", primeiro nome do aluno (somente para exibicao)

    @Expose
    @SerializedName("course")
    private String course; // curso {direito, administracao, contabeis, computacao, ...}

    @Expose
    @SerializedName("period")
    private String period; // periodo {1° periodo, 2° periodo, ...}

    @Expose
    @SerializedName("shift")
    private String shift; // ??

    public Student(String name, String shortName, String course, String period, String shift) {
        this.name = name;
        this.shortName = shortName;
        this.course = course;
        this.period = period;
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.course);
        dest.writeString(this.period);
        dest.writeString(this.shift);
    }

    protected Student(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.course = in.readString();
        this.period = in.readString();
        this.shift = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
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
