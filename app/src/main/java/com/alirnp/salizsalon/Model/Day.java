package com.alirnp.salizsalon.Model;

import java.util.Date;

public class Day {

    private String dayName;
    private String monthName;
    private String dayOfMonth;
    private boolean isSelected;
    private Date date;


    public Day(String dayName, String monthName, String dayOfMonth, Date date) {
        this.dayName = dayName;
        this.monthName = monthName;
        this.dayOfMonth = dayOfMonth;
        this.date = date;
        this.isSelected = false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
