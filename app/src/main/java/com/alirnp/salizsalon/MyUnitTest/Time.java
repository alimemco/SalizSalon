package com.alirnp.salizsalon.MyUnitTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Time {

    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("day_of_week")
    @Expose
    private int day_of_week;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("hour")
    @Expose
    private String hour;

    @SerializedName("reserved")
    @Expose
    private boolean reserved;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(int day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
