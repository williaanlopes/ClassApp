package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Williaan Lopes (d3x773r) on 14/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Entity
public class Semester implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @Expose
    private Date year;

    @Expose
    @SerializedName("half_year")
    private int halfYear;

    public Semester() {
    }

    public Semester(Date year, int halfYear) {
        this.year = year;
        this.halfYear = halfYear;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public int getHalfYear() {
        return halfYear;
    }

    public void setHalfYear(int halfYear) {
        this.halfYear = halfYear;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeLong(this.year != null ? this.year.getTime() : -1);
        dest.writeInt(this.halfYear);
    }

    protected Semester(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        long tmpYear = in.readLong();
        this.year = tmpYear == -1 ? null : new Date(tmpYear);
        this.halfYear = in.readInt();
    }

    public static final Creator<Semester> CREATOR = new Creator<Semester>() {
        @Override
        public Semester createFromParcel(Parcel source) {
            return new Semester(source);
        }

        @Override
        public Semester[] newArray(int size) {
            return new Semester[size];
        }
    };
}
