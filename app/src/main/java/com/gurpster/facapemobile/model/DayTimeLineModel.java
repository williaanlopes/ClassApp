package com.gurpster.facapemobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Williaan Lopes (d3x773r) on 05/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class DayTimeLineModel implements Parcelable {

    private String mMessage;
    private String mDate;
    private OrderStatus mStatus;

    public DayTimeLineModel() {
    }

    public DayTimeLineModel(String mMessage, String mDate, OrderStatus mStatus) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mStatus = mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public void semMessage(String message) {
        this.mMessage = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public OrderStatus getStatus() {
        return mStatus;
    }

    public void setStatus(OrderStatus mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMessage);
        dest.writeString(this.mDate);
        dest.writeInt(this.mStatus == null ? -1 : this.mStatus.ordinal());
    }

    protected DayTimeLineModel(Parcel in) {
        this.mMessage = in.readString();
        this.mDate = in.readString();
        int tmpMStatus = in.readInt();
        this.mStatus = tmpMStatus == -1 ? null : OrderStatus.values()[tmpMStatus];
    }

    public static final Parcelable.Creator<DayTimeLineModel> CREATOR = new Parcelable.Creator<DayTimeLineModel>() {
        @Override
        public DayTimeLineModel createFromParcel(Parcel source) {
            return new DayTimeLineModel(source);
        }

        @Override
        public DayTimeLineModel[] newArray(int size) {
            return new DayTimeLineModel[size];
        }
    };
}
