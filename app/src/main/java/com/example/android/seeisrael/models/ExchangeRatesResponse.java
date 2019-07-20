package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ExchangeRatesResponse implements Parcelable {

    @SerializedName("rates")
    public Rates rates;

    @SerializedName("base")
    public String baseCurrency;

    @SerializedName("date")
    public String date;

    public ExchangeRatesResponse(Rates rates, String baseCurrency, String date) {
        this.rates = rates;
        this.baseCurrency = baseCurrency;
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.rates, flags);
        dest.writeString(this.baseCurrency);
        dest.writeString(this.date);
    }

    protected ExchangeRatesResponse(Parcel in) {
        this.rates = in.readParcelable(Rates.class.getClassLoader());
        this.baseCurrency = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<ExchangeRatesResponse> CREATOR = new Parcelable.Creator<ExchangeRatesResponse>() {
        @Override
        public ExchangeRatesResponse createFromParcel(Parcel source) {
            return new ExchangeRatesResponse(source);
        }

        @Override
        public ExchangeRatesResponse[] newArray(int size) {
            return new ExchangeRatesResponse[size];
        }
    };
}
