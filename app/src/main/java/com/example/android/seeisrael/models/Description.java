package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Description implements Parcelable {

    @SerializedName("text")
    private String longDescription;

    @SerializedName("link")
    private String wikiUrl;

    public Description(String longDescription, String wikiUrl) {
        this.longDescription = longDescription;
        this.wikiUrl = wikiUrl;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.longDescription);
        dest.writeString(this.wikiUrl);
    }

    protected Description(Parcel in) {
        this.longDescription = in.readString();
        this.wikiUrl = in.readString();
    }

    public static final Parcelable.Creator<Description> CREATOR = new Parcelable.Creator<Description>() {
        @Override
        public Description createFromParcel(Parcel source) {
            return new Description(source);
        }

        @Override
        public Description[] newArray(int size) {
            return new Description[size];
        }
    };
}
