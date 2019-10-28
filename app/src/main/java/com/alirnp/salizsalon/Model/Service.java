package com.alirnp.salizsalon.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Service implements Parcelable {

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

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public Service() {
    }

    protected Service(Parcel in) {
        name = in.readString();
        price = in.readInt();
        recentPrice = in.readString();
        checked = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(recentPrice);
        dest.writeByte((byte) (checked ? 0x01 : 0x00));
    }
}