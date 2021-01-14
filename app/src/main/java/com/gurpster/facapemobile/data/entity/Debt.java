package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Williaan Lopes (d3x773r) on 24/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Entity
public class Debt implements Parcelable {

    @Expose
    @SerializedName("type")
    private String type; // tipo de boleto {menssalidade, multa biblioteca, ....}

    @Expose
    @SerializedName("value")
    private Double value; // valor

    @Expose
    @SerializedName("discount")
    private Double discount; // desconto

    @Expose
    @SerializedName("fine")
    private Double fine; // multa

    @Expose
    @SerializedName("expiry_date")
    private String expiryDate; // data de vencimento

    @Expose
    @SerializedName("barcode")
    private String barcode; // codigo de barra do boleto

    public Debt() {
    }

    @Ignore
    public Debt(String type, Double value, Double discount, Double fine, String expiryDate, String barcode) {
        this.type = type;
        this.value = value;
        this.discount = discount;
        this.fine = fine;
        this.expiryDate = expiryDate;
        this.barcode = barcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeValue(this.value);
        dest.writeValue(this.discount);
        dest.writeValue(this.fine);
        dest.writeString(this.expiryDate);
        dest.writeString(this.barcode);
    }

    protected Debt(Parcel in) {
        this.type = in.readString();
        this.value = (Double) in.readValue(Double.class.getClassLoader());
        this.discount = (Double) in.readValue(Double.class.getClassLoader());
        this.fine = (Double) in.readValue(Double.class.getClassLoader());
        this.expiryDate = in.readString();
        this.barcode = in.readString();
    }

    public static final Creator<Debt> CREATOR = new Creator<Debt>() {
        @Override
        public Debt createFromParcel(Parcel source) {
            return new Debt(source);
        }

        @Override
        public Debt[] newArray(int size) {
            return new Debt[size];
        }
    };
}
