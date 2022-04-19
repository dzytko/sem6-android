package com.example.project1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Grade implements Parcelable {
    private String name;
    private int gradeValue;

    public Grade(String name, int gradeValue) {
        this.name = name;
        this.gradeValue = gradeValue;
    }

    protected Grade(Parcel in) {
        name = in.readString();
        gradeValue = in.readInt();
    }

    public static final Creator<Grade> CREATOR = new Creator<Grade>() {
        @Override
        public Grade createFromParcel(Parcel in) {
            return new Grade(in);
        }

        @Override
        public Grade[] newArray(int size) {
            return new Grade[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(gradeValue);
    }
}
