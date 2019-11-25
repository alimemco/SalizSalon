
package com.alirnp.salizsalon.ADMIN.Model.time;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("dayName")
    @Expose
    private String dayName;
    @SerializedName("subItem")
    @Expose
    private List<SubItem> subItem = null;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<SubItem> getSubItem() {
        return subItem;
    }

    public void setSubItem(List<SubItem> subItem) {
        this.subItem = subItem;
    }

}
