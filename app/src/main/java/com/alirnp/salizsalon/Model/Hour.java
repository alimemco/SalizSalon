package com.alirnp.salizsalon.Model;

public class Hour {

    private String time;
    private boolean selected;
    private boolean open;


    public Hour(String time, boolean open) {
        this.time = time;
        this.open = open;
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
}
