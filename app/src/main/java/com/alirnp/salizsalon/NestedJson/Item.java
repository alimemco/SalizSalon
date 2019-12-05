package com.alirnp.salizsalon.NestedJson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {


    @SerializedName("level")
    @Expose
    private String level;


    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;


    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("hour")
    @Expose
    private String hour;

    @SerializedName("open")
    @Expose
    private String open;

    @SerializedName("reserved")
    @Expose
    private String reserved;


    @SerializedName("dayName")
    @Expose
    private String dayName;

    @SerializedName("dayOfMonth")
    @Expose
    private String dayOfMonth;

    @SerializedName("day_of_week")
    @Expose
    private int day_of_week;



    @SerializedName("monthName")
    @Expose
    private String monthName;


    @SerializedName("services")
    @Expose
    private String services;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("period")
    @Expose
    private String period;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("phone")
    @Expose
    private String phone;


    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("TIME_ID")
    @Expose
    private String timeID;

    @SerializedName("USERNAME")
    @Expose
    private String username;


    public Item() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(int day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getTimeID() {
        return timeID;
    }

    public void setTimeID(String timeID) {
        this.timeID = timeID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    protected Item(Parcel in) {
        category = in.readString();
        price = in.readString();
        title = in.readString();
        iD = in.readString();
        name = in.readString();
        image = in.readString();
        day = in.readString();
        hour = in.readString();
        open = in.readString();
        reserved = in.readString();
        dayName = in.readString();
        dayOfMonth = in.readString();
        monthName = in.readString();
        services = in.readString();
        status = in.readString();
        period = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        phone = in.readString();
        checked = in.readByte() != 0x00;
    }

    public String getPhone() {
        return phone;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(price);
        dest.writeString(title);
        dest.writeString(iD);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(day);
        dest.writeString(hour);
        dest.writeString(open);
        dest.writeString(reserved);
        dest.writeString(dayName);
        dest.writeString(dayOfMonth);
        dest.writeString(monthName);
        dest.writeString(services);
        dest.writeString(status);
        dest.writeString(period);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(phone);
        dest.writeByte((byte) (checked ? 0x01 : 0x00));
    }
}