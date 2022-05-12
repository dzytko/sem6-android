package com.example.project2.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class Phone implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String manufacturer;
    private String model;
    private String androidVersion;
    private String pageUrl;

    public Phone() {
    }

    public Phone(int id, String manufacturer, String model, String androidVersion, String pageUrl) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.pageUrl = pageUrl;
    }

    public Phone(String manufacturer, String model, String androidVersion, String pageUrl) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.pageUrl = pageUrl;
    }

    protected Phone(Parcel in) {
        id = in.readInt();
        manufacturer = in.readString();
        model = in.readString();
        androidVersion = in.readString();
        pageUrl = in.readString();
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(manufacturer);
        parcel.writeString(model);
        parcel.writeString(androidVersion);
        parcel.writeString(pageUrl);
    }
}
