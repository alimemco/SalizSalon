
package com.alirnp.salizsalon.ADMIN.Model.time;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubItem {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("reserved")
    @Expose
    private String reserved;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

}
