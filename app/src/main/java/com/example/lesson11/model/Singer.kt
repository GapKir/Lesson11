package com.example.lesson11.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Singer implements Parcelable {
    private long id;
    private String name;
    private int singerPhoto;
    private String birthCountry;

    public Singer(long id, String name, int singerPhoto, String birthCountry) {
        this.id = id;
        this.name = name;
        this.singerPhoto = singerPhoto;
        this.birthCountry = birthCountry;
    }

    protected Singer(Parcel in) {
        id = in.readLong();
        name = in.readString();
        singerPhoto = in.readInt();
        birthCountry = in.readString();
    }

    public static final Creator<Singer> CREATOR = new Creator<Singer>() {
        @Override
        public Singer createFromParcel(Parcel in) {
            return new Singer(in);
        }

        @Override
        public Singer[] newArray(int size) {
            return new Singer[size];
        }
    };

    public long getId() {
        return id;
    }

    public int getSingerPhoto() {
        return singerPhoto;
    }

    public String getName() {
        return name;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeInt(singerPhoto);
        parcel.writeString(birthCountry);
    }
}
