package com.alirnp.salizsalon.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Day implements Parcelable {

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


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    protected Day(Parcel in) {
        dayName = in.readString();
        monthName = in.readString();
        dayOfMonth = in.readString();
        isSelected = in.readByte() != 0x00;
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayName);
        dest.writeString(monthName);
        dest.writeString(dayOfMonth);
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
        dest.writeLong(date != null ? date.getTime() : -1L);
    }
}