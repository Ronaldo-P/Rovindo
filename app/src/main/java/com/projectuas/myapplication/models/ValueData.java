package com.projectuas.myapplication.models;

import java.util.List;

public class ValueData<T> {
    private int success;
    private String messege;
    private List<T> data;

    public int getSuccess() {
        return success;
    }

    public String getMessege() {
        return messege;
    }

    public List<T> getData() {
        return data;
    }
}
