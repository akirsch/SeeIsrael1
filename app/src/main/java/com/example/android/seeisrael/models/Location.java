package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable {

    @SerializedName("id")
    private String mId;

    @SerializedName("location")
    private GeoPosition mGeoPosition;

    @SerializedName("name")
    private String mName;

    @SerializedName("name_suffix")
    private String mNameSuffix;

    @SerializedName("perex")
    private String mSummary;

    @SerializedName("thumbnail_url")
    private String mThumbnailUrl;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("admission")
    private String mAdmissionInfo;

    @SerializedName("email")
    private String mEmailAddress;

    @SerializedName("phone")
    private String mPhoneNumber;

    @SerializedName("opening_hours")
    private String mOpeningHours;

    @SerializedName("description")
    private Description mDescription;

    @SerializedName("main_media")
    private MainMedia mMainMedia;

    public Location(String mId, GeoPosition mGeoPosition, String mName,
                    String mNameSuffix, String mSummary, String mThumbnailUrl, String mAddress,
                    String mAdmissionInfo, String mEmailAddress, String mPhoneNumber,
                    String mOpeningHours, Description mDescription, MainMedia mMainMedia) {
        this.mId = mId;
        this.mGeoPosition = mGeoPosition;
        this.mName = mName;
        this.mNameSuffix = mNameSuffix;
        this.mSummary = mSummary;
        this.mThumbnailUrl = mThumbnailUrl;
        this.mAddress = mAddress;
        this.mAdmissionInfo = mAdmissionInfo;
        this.mEmailAddress = mEmailAddress;
        this.mPhoneNumber = mPhoneNumber;
        this.mOpeningHours = mOpeningHours;
        this.mDescription = mDescription;
        this.mMainMedia = mMainMedia;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public GeoPosition getmGeoPosition() {
        return mGeoPosition;
    }

    public void setmGeoPosition(GeoPosition mGeoPosition) {
        this.mGeoPosition = mGeoPosition;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNameSuffix() {
        return mNameSuffix;
    }

    public void setmNameSuffix(String mNameSuffix) {
        this.mNameSuffix = mNameSuffix;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setmThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmAdmissionInfo() {
        return mAdmissionInfo;
    }

    public void setmAdmissionInfo(String mAdmissionInfo) {
        this.mAdmissionInfo = mAdmissionInfo;
    }

    public String getmEmailAddress() {
        return mEmailAddress;
    }

    public void setmEmailAddress(String mEmailAddress) {
        this.mEmailAddress = mEmailAddress;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmOpeningHours() {
        return mOpeningHours;
    }

    public void setmOpeningHours(String mOpeningHours) {
        this.mOpeningHours = mOpeningHours;
    }

    public Description getmDescription() {
        return mDescription;
    }

    public void setmDescription(Description mDescription) {
        this.mDescription = mDescription;
    }

    public MainMedia getmMainMedia() {
        return mMainMedia;
    }

    public void setmMainMedia(MainMedia mMainMedia) {
        this.mMainMedia = mMainMedia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeParcelable(this.mGeoPosition, flags);
        dest.writeString(this.mName);
        dest.writeString(this.mNameSuffix);
        dest.writeString(this.mSummary);
        dest.writeString(this.mThumbnailUrl);
        dest.writeString(this.mAddress);
        dest.writeString(this.mAdmissionInfo);
        dest.writeString(this.mEmailAddress);
        dest.writeString(this.mPhoneNumber);
        dest.writeString(this.mOpeningHours);
        dest.writeParcelable(this.mDescription, flags);
        dest.writeParcelable(this.mMainMedia, flags);
    }

    protected Location(Parcel in) {
        this.mId = in.readString();
        this.mGeoPosition = in.readParcelable(GeoPosition.class.getClassLoader());
        this.mName = in.readString();
        this.mNameSuffix = in.readString();
        this.mSummary = in.readString();
        this.mThumbnailUrl = in.readString();
        this.mAddress = in.readString();
        this.mAdmissionInfo = in.readString();
        this.mEmailAddress = in.readString();
        this.mPhoneNumber = in.readString();
        this.mOpeningHours = in.readString();
        this.mDescription = in.readParcelable(Description.class.getClassLoader());
        this.mMainMedia = in.readParcelable(MainMedia.class.getClassLoader());
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
