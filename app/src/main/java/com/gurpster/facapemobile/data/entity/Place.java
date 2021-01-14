package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Williaan Lopes (d3x773r) on 14/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Entity
public class Place implements Parcelable {

    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("complement")
    private String complement;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("responsible")
    private String responsible;
    @Expose
    @SerializedName("latitude")
    private Double latitude;
    @Expose
    @SerializedName("longitude")
    private Double longitude;

    public Place() {
    }

    @Ignore

    public Place(String name, String complement, String phone, String email, String responsible, Double latitude, Double longitude) {
        this.name = name;
        this.complement = complement;
        this.phone = phone;
        this.email = email;
        this.responsible = responsible;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
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

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.complement);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.responsible);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    protected Place(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.complement = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.responsible = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
