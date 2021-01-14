package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Williaan Lopes (d3x773r) on 24/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Entity
public class Debt__ implements Parcelable {

    private String title;
    private Double value;
    private String url;

    public Debt__(){

    }

    @Ignore
    public Debt__(String title, Double value) {
        this.title = title;
        this.value = value;
    }

    @Ignore
    public Debt__(String title, Double value, String url) {
        this.title = title;
        this.value = value;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.value);
        dest.writeString(this.url);
    }

    protected Debt__(Parcel in) {
        this.title = in.readString();
        this.value = (Double) in.readValue(Double.class.getClassLoader());
        this.url = in.readString();
    }

    public static final Creator<Debt__> CREATOR = new Creator<Debt__>() {
        @Override
        public Debt__ createFromParcel(Parcel source) {
            return new Debt__(source);
        }

        @Override
        public Debt__[] newArray(int size) {
            return new Debt__[size];
        }
    };
}
