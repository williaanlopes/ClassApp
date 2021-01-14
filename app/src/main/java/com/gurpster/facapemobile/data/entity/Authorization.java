package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Williaan Lopes (d3x773r) on 20/04/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class Authorization implements Parcelable {

    @Expose
    @SerializedName("token_type")
    private String tokenType;

    @Expose
    @SerializedName("expires_in")
    private String expiresIn;

    @Expose
    @SerializedName("access_token")
    private String accessToken;

    public Authorization() {
    }

    @Ignore
    public Authorization(String tokenType, String expiresIn, String accessToken) {
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tokenType);
        dest.writeString(this.expiresIn);
        dest.writeString(this.accessToken);
    }

    protected Authorization(Parcel in) {
        this.tokenType = in.readString();
        this.expiresIn = in.readString();
        this.accessToken = in.readString();
    }

    public static final Creator<Authorization> CREATOR = new Creator<Authorization>() {
        @Override
        public Authorization createFromParcel(Parcel source) {
            return new Authorization(source);
        }

        @Override
        public Authorization[] newArray(int size) {
            return new Authorization[size];
        }
    };
}
