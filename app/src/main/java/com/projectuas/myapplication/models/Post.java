package com.projectuas.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private int id;
    private String content;
    private String user_id;
    private String username;
    private String created_date;

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
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
        dest.writeInt(this.id);
        dest.writeString(this.content);
        dest.writeString(this.user_id);
        dest.writeString(this.username);
        dest.writeString(this.created_date);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.content = source.readString();
        this.user_id = source.readString();
        this.username = source.readString();
        this.created_date = source.readString();
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.id = in.readInt();
        this.content = in.readString();
        this.user_id = in.readString();
        this.username = in.readString();
        this.created_date = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
