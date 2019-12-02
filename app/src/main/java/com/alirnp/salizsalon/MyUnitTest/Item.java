package com.alirnp.salizsalon.MyUnitTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {


    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("child")
    @Expose
    private List<Time> times;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}
