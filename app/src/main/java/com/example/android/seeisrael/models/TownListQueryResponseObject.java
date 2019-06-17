package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TownListQueryResponseObject implements Parcelable {

    @SerializedName("status_code")
    private int responseCode;

    @SerializedName("data")
    private TownListArrayContainerObject mTownListArrayContainerObject;

    @SerializedName("server_timestamp")
    private String timeStamp;

    public TownListQueryResponseObject() {}

    public TownListArrayContainerObject getmTownListArrayContainerObject() {
        return mTownListArrayContainerObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.responseCode);
        dest.writeParcelable(this.mTownListArrayContainerObject, flags);
        dest.writeString(this.timeStamp);
    }

    protected TownListQueryResponseObject(Parcel in) {
        this.responseCode = in.readInt();
        this.mTownListArrayContainerObject = in.readParcelable(TownListArrayContainerObject.class.getClassLoader());
        this.timeStamp = in.readString();
    }

    public static final Parcelable.Creator<TownListQueryResponseObject> CREATOR = new Parcelable.Creator<TownListQueryResponseObject>() {
        @Override
        public TownListQueryResponseObject createFromParcel(Parcel source) {
            return new TownListQueryResponseObject(source);
        }

        @Override
        public TownListQueryResponseObject[] newArray(int size) {
            return new TownListQueryResponseObject[size];
        }
    };
}
