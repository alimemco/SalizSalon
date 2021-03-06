package com.alirnp.salizsalon.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hour implements Parcelable {

    private String time;
    private int timeID;
    private boolean selected;
    private boolean reserved;


    public Hour(int timeID, String time, boolean reserved) {
        this.timeID = timeID;
        this.time = time;
        this.reserved = reserved;
    }

    protected Hour(Parcel in) {
        timeID = in.readInt();
        time = in.readString();
        selected = in.readByte() != 0x00;
        reserved = in.readByte() != 0x00;
    }

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public boolean isReserved() {
        return reserved;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Hour> CREATOR = new Parcelable.Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel in) {
            return new Hour(in);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(timeID);
        dest.writeString(time);
        dest.writeByte((byte) (selected ? 0x01 : 0x00));
        dest.writeByte((byte) (reserved ? 0x01 : 0x00));
    }
}