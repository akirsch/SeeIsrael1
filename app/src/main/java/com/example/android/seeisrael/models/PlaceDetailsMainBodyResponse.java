package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaceDetailsMainBodyResponse implements Parcelable {

    @SerializedName("status_code")
    public int responseCode;

    @SerializedName("data")
    public SingleLocationData data;

    @SerializedName("server_timestamp")
    private String timeStamp;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.responseCode);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.timeStamp);
    }

    public PlaceDetailsMainBodyResponse() {
    }

    protected PlaceDetailsMainBodyResponse(Parcel in) {
        this.responseCode = in.readInt();
        this.data = in.readParcelable(SingleLocationData.class.getClassLoader());
        this.timeStamp = in.readString();
    }

    public static final Parcelable.Creator<PlaceDetailsMainBodyResponse> CREATOR = new Parcelable.Creator<PlaceDetailsMainBodyResponse>() {
        @Override
        public PlaceDetailsMainBodyResponse createFromParcel(Parcel source) {
            return new PlaceDetailsMainBodyResponse(source);
        }

        @Override
        public PlaceDetailsMainBodyResponse[] newArray(int size) {
            return new PlaceDetailsMainBodyResponse[size];
        }
    };
}
