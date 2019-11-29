package com.alirnp.salizsalon.ADMIN.Model;

public class Model {
    private String day;
    private String hour;
    private Integer type;
    private boolean reserved;


    public Model() {

    }

    public Model(String name, Integer type) {
        this.day = name;
        this.type = type;
    }

    public Model(String day, String hour, Integer type, boolean reserved) {
        this.day = day;
        this.hour = hour;
        this.type = type;
        this.reserved = reserved;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String name) {
        this.day = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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