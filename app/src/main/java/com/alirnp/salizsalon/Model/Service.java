package com.alirnp.salizsalon.Model;

public class Service {

    private String name;
    private int price;
    private String recentPrice;
    private boolean checked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRecentPrice() {
        return recentPrice;
    }

    public void setRecentPrice(String recentPrice) {
        this.recentPrice = recentPrice;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
