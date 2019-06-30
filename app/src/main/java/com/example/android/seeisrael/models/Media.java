package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Media implements Parcelable {

    @SerializedName("type")
    public String type;

    @SerializedName("url")
    public String mediaUrl;

    @SerializedName("id")
    public String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.mediaUrl);
        dest.writeString(this.id);
    }

    public Media() {
    }

    protected Media(Parcel in) {
        this.type = in.readString();
        this.mediaUrl = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
