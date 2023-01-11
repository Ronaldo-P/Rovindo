package com.projectuas.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable {
    private int id;
    private String nama;
    private int stok;
    private String created_date;

    protected Barang(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        stok = in.readInt();
        created_date = in.readString();
    }

    public static final Creator<Barang> CREATOR = new Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getStok() {
        return stok;
    }

    public String getCreated_date() {
        return created_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeInt(stok);
        dest.writeString(created_date);
    }
}
