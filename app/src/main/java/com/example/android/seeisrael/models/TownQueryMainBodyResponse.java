package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TownQueryMainBodyResponse implements Parcelable {

    @SerializedName("status_code")
    public int responseCode;

    @SerializedName("data")
    public Data data;

    @SerializedName("server_timestamp")
    private String timeStamp;

    public TownQueryMainBodyResponse() {}

    public Data getData() {
        return data;
    }

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

    protected TownQueryMainBodyResponse(Parcel in) {
        this.responseCode = in.readInt();
        this.data = in.readParcelable(Data.class.getClassLoader());
        this.timeStamp = in.readString();
    }

    public static final Parcelable.Creator<TownQueryMainBodyResponse> CREATOR = new Parcelable.Creator<TownQueryMainBodyResponse>() {
        @Override
        public TownQueryMainBodyResponse createFromParcel(Parcel source) {
            return new TownQueryMainBodyResponse(source);
        }

        @Override
        public TownQueryMainBodyResponse[] newArray(int size) {
            return new TownQueryMainBodyResponse[size];
        }
    };
}
