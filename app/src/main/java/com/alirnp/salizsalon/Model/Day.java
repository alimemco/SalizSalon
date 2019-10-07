package com.alirnp.salizsalon.Model;

public class Day {

    private String dayName;
    private String monthName;
    private String dayOfMonth;

    public Day(String dayName, String monthName, String dayOfMonth) {
        this.dayName = dayName;
        this.monthName = monthName;
        this.dayOfMonth = dayOfMonth;
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
}
