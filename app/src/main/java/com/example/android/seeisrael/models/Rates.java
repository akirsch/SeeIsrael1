package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rates implements Parcelable {

    @SerializedName("CAD")
    public double canadianDollars;

    @SerializedName("ZAR")
    public double southAfricanRand;

    @SerializedName("EUR")
    public double euro;

    @SerializedName("RUB")
    public double roubles;

    @SerializedName("USD")
    public double USDollars;

    @SerializedName("GBP")
    public double britishPounds;

    public Rates() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.canadianDollars);
        dest.writeDouble(this.southAfricanRand);
        dest.writeDouble(this.euro);
        dest.writeDouble(this.roubles);
        dest.writeDouble(this.USDollars);
        dest.writeDouble(this.britishPounds);
    }

    protected Rates(Parcel in) {
        this.canadianDollars = in.readDouble();
        this.southAfricanRand = in.readDouble();
        this.euro = in.readDouble();
        this.roubles = in.readDouble();
        this.USDollars = in.readDouble();
        this.britishPounds = in.readDouble();
    }

    public static final Creator<Rates> CREATOR = new Creator<Rates>() {
        @Override
        public Rates createFromParcel(Parcel source) {
            return new Rates(source);
        }

        @Override
        public Rates[] newArray(int size) {
            return new Rates[size];
        }
    };
}
