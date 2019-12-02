package com.alirnp.salizsalon.ADMIN.Model;

import com.alirnp.salizsalon.Utils.Constants;

public class Model {
    private int id;
    private String day;
    private String hour;
    private Constants.state type;
    private boolean reserved;


    public Model() {

    }

    public Model(String name, Constants.state type) {
        this.day = name;
        this.type = type;
    }

    public Model(int id, String day, String hour, Constants.state type, boolean reserved) {
        this.id = id;
        this.day = day;
        this.hour = hour;
        this.type = type;
        this.reserved = reserved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String name) {
        this.day = name;
    }

    public Constants.state getType() {
        return type;
    }

    public void setType(Constants.state type) {
        this.type = type;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}