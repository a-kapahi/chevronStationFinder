package com.example.chevron_stationfinder.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Prediction implements Parcelable {
    private String description;
    private String place_id;

    public Prediction(String description, String place_id) {
        this.description = description;
        this.place_id = place_id;
    }

    public static final Creator<Prediction> CREATOR = new Creator<Prediction>() {
        @Override
        public Prediction createFromParcel(Parcel in) {
            return new Prediction(in);
        }

        @Override
        public Prediction[] newArray(int size) {
            return new Prediction[size];
        }
    };

    protected Prediction(Parcel in) {
        description = in.readString();
        place_id = in.readString();
    }

    public String getDescription() {
        return description;
    }

    public String getPlace_id() {
        return place_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(place_id);
    }
}
