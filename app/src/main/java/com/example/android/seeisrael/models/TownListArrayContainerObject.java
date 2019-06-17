package com.example.android.seeisrael.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TownListArrayContainerObject implements Parcelable {

    private List<Town> mListOfTowns;

    public TownListArrayContainerObject(List<Town> mListOfTowns) {
        this.mListOfTowns = mListOfTowns;
    }

    public List<Town> getmListOfTowns() {
        return mListOfTowns;
    }

    public void setmListOfTowns(List<Town> mListOfTowns) {
        this.mListOfTowns = mListOfTowns;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mListOfTowns);
    }

    protected TownListArrayContainerObject(Parcel in) {
        this.mListOfTowns = in.createTypedArrayList(Town.CREATOR);
    }

    public static final Parcelable.Creator<TownListArrayContainerObject> CREATOR = new Parcelable.Creator<TownListArrayContainerObject>() {
        @Override
        public TownListArrayContainerObject createFromParcel(Parcel source) {
            return new TownListArrayContainerObject(source);
        }

        @Override
        public TownListArrayContainerObject[] newArray(int size) {
            return new TownListArrayContainerObject[size];
        }
    };
}
