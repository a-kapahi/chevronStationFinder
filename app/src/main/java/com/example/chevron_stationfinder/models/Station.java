package com.example.chevron_stationfinder.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Station implements Parcelable {


    public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel source) {
            return new Station(source);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };
    public String id;
    public String lat;
    public String lng;
    private String name;
    private String brand;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String ccc;
    private String extramile;
    private String cstore;
    private String carwash;
    private String loyalty;
    private String loyalty_icon;
    private String loyalty_title;
    private String servicebay;
    private String truckshop;
    private String diesel;
    private String nfc;
    private String giftcard;
    private String distance;

    private Station(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.brand = in.readString();
        this.address = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.zip = in.readString();
        this.phone = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.ccc = in.readString();
        this.extramile = in.readString();
        this.cstore = in.readString();
        this.carwash = in.readString();
        this.loyalty = in.readString();
        this.loyalty_icon = in.readString();
        this.loyalty_title = in.readString();
        this.servicebay = in.readString();
        this.truckshop = in.readString();
        this.diesel = in.readString();
        this.nfc = in.readString();
        this.giftcard = in.readString();
        this.distance = in.readString();
    }

    public String getExtramile() {
        return extramile;
    }

    public String getCstore() {
        return cstore;
    }

    public String getCarwash() {
        return carwash;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public String getDiesel() {
        return diesel;
    }

    public String getNfc() {
        return nfc;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.brand);
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.zip);
        dest.writeString(this.phone);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.ccc);
        dest.writeString(this.extramile);
        dest.writeString(this.cstore);
        dest.writeString(this.carwash);
        dest.writeString(this.loyalty);
        dest.writeString(this.loyalty_icon);
        dest.writeString(this.loyalty_title);
        dest.writeString(this.servicebay);
        dest.writeString(this.truckshop);
        dest.writeString(this.diesel);
        dest.writeString(this.nfc);
        dest.writeString(this.giftcard);
        dest.writeString(this.distance);
    }
}
